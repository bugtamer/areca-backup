package com.application.areca.metadata.manifest;

import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.application.areca.ApplicationException;
import com.application.areca.impl.AbstractFileSystemMedium;
import com.myJava.file.FileSystemManager;
import com.myJava.file.FileTool;
import com.myJava.util.log.Logger;
import com.myJava.util.xml.AdapterException;
import com.myJava.util.xml.XMLTool;

/**
 * Reads Manifests from the disk.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 *
 */

 /*
 Copyright 2005-2010, Olivier PETRUCCI.

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
public class ManifestManager {
    private static FileTool tool = FileTool.getInstance();
    
    public static Manifest readManifestForArchive(AbstractFileSystemMedium medium, File archive) throws ApplicationException {
        try {
            File dataDir = medium.getDataDirectory(archive);
            File manifestFile = new File(dataDir, medium.getManifestName());
            ManifestReader reader = buildReader(manifestFile);
            return reader.read(manifestFile);
        } catch (AdapterException e) {
            Logger.defaultLogger().error(e);
            throw new ApplicationException(e);
        }
    }
    
    public static void writeManifest(AbstractFileSystemMedium medium, Manifest mf, File archive) throws ApplicationException {
        try {
            File metadataDir = medium.getDataDirectory(archive);
            if (! FileSystemManager.exists(metadataDir)) {
                tool.createDir(metadataDir);
            }
            File manifestFile = new File(metadataDir, medium.getManifestName());
            XMLManifestAdapter adapter = new XMLManifestAdapter();
            adapter.write(mf, manifestFile);
        } catch (AdapterException e) {
            Logger.defaultLogger().error(e);
            throw new ApplicationException(e);    
        } catch (IOException e) {
            Logger.defaultLogger().error(e);
            throw new ApplicationException(e);               
        }
    }
    
	private static ManifestReader buildReader(File file) {
		try {
			String r = FileTool.getInstance().getFirstRow(new GZIPInputStream(FileSystemManager.getFileInputStream(file)), XMLManifestAdapter.ENCODING);
			if (r.equalsIgnoreCase(XMLTool.getHeader(XMLManifestAdapter.ENCODING))) {
				return new XMLManifestAdapter();
			} else {
				return new DeprecatedManifestAdapter();
			}
		} catch (IOException e) {
			return new XMLManifestAdapter();
		}
	}
}
