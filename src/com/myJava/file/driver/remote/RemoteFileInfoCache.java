package com.myJava.file.driver.remote;

import java.util.HashMap;
import java.util.Map;

import com.myJava.configuration.FrameworkConfiguration;
import com.myJava.util.log.Logger;

import sun.misc.Queue;

/**
 * Local cache of file data.
 * <BR>This cache is complementary to the CachedFileSystemDriver : it stored data for all recently accessed files
 * while the CachedFileSystemDriver is limited by its "depth" property.
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
public class RemoteFileInfoCache {

    private static final int CACHE_SIZE;
    
    static {
        if (FrameworkConfiguration.getInstance().isRemoteCacheMode()) {
            CACHE_SIZE= FrameworkConfiguration.getInstance().getFTPCacheSize();
        } else {
            CACHE_SIZE = 0;
        }
    }
    
    // DATA CACHE
    private Map fileInfoCache = new HashMap();
    private Queue fileInfoOrder = new Queue();

    public RemoteFileInfoCache() {
    }
    
    public synchronized void registerFileInfo(String remoteFileName, FictiveFile info) {
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
    
    public synchronized FictiveFile getCachedFileInfos(String remoteFileName) {
    	return (FictiveFile)this.fileInfoCache.get(remoteFileName);
    }
    
    public synchronized void removeCachedFileInfos(String fileName) {
        this.fileInfoCache.remove(fileName);
    }
    
    public synchronized void clearCache() {
        this.fileInfoCache.clear();
        this.fileInfoOrder = new Queue();
    }
    
    public int size() {
        return this.fileInfoCache.size();
    }
}
