package com.application.areca.launcher.gui.common;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.application.areca.ResourceManager;
import com.application.areca.launcher.gui.Application;
import com.application.areca.version.VersionInfos;
import com.myJava.util.os.OSTool;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : -3366468978279844961
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
public abstract class AbstractWindow 
extends ApplicationWindow {

    protected final ResourceManager RM = ResourceManager.instance();
    protected Application application = Application.getInstance(); 
    protected boolean hasBeenUpdated = false;
    protected boolean initialized = false;
    protected Point size;
    
    /**
     * @param parentShell
     */
    public AbstractWindow() {
        super(null);
    }
    
    public static Point computeSize(int linuxW, int linuxH) {
        return new Point(computeWidth(linuxW), computeHeight(linuxH));
    }
    
    public static int computeWidth(int linuxW) {
        if (OSTool.isSystemWindows()) {
            return (int)(linuxW * 0.8);
        } else {
            return linuxW;
        }
    }
    
    public static int computeHeight(int linuxH) {
        if (OSTool.isSystemWindows()) {
            return (int)(linuxH * 0.8);
        } else {
            return linuxH;
        }
    }
    
    protected void configureShell(final Shell shell) {
        super.configureShell(shell);

        if (size != null) {
            shell.setSize(size);
        }
        shell.setText(getFullWindowTitle());
        shell.setImage(ArecaImages.ICO_SMALL);
    }
    
    public boolean close() {
        boolean close = true;
        
        if (hasBeenUpdated) {
            if (checkBusinessRules()) {
                int result = application.showConfirmDialog(
                        RM.getLabel("appdialog.confirmclose.message"),
                        RM.getLabel("appdialog.confirmclose.title"),
                        SWT.YES | SWT.NO | SWT.CANCEL);
   
                if (result == SWT.YES) {
                    saveChanges();
                } else if (result == SWT.CANCEL) {
                    close = false;
                }
            } else {
                if (showCancelMessage() == SWT.NO) {
                    close = false;
                }
            }
        }
        
        if (close) {
            return super.close();
        } else {
            return false;
        }
    }

    protected String getFullWindowTitle() {
        String title = getTitle();
        if (title != null) {
            title = VersionInfos.APP_NAME + " - " + title;
        } else {
            title = VersionInfos.APP_NAME;      
        }

        if (hasBeenUpdated) {
            title += " [" + ResourceManager.instance().getLabel("appdialog.modified.label") + "]";
        }
        
        return title;
    }
    
    public void monitorControl(Text ctrl) {
        ctrl.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                registerUpdate();
            }
        });
    }
    
    public void monitorControl(Button ctrl) {
        monitorControl(SWT.Selection, ctrl);
    }
    
    public void monitorControl(Combo ctrl) {
        monitorControl(SWT.Selection, ctrl);
    }
    
    protected void monitorControl(int swtEventType, Control ctrl) {
        ctrl.addListener(swtEventType, new Listener() {
            public void handleEvent(Event event) {
                registerUpdate();
            }
        });
    }
    
    public void resetErrorState(Control ctrl) {
        ctrl.setBackground(null);
    }
    
    public void setInError(Control ctrl) {
        ctrl.setBackground(Colors.C_FLD_ERROR);        
    }

    protected void constrainShellSize() {
        super.constrainShellSize();
        initialized = true;
        this.updateState(checkBusinessRules());
    }

    protected void registerUpdate() {
        if (this.initialized) {
            // On enregistre l'update
            hasBeenUpdated = true;
            getShell().setText(getFullWindowTitle());
            
            // On met � jour l'�tat du bouton "save"
            this.updateState(checkBusinessRules());
        }
    }  

    public void shellActivated(ShellEvent e) {
        // Nothing
    }

    public void shellClosed(ShellEvent e) {

    }
    
    protected void cancelChanges() {
        boolean close = true;
        if (hasBeenUpdated) {
            if (showCancelMessage() == SWT.NO) {
                close = false;
            }
        }
        
        if (close) {
            hasBeenUpdated = false;
            this.close();
        }
    }

    public void shellDeactivated(ShellEvent e) {
        // Nothing
    }

    public void shellDeiconified(ShellEvent e) {
        // Nothing
    }

    public void shellIconified(ShellEvent e) {
        // Nothing
    }
    
    protected int showCancelMessage() {
        return application.showConfirmDialog(
                RM.getLabel("appdialog.confirmcancel.message"),
                RM.getLabel("appdialog.confirmcancel.title"));
    }
    
    public void setModal(AbstractWindow window) {
        setParentShell(window.getShell());
        setShellStyle(SWT.SHELL_TRIM);
    }
 
    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;
    }
    
    public static boolean getTableLinesVisible() {
        return ! OSTool.isSystemWindows();
    }

    public abstract String getTitle();
    protected abstract boolean checkBusinessRules();  
    protected abstract void updateState(boolean rulesSatisfied);
    protected abstract void saveChanges();
    
    public static String configureForTable(String text) {
        if (OSTool.isSystemWindows()) {
            return text.replace('\n', ' ').replace('\r', ' ');
        } else {
            return text;
        }
    }
}
