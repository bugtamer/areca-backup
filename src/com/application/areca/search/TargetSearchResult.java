package com.application.areca.search;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.application.areca.AbstractRecoveryTarget;

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
public class TargetSearchResult {
    
    private AbstractRecoveryTarget target;
    private TreeSet items = new TreeSet(new SearchResultItemComparator());
    
    public TargetSearchResult() {
    }
    
    public Set getItems() {
        return items;
    }
    
    public AbstractRecoveryTarget getTarget() {
        return target;
    }
    
    public void addSearchresultItem(SearchResultItem item) {
        this.items.add(item);
    }
    
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
    
    private static class SearchResultItemComparator implements Comparator {
        
        public int compare(Object o1, Object o2) {
            SearchResultItem i1 = (SearchResultItem)o1;
            SearchResultItem i2 = (SearchResultItem)o2;
            
            return i1.getEntry().getName().compareTo(i2.getEntry().getName());
        }
    }
}
