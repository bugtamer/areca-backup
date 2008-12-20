package com.myJava.file.driver;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import com.myJava.configuration.FrameworkConfiguration;
import com.myJava.file.FileNameUtil;
import com.myJava.file.InvalidPathException;
import com.myJava.file.metadata.FileMetaData;
import com.myJava.system.OSTool;

/**
 * Impl�mentation abstraite de l'interface FileSystemDriver
 * <BR>Lance des UnsupportedOperationExceptions.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 8785459451506899793
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
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
public abstract class AbstractFileSystemDriver 
implements FileSystemDriver {
    private static long MAX_FILEPATH = FrameworkConfiguration.getInstance().getMaxFilePath();
    private static int FORCE_MAX_FILEPATH_CHECK = FrameworkConfiguration.getInstance().getForceMaxFilePathCheck();    
    private static boolean CHECK_PATH = 
        MAX_FILEPATH != 0 
        && (
                FORCE_MAX_FILEPATH_CHECK == 1 || 
                (FORCE_MAX_FILEPATH_CHECK == -1 && OSTool.isSystemWindows())
        );
    
    public boolean createNewFile(File file) throws IOException {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean delete(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean exists(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public File getAbsoluteFile(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public String getAbsolutePath(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public File getCanonicalFile(File file) throws IOException {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }

    public boolean createSymbolicLink(File symlink, String realPath) throws IOException {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }

    public String getCanonicalPath(File file) throws IOException {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public String getName(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public String getParent(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public File getParentFile(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public String getPath(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean isAbsolute(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean isDirectory(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean isFile(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean isHidden(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public long lastModified(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public long length(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public String[] list(File file, FilenameFilter filter) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public String[] list(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public File[] listFiles(File file, FileFilter filter) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public File[] listFiles(File file, FilenameFilter filter) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public File[] listFiles(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean mkdir(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean mkdirs(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean renameTo(File source, File dest) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean setLastModified(File file, long time) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public boolean setReadOnly(File file) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public FileMetaData getAttributes(File f) throws IOException {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }

    public void applyAttributes(FileMetaData p, File f) throws IOException {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }
    
    public void deleteOnExit(File f) {
        throw new UnsupportedOperationException("This method is not supported by this implementation");
    }

    public static String normalizeIfNeeded(String path) {
        if (File.separatorChar == '\\') {
            return FileNameUtil.normalizePath(path);
        } else {
            return path;
        }
    }
    
    protected void checkFilePath(File f) throws InvalidPathException {
        if (CHECK_PATH) {
            String p = getAbsolutePath(f);
            if (p != null && p.length() > MAX_FILEPATH) {
                throw new InvalidPathException("File path (" + p + ") exceeds maximum length (" + MAX_FILEPATH + ")");
            }
        }
    }
    
    public InputStream getCachedFileInputStream(File file) throws IOException {
        return getFileInputStream(file);
    }
}