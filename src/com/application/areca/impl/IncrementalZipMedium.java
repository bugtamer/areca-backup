package com.application.areca.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.myJava.file.archive.ArchiveAdapter;
import com.myJava.file.archive.zip.ZipArchiveAdapter;
import com.myJava.util.PublicClonable;

/**
 * Zip storage medium
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 2162742295696737000
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
public class IncrementalZipMedium extends AbstractIncrementalArchiveMedium {
       
    public PublicClonable duplicate() {
        IncrementalZipMedium other = new IncrementalZipMedium();
        copyAttributes(other);
        return other;
    }
    
    protected ArchiveAdapter getArchiveAdapter(OutputStream out) throws IOException {
        return new ZipArchiveAdapter(out);
    }
    
    protected ArchiveAdapter getArchiveAdapter(InputStream in, long length) throws IOException {
        return new ZipArchiveAdapter(in, length); 
    }
    
    protected String getArchiveExtension() {
        return ".zip";
    }
}
