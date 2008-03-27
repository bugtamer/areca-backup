package com.application.areca.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.application.areca.RecoveryEntry;
import com.myJava.object.EqualsHelper;
import com.myJava.object.HashHelper;
import com.myJava.object.PublicClonable;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 7289397627058093710
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
public class FilterGroup implements ArchiveFilter {

    private boolean isAnd = true;
    private List filters = new ArrayList();
    private boolean isExclude = false;
    
    /**
     * Adds a filter and recomputes the cached values for
     * the "doesExclusionApplyToChildrenImpl" method.      
     */         
    public void addFilter(ArchiveFilter f) {
        this.filters.add(f);
    }
    
    public Iterator getFilterIterator() {
        return this.filters.iterator();
    } 
    
    public boolean isAnd() {
        return isAnd;
    }

    public void setAnd(boolean isAnd) {
        this.isAnd = isAnd;
    }

    /**
     * Accepts (or refuses) an entry
     */         
    public boolean acceptIteration(RecoveryEntry entry) {
        boolean matchFilter;
        
        Iterator iter = this.getFilterIterator();
        if (this.isAnd()) {
            // AND
            matchFilter = true;
            while (iter.hasNext()) {
                ArchiveFilter filter = (ArchiveFilter)iter.next();
                if (! filter.acceptIteration(entry)) {
                    matchFilter = false;
                    break;
                }
            }
        } else {
            // OR
            matchFilter = false;            
            while (iter.hasNext()) {
                ArchiveFilter filter = (ArchiveFilter)iter.next();
                if (filter.acceptIteration(entry)) {
                    matchFilter = true;
                    break;
                }
            }
        }
        return isExclude ? ! matchFilter : matchFilter;
    }
    
    /**
     * Accepts (or refuses) an entry
     */         
    public boolean acceptStorage(RecoveryEntry entry) {
        boolean matchFilter;
        
        Iterator iter = this.getFilterIterator();
        if (this.isAnd()) {
            // AND
            matchFilter = true;
            while (iter.hasNext()) {
                ArchiveFilter filter = (ArchiveFilter)iter.next();
                if (! filter.acceptStorage(entry)) {
                    matchFilter = false;
                    break;
                }
            }
        } else {
            // OR
            matchFilter = false;            
            while (iter.hasNext()) {
                ArchiveFilter filter = (ArchiveFilter)iter.next();
                if (filter.acceptStorage(entry)) {
                    matchFilter = true;
                    break;
                }
            }
        }
        return isExclude ? ! matchFilter : matchFilter;
    }
    
    public void remove(ArchiveFilter filter) {
        this.filters.remove(filter);
    }

    public boolean equals(Object obj) {
        if (! EqualsHelper.checkClasses(this, obj)) {
            return false;
        } else {
            FilterGroup other = (FilterGroup)obj;
            return (
                    EqualsHelper.equals(other.isAnd(), this.isAnd())
                    && EqualsHelper.equals(other.isExclude(), this.isExclude())
                    && EqualsHelper.equals(other.filters, this.filters)                    
            );
        }
    }
    
    public int hashCode() {
        int hash = HashHelper.initHash(this);
        hash = HashHelper.hash(hash, this.isExclude);
        hash = HashHelper.hash(hash, this.isAnd());
        Iterator iter = this.getFilterIterator();
        while (iter.hasNext()) {
            hash = HashHelper.hash(hash, iter.next());
        }
        return hash;
    }

    public PublicClonable duplicate() {
        FilterGroup other = new FilterGroup();
        other.setAnd(this.isAnd);
        other.setExclude(this.isExclude);
        Iterator iter = this.getFilterIterator();
        while (iter.hasNext()) {
            ArchiveFilter filter = (ArchiveFilter)iter.next();
            other.addFilter((ArchiveFilter)filter.duplicate());
        }
        return other;
    }

    public boolean isExclude() {
        return isExclude;
    }

    public boolean requiresParameters() {
        return false;
    }
    
    public String getStringParameters() {
        return "";
    }
    
    public void acceptParameters(String parameters) {
        throw new UnsupportedOperationException("Parameters are not supported by this implementation.");
    }

    public void setExclude(boolean exclude) {
        isExclude = exclude;
    }
}
