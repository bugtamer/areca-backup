package com.application.areca.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.application.areca.ApplicationException;
import com.application.areca.context.ProcessContext;
import com.application.areca.impl.tools.RecoveryFilterMap;
import com.application.areca.metadata.content.ArchiveContentAdapter;
import com.application.areca.metadata.trace.ArchiveTraceManager;
import com.myJava.file.FileFilterList;
import com.myJava.file.FileSystemManager;
import com.myJava.file.FileTool;
import com.myJava.file.InvalidPathException;
import com.myJava.file.driver.CompressedFileSystemDriver;
import com.myJava.file.driver.FileSystemDriver;
import com.myJava.object.Duplicable;
import com.myJava.util.log.Logger;
import com.myJava.util.taskmonitor.TaskCancelledException;

/**
 * Incremental medium that stores data in individual files (compressed or not)
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 1391842375571115750
 */

 /*
 Copyright 2005-2009, Olivier PETRUCCI.

This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class IncrementalDirectoryMedium extends AbstractIncrementalFileSystemMedium {

	public Duplicable duplicate() {
		IncrementalDirectoryMedium other = new IncrementalDirectoryMedium();
		copyAttributes(other);
		return other;
	}

	protected FileSystemDriver buildStorageDriver(File storageDir) throws ApplicationException {
		FileSystemDriver driver = super.buildStorageDriver(storageDir);

		if (this.compressionArguments.isCompressed()) {
			driver = new CompressedFileSystemDriver(storageDir, driver, compressionArguments);
		}

		return driver;
	}

	/**
	 * Return a description for the medium
	 */
	public String getDescription() {
		String type = "incremental";
		if (imageBackups) {
			type = "image"; 
		}
		return "Uncompressed " + type + " medium. (" + fileSystemPolicy.getArchivePath() + ")";        
	}  

	protected void prepareContext(ProcessContext context) throws IOException {
		if (imageBackups && context.getReferenceTrace() != null) {
			// see "registerUnstoredFile" method
			ArchiveContentAdapter adapter = new ArchiveContentAdapter(duplicateContentFile(context.getHashAdapter().getFile(), context));
			context.setPreviousHashIterator(adapter.buildIterator(true));
		}
	}
	
    protected void registerUnstoredFile(FileSystemRecoveryEntry entry, ProcessContext context) throws IOException {
		if (imageBackups) {
			context.getContentAdapter().writeContentEntry(entry);
		
			boolean found = context.getPreviousHashIterator().fetchUntil(entry.getKey());
			if (found) {
				context.getHashAdapter().writeGenericEntry(entry.getKey(), context.getPreviousHashIterator().current().getData());
			} else {
				// Shall not happen
				throw new IllegalArgumentException(entry.getKey() + " not found in hash file. Current entry = " + context.getPreviousHashIterator().current().getKey());
			}
		}
	}

	public File duplicateContentFile(File source, ProcessContext context) {
		File target = null;
		if (FileSystemManager.exists(source)) {
			try {
				// Copy file in a temporary place
				target = FileTool.getInstance().generateNewWorkingFile("areca", "ctn");
				FileTool.getInstance().copyFile(source, FileSystemManager.getParentFile(target), FileSystemManager.getName(target), null, null);
			} catch (IOException e) {
				Logger.defaultLogger().error(e);
				throw new IllegalStateException(e);
			} catch (TaskCancelledException e) {
				// ignored : never happens
				Logger.defaultLogger().error(e);
			}
		}
		return target;
	}

	protected void storeFileInArchive(FileSystemRecoveryEntry entry, InputStream in, ProcessContext context) throws ApplicationException, TaskCancelledException {
		// Store the file
		File targetFile = new File(context.getCurrentArchiveFile(), entry.getKey());
		File targetDirectory = FileSystemManager.getParentFile(targetFile);
		OutputStream out = null;
		try {
			if (! FileSystemManager.exists(targetDirectory)) {
				FileTool.getInstance().createDir(targetDirectory);
			}

			out = FileSystemManager.getFileOutputStream(targetFile, false, context.getOutputStreamListener());
			this.handler.store(entry, in, out, context);
		} catch (InvalidPathException e) {
			throw new ApplicationException("Error storing file " + FileSystemManager.getAbsolutePath(entry.getFile()) + " : " + e.getMessage(), e);
		} catch (Throwable e) {
			if (e instanceof TaskCancelledException) {
				throw (TaskCancelledException)e;
			} else {
				throw new ApplicationException("Error storing file " + FileSystemManager.getAbsolutePath(entry.getFile()) + " - target=" + FileSystemManager.getAbsolutePath(targetFile), e);
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Logger.defaultLogger().error(e);
				}
			}
		}
	}

	public void completeLocalCopyCleaning(File copy, ProcessContext context) throws IOException, ApplicationException {
	}

	public void cleanLocalCopies(List copies, ProcessContext context) throws IOException, ApplicationException {
	}

	public File[] ensureLocalCopy(
			File[] archivesToProcess, 
			boolean overrideRecoveredFiles, 
			File destination, 
			RecoveryFilterMap filtersByArchive, 
			ProcessContext context
	) throws IOException, ApplicationException, TaskCancelledException {
		if (overrideRecoveredFiles) {
			try {
				context.getInfoChannel().print("Data recovery ...");
				for (int i=0; i<archivesToProcess.length; i++) {
					FileFilterList filters = null;
					if (filtersByArchive != null) {
						filters = (FileFilterList)filtersByArchive.get(archivesToProcess[i]);
					}

					logRecoveryStep(filtersByArchive, filters, archivesToProcess[i], context);

					// Copie de l'element en cours.
					if (filtersByArchive == null) {
						copyFile(archivesToProcess[i], destination, FileSystemManager.getAbsolutePath(archivesToProcess[i]), i, context);
					} else if (filters != null) {
						for (int j=0; j<filters.size(); j++) {
							File sourceFileOrDirectory = new File(archivesToProcess[i], (String)filters.get(j));
							if (FileSystemManager.exists(sourceFileOrDirectory)) {
								File targetDirectory = FileSystemManager.getParentFile(new File(destination, (String)filters.get(j)));
								tool.copy(sourceFileOrDirectory, targetDirectory, context.getTaskMonitor(), context.getOutputStreamListener());
							}
						}
					}

					context.getTaskMonitor().getCurrentActiveSubTask().setCurrentCompletion(i+1, archivesToProcess.length);
				}

				return new File[] {destination};
			} catch (IOException e) {
				throw e;
			} catch (TaskCancelledException e) {
				throw e;
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
		} else {
			return archivesToProcess;
		}
	}

	/**
	 * Copy a stored file to a local location denoted by the "destination" argument.
	 */
	private void copyFile(File source, File destination, String root, int index, ProcessContext context) 
	throws IOException, TaskCancelledException {
		String localPath = FileSystemManager.getAbsolutePath(source).substring(root.length());
		if (FileSystemManager.isFile(source)) {
			File tg = new File(destination, localPath);
			tool.copyFile(source, FileSystemManager.getFileOutputStream(tg, false, context.getOutputStreamListener()), true, context.getTaskMonitor());
		} else {
			tool.createDir(new File(destination, localPath));
			File[] files = FileSystemManager.listFiles(source);
			for (int i=0; i<files.length; i++) {
				copyFile(files[i], destination, root, index, context);
			}
		}
	}

	protected void closeArchive(ProcessContext context) throws IOException, ApplicationException {
		if (context.getPreviousHashIterator() != null) {
			context.getPreviousHashIterator().close();
		}
	}

	protected void computeMergeDirectories(ProcessContext context) {
		context.setCurrentArchiveFile(context.getRecoveryDestination());
	}

	protected void buildMergedArchiveFromDirectory(ProcessContext context) throws ApplicationException {
		// does nothing
	} 

	public void commitBackup(ProcessContext context) throws ApplicationException {
		super.commitBackup(context);

		if (imageBackups) {
			try {
				this.cleanUnwantedFiles(
						new File(computeFinalArchivePath()),
						ArchiveTraceManager.resolveTraceFileForArchive(this, context.getCurrentArchiveFile()),
						false,
						context); // --> Call to "cleanUnwantedFiles" in "cancel unsensitive" mode
			} catch (TaskCancelledException e) {
				throw new ApplicationException(e);
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
		}
	}

	protected void convertArchiveToFinal(ProcessContext context) throws IOException, ApplicationException {
		// Case of empty archive (nothing to store)
		if (! FileSystemManager.exists(context.getCurrentArchiveFile())) {
			AbstractFileSystemMedium.tool.createDir(context.getCurrentArchiveFile());
		}
		super.convertArchiveToFinal(context);
	}

	protected String getArchiveExtension() {
		return "";
	}
}