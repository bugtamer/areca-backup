package com.myJava.file.ftp;

import java.util.HashMap;
import java.util.Map;

import com.myJava.configuration.FrameworkConfiguration;
import com.myJava.util.log.Logger;

import sun.misc.Queue;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 7453350623295719521
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
public class FTPFileInfoCache {

    private static final int CACHE_SIZE;
    
    static {
        if (FrameworkConfiguration.getInstance().isFTPCacheMode()) {
            CACHE_SIZE= FrameworkConfiguration.getInstance().getFTPCacheSize();
        } else {
            CACHE_SIZE = 0;
        }
    }
    
    // DATA CACHE
    private Map fileInfoCache = new HashMap();
    private Queue fileInfoOrder = new Queue();

    public FTPFileInfoCache() {
    }
    
    protected synchronized void registerFileInfo(String remoteFileName, FictiveFile info) {
        if (CACHE_SIZE != 0) {
	        try {
	            while (this.fileInfoCache.size() >= CACHE_SIZE) {
	                this.fileInfoCache.remove(this.fileInfoOrder.dequeue());
	            }
	            
	            this.fileInfoCache.put(remoteFileName, info);
	            this.fileInfoOrder.enqueue(remoteFileName);
	        } catch (InterruptedException e) {
	            Logger.defaultLogger().error(e);
	        }
        }
    }
    
    protected synchronized FictiveFile getCachedFileInfos(String remoteFileName) {
        return (FictiveFile)this.fileInfoCache.get(remoteFileName);
    }
    
    protected synchronized void removeCachedFileInfos(String fileName) {
        this.fileInfoCache.remove(fileName);
    }
    
    protected synchronized void clearCache() {
        this.fileInfoCache.clear();
        this.fileInfoOrder = new Queue();
    }
    
    public int size() {
        return this.fileInfoCache.size();
    }
}
