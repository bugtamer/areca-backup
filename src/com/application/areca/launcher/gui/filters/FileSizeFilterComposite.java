package com.application.areca.launcher.gui.filters;

import org.eclipse.swt.widgets.Composite;

import com.application.areca.filter.ArchiveFilter;
import com.application.areca.filter.FileSizeArchiveFilter;
import com.application.areca.launcher.gui.FilterEditionWindow;
import com.application.areca.launcher.gui.FilterRepository;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 6892146605129115786
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
public class FileSizeFilterComposite extends AbstractSimpleParamFilterComposite {
    private static final String EXAMPLE = RM.getLabel("filteredition.examplesize.label");    
    
    public FileSizeFilterComposite(Composite composite, ArchiveFilter filter, FilterEditionWindow window) {
        super(composite, FilterRepository.getIndex(FileSizeArchiveFilter.class), filter, window);
    }

    public String getParamExample() {
        return EXAMPLE;
    }
}
