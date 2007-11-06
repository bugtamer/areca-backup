package com.application.areca.launcher.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.application.areca.AbstractRecoveryTarget;
import com.application.areca.ResourceManager;
import com.application.areca.launcher.gui.postprocessors.AbstractProcessorComposite;
import com.application.areca.launcher.gui.postprocessors.DeleteProcessorComposite;
import com.application.areca.launcher.gui.postprocessors.FileDumpProcessorComposite;
import com.application.areca.launcher.gui.postprocessors.MailSendProcessorComposite;
import com.application.areca.launcher.gui.postprocessors.MergeProcessorComposite;
import com.application.areca.launcher.gui.postprocessors.ShellScriptProcessorComposite;
import com.application.areca.processor.DeleteProcessor;
import com.application.areca.processor.FileDumpProcessor;
import com.application.areca.processor.MailSendProcessor;
import com.application.areca.processor.MergeProcessor;
import com.application.areca.processor.Processor;
import com.application.areca.processor.ShellScriptProcessor;

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
public class ProcessorRepository {
    private static String K_MERGE = "merge";
    private static String K_SHELL = "shell";
    private static String K_DUMP = "dump";
    private static String K_MAIL = "mail";
    private static String K_DELETE = "delete";
    
    private static final ResourceManager RM = ResourceManager.instance();
    
    public static List getProcessors(boolean preProcess) {
        ArrayList list = new ArrayList();
        list.add(K_SHELL);
        list.add(K_MERGE);

        if (preProcess) {
            list.add(K_DELETE);            
        } else {
            list.add(K_DUMP);
            list.add(K_MAIL);
        }
        return list;
    }
    
    private static String getKey(int i, List procs) {
        if (i == -1) {
            return null;
        } else {
            return (String)procs.get(i);
        }
    }
    
    public static AbstractProcessorComposite buildProcessorComposite(
            int index, 
            List procs,
            Composite composite,
            Processor proc, 
            ProcessorEditionWindow frm
    ) {
        String key = getKey(index, procs);
        AbstractProcessorComposite pnl = null;
        if (key == K_DUMP) {
            pnl = new FileDumpProcessorComposite(composite, proc, frm);
        } else if (key == K_MAIL){
            pnl = new MailSendProcessorComposite(composite, proc, frm);
        } else if (key == K_SHELL){
            pnl = new ShellScriptProcessorComposite(composite, proc, frm);
        } else if (key == K_MERGE){
            pnl = new MergeProcessorComposite(composite, proc, frm);
        } else if (key == K_DELETE){
            pnl = new DeleteProcessorComposite(composite, proc, frm);
        }
        
        return pnl;
    }
    
    public static Processor buildProcessor(int index, List procs, AbstractRecoveryTarget target) {
        String key = getKey(index, procs);
        Processor proc = null;
        if (key == K_DUMP) {
            proc = new FileDumpProcessor();
        } else if (key == K_MAIL){
            proc = new MailSendProcessor();
        } else if (key == K_SHELL){
            proc = new ShellScriptProcessor();
        } else if (key == K_MERGE){
            proc = new MergeProcessor();
        } else if (key == K_DELETE){
            proc = new DeleteProcessor();            
        }
        
        return proc;
    }
    
    public static String getKey(Processor proc) {
        if (proc == null) {
        }
        
        if (ShellScriptProcessor.class.isAssignableFrom(proc.getClass())) {
            return K_SHELL;
        } else if (MailSendProcessor.class.isAssignableFrom(proc.getClass())) {
            return K_MAIL;
        } else if (FileDumpProcessor.class.isAssignableFrom(proc.getClass())) {
            return K_DUMP;         
        } else if (MergeProcessor.class.isAssignableFrom(proc.getClass())) {
            return K_MERGE;         
        } else if (DeleteProcessor.class.isAssignableFrom(proc.getClass())) {
            return K_DELETE;  
        }
        
        return null;
    }
    
    public static int getIndex(Processor proc, List procs) {
        if (proc == null) {
            return 0;
        }
        
        String key = getKey(proc);
        for (int i=0; i<procs.size(); i++) {
            if (procs.get(i).equals(key)) {
                return i;
            }
        }
    	
    	return 0;
    }
    
    public static String getName(Processor proc) {
        return RM.getLabel("procedition." + getKey(proc) + ".label");
    }
    
    
    public static String getName(String key) {
        return RM.getLabel("procedition." + key + ".label");
    }
}
