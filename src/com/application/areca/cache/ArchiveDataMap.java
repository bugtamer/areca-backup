package com.application.areca.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.myJava.file.FileSystemManager;

/**
 * Sorted cache of data for a given FileSystemArchiveMedium.
 * <BR>These data are indexed by File.
 * <BR>It counts the usage of each file and build an eviction strategy upon these statistics.
 * <BR>It checks that the underlying file key hasn't been modified since the caching of the data and refreshes its content if needed.
 * <BR>
 * <BR>This class is not synchronized. The caller must manage concurrent accesses.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 2380639557663016217
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
public class ArchiveDataMap 
	implements ObjectPool  // additional behaviour which allows to pool instances and link their lifecycle to the cache. 
	{
    
    /**
     * <BR>Contains instances of CachedData indexed by File
     */
    private Map content;
    
    /**
     * Set containing pooled instances.
     */
    private HashMap pooledData = new HashMap();
    
    /**
     * Keeps a trace of the nr of accesses to this cache
     */
    private long usageCount = 0;
    
    /**
     * Approximation of the global size of the data map
     */
    private long sizeApproximation = 0;

    public ArchiveDataMap() {
        this.content = new HashMap();
    }
    
    public Object get(File key) {
        CachedData data = (CachedData)this.content.get(key);
        if (data == null) {
            return null;
        } else {
	        String currentHash = computeHash(key);
	        if (currentHash.equals(data.getHash())) {
	            return data.getData();
	        } else {
	            return null;
	        }
        }
    }
    
    public int getPoolSize() {
        return this.pooledData.size();
    }
    
    /**
     * Computes a score used by the eviction strategy 
     */
    public double computeScore() {
        if (sizeApproximation == 0) {
            return Double.MAX_VALUE; //             x / 0.0 = infinity !
        } else {
            return (1000000.0 * (double)usageCount) / ((double)sizeApproximation);
        }
    }
    
    public void put(File key, Object data, long approximateSize) {
        this.content.put(key, new CachedData(computeHash(key), data, approximateSize));
        this.sizeApproximation += approximateSize;
    }
    
    /**
     * Supprime l'objet correspondant � l'archive pass�e en argument.
     * <BR>L'objet �ventuellement supprim� est retourn� par la m�thode.
     */
    public Object remove(File key) {
        CachedData data = (CachedData)this.content.remove(key);
        if (data == null) {
            return null;
        } else {
            this.sizeApproximation -= data.getApproximateSize();
            return data.getData();
        }
    }
    
    public Iterator keyIterator() {
        return this.content.keySet().iterator();
    } 
    
    /**
     * Clears the map's content but keeps the usage count.
     */
    public void clear() {
        this.content.clear();
        this.sizeApproximation = 0;
        this.pooledData.clear();
    }
    
    public boolean isEmpty() {
        return content.isEmpty();
    }
    
    /**
     * Checks wether the pooled data map already contains this object.
     * <BR>If the object is already referenced, the existing instance is returned.
     * <BR>Otherwise, the object is added to the pooled data and returned to the caller.  
     */
    public Object intern(Object o) {
        if (o == null) {
            return null;
        }
        
        Object old = this.pooledData.get(o);
        if (old == null) {
            this.pooledData.put(o, o);
            return o;
        } else {
            return old;
        }
    }
    
    /**
     * Registers that the cache has been used
     */
    protected void registerUsage() {
        this.usageCount++;
    }
    
    private static String computeHash(File file) {
        return "" + FileSystemManager.length(file) + "#" + FileSystemManager.lastModified(file);
    }
}