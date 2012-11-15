package com.application.areca.launcher.gui;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.application.areca.AbstractArecaLauncher;
import com.application.areca.ArecaFileConstants;
import com.application.areca.Utils;
import com.application.areca.launcher.gui.common.ApplicationPreferences;
import com.application.areca.version.VersionInfos;
import com.myJava.file.FileTool;
import com.myJava.system.OSTool;
import com.myJava.util.log.Logger;
import com.myJava.util.taskmonitor.TaskCancelledException;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 *
 */

 /*
 Copyright 2005-2011, Olivier PETRUCCI.

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
public class Launcher extends AbstractArecaLauncher {
	private static String OPT_WORKSPACE = "ws";
	private static String OPT_CONFIG = "cfg";
	
	static {
		AbstractArecaLauncher.setInstance(new Launcher());
	}

	public static void main(String[] args) {   

/*
		try {
			String src = "C:\\Users\\Olivier\\Desktop\\src\\todo_areca_mutate.TXT";
			File f = new File(src);
			long lm = f.lastModified();

			int i1 = Math.abs((int)(10000*Math.random())) % 10;
			int i2 = Math.abs((int)(10000*Math.random())) % 10;
			int i3 = Math.abs((int)(10000*Math.random())) % 10;
			
			String content = "contenu nr " + i1 + "" + i2 + "" + i3;
			FileTool.getInstance().createFile(new File(src), content);
			if (lm > 0) {
				f.setLastModified(lm);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
    	getInstance().launch(args);
    	getInstance().exit();
    }

	private static class Options {
		public String workspace;
		public String configurationDirectory;
		
		public Options(String[] args) {
			for (int i=0; i<args.length; i++) {
				load(args[i]);
			}
		}
		
		private void load(String option) {
			if (option != null && option.trim().length() != 0) {
				String chk;
				
				chk = check(option, OPT_WORKSPACE);
				if (chk != null) {
					workspace = chk;
				} else {
					chk = check(option, OPT_CONFIG);
					if (chk != null) {
						configurationDirectory = chk;
					} else {
						// Default case (backward compatibility)
						workspace = option;
					}
				}
			}
		}
		
		private String check(String option, String prefix) {
			String opt = option.toLowerCase();
			String prf1 = "-" + prefix + "=";
			String prf2 = "-" + prefix + " =";
			
			if (opt.startsWith(prf1)) {
				return option.substring(prf1.length()).trim();
			} else if (opt.startsWith(prf2)) {
				return option.substring(prf2.length()).trim();
			} else {
				return null;
			}
		}
		
		public String toString() {
			String ret = "";
			if (workspace != null) {
				ret += ", Workspace=\"" + workspace + "\"";
			}
			if (configurationDirectory != null) {
				ret += ", Configuration Directory=\"" + configurationDirectory + "\"";
			}
			
			if (ret.length() != 0) {
				return ret.substring(2);
			} else {
				return ret;
			}
		}
	}

    protected void launchImpl(String[] args) {
    	Options opt = new Options(args);
    	if (opt.toString().length() != 0) {
            Logger.defaultLogger().info("Parameters detected : " + opt.toString());
    	}
    	ApplicationPreferences.initialize(opt.configurationDirectory == null ? System.getProperty("user.home") : opt.configurationDirectory);
    	
        boolean killOnError = true;
        try {
            String workspace = null;
            
            if (opt.workspace != null && opt.workspace.trim().length() != 0) {
                workspace = opt.workspace;
            } else {
	            switch (ApplicationPreferences.getStartupMode()) {
	            case ApplicationPreferences.LAST_WORKSPACE_MODE:
	                workspace = ApplicationPreferences.getLastWorkspace();
	                break;
	            case ApplicationPreferences.DEFAULT_WORKSPACE_MODE:
	                workspace = ApplicationPreferences.getDefaultWorkspace();
	                break;
	            }
	            
	            if (workspace == null) {
	                workspace = System.getProperty("user.home") + "/" + ArecaFileConstants.USER_DEFAULT_WORKSPACE;
	            }
            }

            Application gui = Application.getInstance();
            killOnError = false;  // Now that the gui was initialized, don't kill on error 
            gui.show(workspace);
        } catch (Throwable e) {
            e.printStackTrace();
            Logger.defaultLogger().error("Unexpected error", e);

            if (killOnError) {
                Logger.defaultLogger().warn("Critical error during initialization ... exiting.");
                setErrorCode(ERR_UNEXPECTED);
                exit();
            }
        }
    }
    
	protected boolean returnErrorCode() {
		return false;
	}

    protected void checkJavaVersion() {
        if (! OSTool.isJavaVersionGreaterThanOrEquals(VersionInfos.REQUIRED_JAVA_VERSION)) {
            System.out.println(SEPARATOR + "\n ");
            System.out.println(VersionInfos.VERSION_MSG);
            showLine();

            JOptionPane.showMessageDialog(null,
                    VersionInfos.VERSION_MSG, VersionInfos.APP_NAME + " - Invalid Java Version", JOptionPane.ERROR_MESSAGE);

            setErrorCode(ERR_JAVA_VERSION);
            exit();
        }

        if (! VersionInfos.checkJavaVendor()) {
            showLine();
            System.out.println(VersionInfos.VENDOR_MSG);
            showLine();
        }
    }
}