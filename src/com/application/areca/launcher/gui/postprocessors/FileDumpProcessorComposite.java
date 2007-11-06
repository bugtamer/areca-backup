package com.application.areca.launcher.gui.postprocessors;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.application.areca.launcher.gui.Application;
import com.application.areca.launcher.gui.ProcessorEditionWindow;
import com.application.areca.processor.FileDumpProcessor;
import com.application.areca.processor.Processor;
import com.myJava.file.FileSystemManager;

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
public class FileDumpProcessorComposite extends AbstractProcessorComposite {

    private Text txtDir;
    private Text txtName;
    private Button btnOnlyError;
    private Button btnListFiltered;
    
    public FileDumpProcessorComposite(Composite composite, Processor proc, final ProcessorEditionWindow window) {
        super(composite, proc, window);
        this.setLayout(new GridLayout(3, false));
        
        Label lblDirectory = new Label(this, SWT.NONE);
        lblDirectory.setText(RM.getLabel("procedition.filedump.label"));
        txtDir = new Text(this, SWT.BORDER);
        txtDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        window.monitorControl(txtDir);
        
        Button btnBrowse = new Button(this, SWT.PUSH);
        btnBrowse.setText(RM.getLabel("common.browseaction.label"));
        btnBrowse.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                String path = Application.getInstance().showDirectoryDialog(txtDir.getText(), window);
                if (path != null) {
                    txtDir.setText(path);
                }
            }
        });
        
        Label lblName = new Label(this, SWT.NONE);
        lblName.setText(RM.getLabel("procedition.filename.label"));
        txtName = new Text(this, SWT.BORDER);
        txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        new Label(this, SWT.NONE);
        window.monitorControl(txtName);
        
        // Example
        new Label(this, SWT.NONE);
        Label lblExample = new Label(this, SWT.NONE);
        lblExample.setText(RM.getLabel("procedition.dynparams.label"));
        lblExample.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        // List filtered entries
        btnListFiltered = new Button(this, SWT.CHECK);
        btnListFiltered.setText(RM.getLabel("procedition.listfiltered.label"));
        btnListFiltered.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
        window.monitorControl(btnListFiltered);
        
        // Send only if in error
        btnOnlyError = new Button(this, SWT.CHECK);
        btnOnlyError.setText(RM.getLabel("procedition.onlyerror.label"));
        btnOnlyError.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
        window.monitorControl(btnOnlyError);
        
        if (proc != null) {
            FileDumpProcessor sProc = (FileDumpProcessor)proc;
            txtDir.setText(sProc.getDestinationFolder().getAbsolutePath());
            txtName.setText(sProc.getReportName());
            btnListFiltered.setSelection(sProc.isListFiltered());
            btnOnlyError.setSelection(sProc.isOnlyIfError());
        }
    }

    public void initProcessor(Processor proc) {
        FileDumpProcessor fProc = (FileDumpProcessor)proc;
        fProc.setDestinationFolder(new File(txtDir.getText()));
        fProc.setReportName(txtName.getText());
        fProc.setOnlyIfError(btnOnlyError.getSelection());
        fProc.setListFiltered(btnListFiltered.getSelection());
    }
    
    public boolean validateParams() {
        window.resetErrorState(txtDir);
        window.resetErrorState(txtName);
        
        // DIRECTORY
        if (
                txtDir.getText() == null 
                || txtDir.getText().trim().length() == 0
                || FileSystemManager.isFile(new File(txtDir.getText()))
        ) {
            window.setInError(txtDir);
            return false;
        }
        
        // NAME
        if (
                txtName.getText() == null 
                || txtName.getText().trim().length() == 0
        ) {
            window.setInError(txtName);
            return false;
        }

        return true;
    }
}
