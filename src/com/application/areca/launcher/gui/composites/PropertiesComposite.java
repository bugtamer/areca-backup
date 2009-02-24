package com.application.areca.launcher.gui.composites;

import java.io.File;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.application.areca.ResourceManager;
import com.application.areca.TargetGroup;
import com.application.areca.impl.AbstractIncrementalFileSystemMedium;
import com.application.areca.impl.FileSystemRecoveryTarget;
import com.application.areca.impl.handler.DefaultArchiveHandler;
import com.application.areca.launcher.gui.Application;
import com.application.areca.launcher.gui.common.AbstractWindow;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 8156499128785761244
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
public class PropertiesComposite 
extends Composite { 
    private static final ResourceManager RM = ResourceManager.instance();
    
    private Table table;
    private TableViewer viewer;
    private Application application = Application.getInstance();
    
    public PropertiesComposite(Composite parent) {
        super(parent, SWT.NONE);
        setLayout(new FillLayout());

        viewer = new TableViewer(this, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);

        table = viewer.getTable();
        table.setLinesVisible(false);
        table.setHeaderVisible(true);
        
        TableColumn col1 = new TableColumn (table, SWT.NONE);
        col1.setWidth(AbstractWindow.computeWidth(100));
        col1.setMoveable(true);
        TableColumn col2 = new TableColumn (table, SWT.NONE);
        col2.setWidth(AbstractWindow.computeWidth(250));
        col2.setMoveable(true);
        
        // REFRESH DATA
        this.refresh();
    }
    
    public void refresh() {
        table.removeAll();
        if (application.isCurrentObjectTarget()) {
            fillData((FileSystemRecoveryTarget)application.getCurrentTarget());
        } else if (application.isCurrentObjectProcess()) {
            fillData(application.getCurrentTargetGroup());
        }
    }
    
    private void fillData(TargetGroup process) {
        this.addProperty(RM.getLabel("property.element.label"), process.getName());
        if (process.getComments() != null) {
            this.addProperty(RM.getLabel("property.description.label"), process.getComments());
        }
        
        this.addProperty(RM.getLabel("property.source.label"), application.getWorkspace().getPath() + File.separator + process.getSource());
        this.addProperty(RM.getLabel("property.targets.label"), String.valueOf(process.getTargetCount()));
    }
    
    private void fillData(FileSystemRecoveryTarget tg) {
        try {
            String tgName = null;
            if (tg.getTargetName() == null) {
                tgName = RM.getLabel("property.targetwithid.label") + tg.getId();
            } else {
                tgName = tg.getTargetName();
            }
            
            this.addProperty(RM.getLabel("property.element.label"), tgName);
            this.addProperty(RM.getLabel("property.id.label"), String.valueOf(tg.getId()));
            this.addProperty(RM.getLabel("property.uid.label"), String.valueOf(tg.getUid()));

            if (tg.getComments() != null) {
                this.addProperty(RM.getLabel("property.description.label"), tg.getComments());
            }  
            this.addProperty(RM.getLabel("property.source.label"), tg.getSourcesRoot());

            if (tg.getMedium() != null && tg.getMedium() instanceof AbstractIncrementalFileSystemMedium) {
                AbstractIncrementalFileSystemMedium medium = (AbstractIncrementalFileSystemMedium)tg.getMedium();
                
                this.addProperty(RM.getLabel("property.directory.label"), medium.getDisplayArchivePath());
                
                if (medium.isOverwrite()) {
                    this.addProperty(RM.getLabel("property.type.label"), RM.getLabel("targetedition.storagetype.image"));
                } else if (medium.getHandler() instanceof DefaultArchiveHandler) {
                    this.addProperty(RM.getLabel("property.type.label"), RM.getLabel("targetedition.storagetype.multiple"));
                } else {
                    this.addProperty(RM.getLabel("property.type.label"), RM.getLabel("targetedition.storagetype.delta"));                	
                }
            }
        } catch (Throwable e) {
            application.handleException(e);
        }
    }
    
    private void addProperty(String label, String value) {
        TableItem item = new TableItem(table, SWT.NONE);
        item.setText(0, label + " : ");
        item.setText(1, AbstractWindow.configureForTable(value));
    }
}
