package com.application.areca.launcher.gui.postprocessors;

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
import com.application.areca.postprocess.PostProcessor;
import com.application.areca.postprocess.ShellScriptPostProcessor;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : -4899974077672581254
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
public class ShellScriptProcessorComposite extends AbstractProcessorComposite {

    private Text txtScript;
    private Text txtParams;
    
    public ShellScriptProcessorComposite(Composite composite, PostProcessor proc, final ProcessorEditionWindow window) {
        super(composite, proc, window);
        this.setLayout(new GridLayout(3, false));
        
        Label lblScript = new Label(this, SWT.NONE);
        lblScript.setText(RM.getLabel("procedition.scriptfile.label"));
        
        txtScript = new Text(this, SWT.BORDER);
        txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        window.monitorControl(txtScript);
        
        Button btnBrowse = new Button(this, SWT.PUSH);
        btnBrowse.setText(RM.getLabel("common.browseaction.label"));
        btnBrowse.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                String path = Application.getInstance().showFileDialog(txtScript.getText(), window);
                if (path != null) {
                    txtScript.setText(path);
                }
            }
        });
        
        Label lblParams = new Label(this, SWT.NONE);
        lblParams.setText(RM.getLabel("procedition.scriptparams.label"));
        
        txtParams = new Text(this, SWT.BORDER);
        txtParams.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        window.monitorControl(txtParams);
        
        Label lbl1 = new Label(this, SWT.NONE);
        Label lblExample = new Label(this, SWT.NONE);
        lblExample.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        lblExample.setText(RM.getLabel("procedition.scriptparams.example.label"));
        
        if (proc != null) {
            ShellScriptPostProcessor sProc = (ShellScriptPostProcessor)proc;
            txtScript.setText(sProc.getCommand());
            txtParams.setText(sProc.getCommandParameters() == null ? "" : sProc.getCommandParameters());
        }
    }

    public void initProcessor(PostProcessor proc) {
        ShellScriptPostProcessor fProc = (ShellScriptPostProcessor)proc;
        fProc.setCommand(txtScript.getText());
        fProc.setCommandParameters(txtParams.getText());
    }
    
    public boolean validateParams() {
        window.resetErrorState(txtScript);
        window.resetErrorState(txtParams);
        
        if (
                txtScript.getText() == null 
                || txtScript.getText().trim().length() == 0 
        ) {
            window.setInError(txtScript);
            return false;
        }
        
        if (txtParams.getText() != null) {
            if (txtParams.getText().indexOf('\"') != -1) {
                window.setInError(txtParams);
                return false;
            }
        }

        return true;
    }
}
