package com.application.areca.launcher.gui;

import java.io.File;

import org.eclipse.swt.widgets.Composite;

import com.application.areca.ResourceManager;
import com.application.areca.filter.ArchiveFilter;
import com.application.areca.filter.DirectoryArchiveFilter;
import com.application.areca.filter.FileDateArchiveFilter;
import com.application.areca.filter.FileExtensionArchiveFilter;
import com.application.areca.filter.FileSizeArchiveFilter;
import com.application.areca.filter.FilterGroup;
import com.application.areca.filter.LinkFilter;
import com.application.areca.filter.LockedFileFilter;
import com.application.areca.filter.RegexArchiveFilter;
import com.application.areca.launcher.gui.filters.AbstractFilterComposite;
import com.application.areca.launcher.gui.filters.DirectoryFilterComposite;
import com.application.areca.launcher.gui.filters.FileDateFilterComposite;
import com.application.areca.launcher.gui.filters.FileExtensionFilterComposite;
import com.application.areca.launcher.gui.filters.FileSizeFilterComposite;
import com.application.areca.launcher.gui.filters.FilterGroupComposite;
import com.application.areca.launcher.gui.filters.RegexFilterComposite;
import com.myJava.file.FileSystemManager;

/**
 * 
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
public class FilterRepository {
    
    private static final ResourceManager RM = ResourceManager.instance();
    
    public static boolean checkParameters(String params, int filterIndex) {
        // Check that the parameters are correct
        ArchiveFilter filter = buildFilter(filterIndex);

        // Parameters are mandatory
        if (
                filter == null 
                || ((params== null || params.length() == 0) && filter.requiresParameters())
        ) {
            return false;
        }
        
        if (filterIndex == 2 && ! FileSystemManager.exists(new File(params))) {
            return false;
        } 
      
        try {
            filter.acceptParameters(params);
        } catch (Throwable e) {
            return false;
        }
        
        return true;        
    }
    
    public static AbstractFilterComposite buildFilterComposite(
            int index, 
            Composite composite,
            ArchiveFilter filter, 
            FilterEditionWindow frm
    ) {
        AbstractFilterComposite pnl = null;
        if (index == 0) {
            pnl = new FileExtensionFilterComposite(composite, filter, frm);
        } else if (index == 1){
            pnl = new RegexFilterComposite(composite, filter, frm);
        } else if (index == 2){
            pnl = new DirectoryFilterComposite(composite, filter, frm);
        } else if (index == 3){
            pnl = new FileSizeFilterComposite(composite, filter, frm);
        } else if (index == 4){
            pnl = new FileDateFilterComposite(composite, filter, frm);
        } else if (index == 7) {
            pnl = new FilterGroupComposite(composite, filter, frm);
        }
        
        return pnl;
    }
    
    public static ArchiveFilter buildFilter(int filterIndex) {
        ArchiveFilter filter = null;
        if (filterIndex == 0) {
            filter = new FileExtensionArchiveFilter();
        } else if (filterIndex == 1){
            filter = new RegexArchiveFilter();
        } else if (filterIndex == 2){
            filter = new DirectoryArchiveFilter();
        } else if (filterIndex == 3){
            filter = new FileSizeArchiveFilter();
        } else if (filterIndex== 4){
            filter = new FileDateArchiveFilter();
        } else if (filterIndex== 5){
            filter = new LinkFilter();
        } else if (filterIndex== 6){
            filter = new LockedFileFilter();
        } else if (filterIndex == 7) {
            filter = new FilterGroup();
        }
        
        return filter;
    }
    
    public static int getIndex(Class currentFilter) {
        if (DirectoryArchiveFilter.class.isAssignableFrom(currentFilter)) {
            return 2;
        } else if (RegexArchiveFilter.class.isAssignableFrom(currentFilter)) {
            return 1;
        } else if (FileExtensionArchiveFilter.class.isAssignableFrom(currentFilter)) {
            return 0;         
        } else if (FileSizeArchiveFilter.class.isAssignableFrom(currentFilter)) {
            return 3;   
        } else if (FileDateArchiveFilter.class.isAssignableFrom(currentFilter)) {
            return 4;               
        } else if (LinkFilter.class.isAssignableFrom(currentFilter)) {
            return 5;               
        } else if (LockedFileFilter.class.isAssignableFrom(currentFilter)) {
            return 6;               
        } else if (FilterGroup.class.isAssignableFrom(currentFilter)) {
            return 7;
        }
        return 0;
    }
    
    public static int getIndex(ArchiveFilter currentFilter) {
        if (currentFilter == null) {
            return 0;
        }
        return getIndex(currentFilter.getClass());
    }
    
    public static String getName(Class filter) {
        if (DirectoryArchiveFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.directory.label");
        } else if (RegexArchiveFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.regex.label");
        } else if (FileSizeArchiveFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.filesize.label");
        } else if (FileDateArchiveFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.filedate.label");    
        } else if (LinkFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.link.label");      
        } else if (LockedFileFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.lockedfile.label");                      
        } else if (FileExtensionArchiveFilter.class.isAssignableFrom(filter)) {
            return RM.getLabel("filteredition.fileext.label");          
        } else {
            return "...";
        }
    }
}