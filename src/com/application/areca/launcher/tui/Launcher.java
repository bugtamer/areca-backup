package com.application.areca.launcher.tui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.application.areca.AbstractRecoveryTarget;
import com.application.areca.ArecaTechnicalConfiguration;
import com.application.areca.RecoveryProcess;
import com.application.areca.UserInformationChannel;
import com.application.areca.adapters.ProcessXMLReader;
import com.application.areca.context.ProcessContext;
import com.application.areca.launcher.CommandConstants;
import com.application.areca.launcher.InvalidCommandException;
import com.application.areca.launcher.UserCommand;
import com.application.areca.version.VersionInfos;
import com.myJava.file.FileSystemManager;
import com.myJava.util.CalendarUtils;
import com.myJava.util.Utilitaire;
import com.myJava.util.log.FileLogProcessor;
import com.myJava.util.log.Logger;
import com.myJava.util.os.OSTool;

/**
 * Launcher
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
public class Launcher implements CommandConstants {
    public static String SEPARATOR = "------------------------------------------------------------------";
    private static UserInformationChannel channel = new LoggerUserInformationChannel(false);
    
    /**
     * Méthode principale de lancement.
     * @param args
     */
    public static void main(String[] args) {
        checkJavaVersion();
        ArecaTechnicalConfiguration.initialize();
        UserCommand command = null;
        try {
            command = new UserCommand(args);
            command.parse();
            
            // On logge dans le répertoire du fichier de config
            if (command.hasOption(OPTION_CONFIG)) {
                File f = new File(command.getOption(OPTION_CONFIG));
                if (FileSystemManager.exists(f)) {
        	        Logger.defaultLogger().removeAllProcessors();
                    File configFile = new File(Utilitaire.replace(command.getOption(OPTION_CONFIG), ".xml", ""));
                    File logFile = new File(
                            FileSystemManager.getParentFile(configFile) + "/log/",
                            FileSystemManager.getName(configFile)
                    );
        	        FileLogProcessor proc = new FileLogProcessor(logFile);
        	        Logger.defaultLogger().addProcessor(proc);
                }
            }
            
            Logger.defaultLogger().info("Starting the process ... config = [" + command.getOption(OPTION_CONFIG) + "].");
            channel.print("Starting the process ... config = [" + command.getOption(OPTION_CONFIG) + "].");
            
            ProcessXMLReader adapter = new ProcessXMLReader(command.getOption(OPTION_CONFIG));
            adapter.setMissingDataListener(new MissingDataListener());
            RecoveryProcess process = adapter.load();
            AbstractRecoveryTarget target = null;
            if (command.hasOption(OPTION_TARGET)) {
                target = getTarget(process, command.getOption(OPTION_TARGET));
            }
            ProcessContext context = new ProcessContext(target, channel);
            
            if (command.getCommand().equalsIgnoreCase(COMMAND_COMPACT)) {
                processCompact(command, process, context);
            } else if (command.getCommand().equalsIgnoreCase(COMMAND_RECOVER)) {
                processRecover(command, process, context);
            } else if (command.getCommand().equalsIgnoreCase(COMMAND_BACKUP)) {
                processBackup(command, process, context);
            } else if (command.getCommand().equalsIgnoreCase(COMMAND_DESCRIBE)) {
                processDescribe(command, process, context);
            } else if (command.getCommand().equalsIgnoreCase(COMMAND_DELETE)) {
                processDelete(command, process, context);
            }
            
            Logger.defaultLogger().info("End of process.");
            channel.print("End of process.");
            
        } catch (InvalidCommandException e) {
            channel.print(SEPARATOR);
            channel.print("File Backup Software (Copyright 2005-2007, Olivier PETRUCCI)");
            channel.print(SEPARATOR);
            channel.print("Syntax   : (command) (options)");
            channel.print("Commands : describe / backup / merge / delete / recover");
            channel.print("Options (describe): -config (your xml config file)");
            channel.print("Options (backup)  : -config (your xml config file) [-target (specific target)]");
            channel.print("Options (merge) : -config (your xml config file) -target (specific target) [-date (recovery date : YYYY-MM-DD) / -delay (nr of days)]");
            channel.print("Options (delete) : -config (your xml config file) -target (specific target) [-date (recovery date : YYYY-MM-DD) / -delay (nr of days)]");
            channel.print("Options (recover) : -config (your xml config file) -target (specific target) -destination (destination folder) [-date (recovery date : YYYY-MM-DD)]");
            channel.print(SEPARATOR);
            channel.print("Invalid Command : " + command.toString());
            channel.print(e.getMessage());
            channel.print(SEPARATOR);
        } catch (Throwable e) {
            handleError(e);
        }
    }
    
    private static void handleError(Throwable e) {
        channel.print(SEPARATOR);
        channel.print("An error occured during the process : " + e.getMessage());
        channel.print("Please refer to the log file : " + ((FileLogProcessor)Logger.defaultLogger().find(FileLogProcessor.class)).getCurrentLogFile());
        channel.print(SEPARATOR);
        
        // On logge tout systématiquement.
        Logger.defaultLogger().error(e);
    }
    
    /**
     * Processus de backup
     *
     * @param command
     * @param process
     */
    private static void processBackup(UserCommand command, final RecoveryProcess process, final ProcessContext context) throws Exception {
        if (command.hasOption(OPTION_TARGET)) {
            AbstractRecoveryTarget target = getTarget(process, command.getOption(OPTION_TARGET));
            process.processBackupOnTarget(
                    target,
                    context
            );
        } else {
            List thList = new ArrayList();
            
            Iterator iter = process.getTargetIterator();
            while (iter.hasNext()) {
                final AbstractRecoveryTarget tg = (AbstractRecoveryTarget)iter.next();
                final ProcessContext cloneCtx = new ProcessContext(tg, new LoggerUserInformationChannel(true));
                Thread th = new Thread(new Runnable() {
                    public void run() {
                        try {
                            process.processBackupOnTarget(
                                    tg,
                                    cloneCtx
                            );
                        } catch (Exception e) {
                            handleError(e);
                        }
                    }
                });
                th.setName("Backup on " + tg.getTargetName());
                th.setDaemon(false);
                thList.add(th);
                th.start();
            }
            
            // Wait for all threads to die
            Iterator thIter = thList.iterator();
            while (thIter.hasNext()) {
                Thread th = (Thread) thIter.next();
                th.join();
            }
        }
    }
    
    /**
     * Merges the archives.
     *
     * @param command
     * @param process
     */
    private static void processCompact(UserCommand command, RecoveryProcess process, ProcessContext context) throws Exception {
        String strDelay = command.getOption(OPTION_DELAY);
        AbstractRecoveryTarget target =getTarget(process, command.getOption(OPTION_TARGET));
        if (strDelay != null) {
            // A delay (in days) is provided
            process.processCompactOnTarget(
                    target,
                    Integer.parseInt(strDelay),
                    context
            );
        } else {
            // A full date is provided
            process.processCompactOnTarget(
                    target,
                    null,
                    CalendarUtils.resolveDate(command.getOption(OPTION_DATE), null),
                    null,
                    context
            );
        }
    }
    
    /**
     * Merges the archives.
     *
     * @param command
     * @param process
     */
    private static void processDelete(UserCommand command, RecoveryProcess process, ProcessContext context) throws Exception {
        String strDelay = command.getOption(OPTION_DELAY);
        AbstractRecoveryTarget target =getTarget(process, command.getOption(OPTION_TARGET));
        if (strDelay != null) {
            // A delay (in days) is provided
            process.processDeleteOnTarget(
                    target,
                    Integer.parseInt(strDelay),
                    context
            );
        } else {
            // A full date is provided
            process.processDeleteOnTarget(
                    target,
                    CalendarUtils.resolveDate(command.getOption(OPTION_DATE), null),
                    context
            );
        }
    }
    
    /**
     * Processus de chargement d'une archive
     *
     * @param command
     * @param process
     */
    private static void processRecover(UserCommand command, RecoveryProcess process, ProcessContext context) throws Exception {
        AbstractRecoveryTarget target =getTarget(process, command.getOption(OPTION_TARGET));
        process.processRecoverOnTarget(
                target,
                null,
                command.getOption(OPTION_DESTINATION),
                CalendarUtils.resolveDate(command.getOption(OPTION_DATE), null),
                false, 
                context
        );
    }
    
    /**
     * Processus de description d'un fichier de config
     *
     * @param command
     * @param process
     */
    private static void processDescribe(UserCommand command, RecoveryProcess process, ProcessContext context) throws Exception {
        channel.print(process.getDescription());
    }
    
    /**
     * Retourne la target demandée.
     *
     * @param process
     * @param targetId
     * @return
     * @throws InvalidCommandException
     */
    private static AbstractRecoveryTarget getTarget(RecoveryProcess process, String targetId) throws InvalidCommandException {
        AbstractRecoveryTarget target = process.getTargetById(Integer.parseInt(targetId));
        if (target == null) {
            throw new InvalidCommandException("Invalid target ID : [" + targetId + "]");
        } else {
            return target;
        }
    }
    
    private static void checkJavaVersion() {
        if (!
                OSTool.isJavaVersionGreaterThanOrEquals(VersionInfos.REQUIRED_JAVA_VERSION)
        ) {
            System.out.println(SEPARATOR + "\n ");
            System.out.println(VersionInfos.VERSION_MSG);
            System.out.println(SEPARATOR);
            System.exit(-1);
        }
        
        if (! VersionInfos.checkJavaVendor()) {
            System.out.println(SEPARATOR);
            System.out.println(VersionInfos.VENDOR_MSG);
            System.out.println(SEPARATOR);
        }
    }
}

