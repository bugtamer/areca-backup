package com.myJava.file.delta.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 6668125177615540854
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
public class IOHelper {
    
    public static final int get16(byte b[], int off) {
        return (b[off] & 0xff) | ((b[off+1] & 0xff) << 8);
    }
    
    public static final long get32(byte b[], int off) {
        return get16(b, off) | ((long)get16(b, off+2) << 16);
    }

    public static final long get64(byte b[], int off) {
        return get32(b, off) | ((long)get32(b, off+4) << 32);
    }
    
    public static void writeShort(long v, OutputStream out) throws IOException {
        out.write((int)((v >>>  0) & 0xff));
        out.write((int)((v >>>  8) & 0xff));
    }
    
    public static void writeInt(long v, OutputStream out) throws IOException {
        out.write((int)((v >>>  0) & 0xff));
        out.write((int)((v >>>  8) & 0xff));
        out.write((int)((v >>> 16) & 0xff));
        out.write((int)((v >>> 24) & 0xff));
    }
    
    public static void writeLong(long v, OutputStream out) throws IOException {
        out.write((int)((v >>>  0) & 0xff));
        out.write((int)((v >>>  8) & 0xff));
        out.write((int)((v >>> 16) & 0xff));
        out.write((int)((v >>> 24) & 0xff));
        out.write((int)((v >>> 32) & 0xff));
        out.write((int)((v >>> 40) & 0xff));
        out.write((int)((v >>> 48) & 0xff));
        out.write((int)((v >>> 56) & 0xff));        
    }
    
    public static int readFully(InputStream in, byte[] data) throws IOException {
    	return readFully(in, data, 0, data.length);
    }
    
    public static int readFully(InputStream in, byte[] data, int offset, int length) throws IOException {
    	int r = in.read(data, offset, length);
    	int t = r;
    	while (r != -1 && t < length) {
    		r = in.read(data, offset + t, length - t);
    		if (r != -1) {
    			t += r;
    		}
    	}
    	return t;
    }
    
    public static long skipFully(InputStream in, long length) throws IOException {
    	long s = in.skip(length);
    	long t = s;
    	while (s != -1 && t < length) {
    		s = in.skip(length - t);
    		if (s != -1) {
    			t += s;
    		}
    	}
    	return t;
    }
}
