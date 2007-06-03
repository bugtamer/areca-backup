package com.application.areca.impl.policy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.application.areca.ApplicationException;
import com.application.areca.ArchiveMedium;
import com.myJava.file.FileSystemDriver;
import com.myJava.file.ftp.FTPFileInfoCache;
import com.myJava.file.ftp.FTPFileSystemDriver;
import com.myJava.file.ftp.FTPProxy;
import com.myJava.file.ftp.FictiveFile;
import com.myJava.util.PublicClonable;
import com.myJava.util.ToStringHelper;
import com.myJava.util.log.Logger;
import com.myJava.util.os.OSTool;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 2162742295696737000
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
public class FTPFileSystemPolicy 
extends AbstractFileSystemPolicy
implements FileSystemPolicy {

    private static final String LOCAL_DIR_PREFIX;
    public static final int DEFAULT_PORT = 21;
    
    private String remoteServer;
    private int remotePort = DEFAULT_PORT;
    private String login;
    private String password;
    private boolean passivMode;
    private String protocol = null;
    private boolean implicit = false;
    private String remoteDirectory;
    private ArchiveMedium medium;
    
    static {
        if (OSTool.isSystemWindows()) {
            LOCAL_DIR_PREFIX = "C:\\ftp\\";
        } else {
            LOCAL_DIR_PREFIX = "/ftp/";
        }
    }

    public void synchronizeConfiguration() {
    }
    
    public void validate(boolean extendedTests) throws ApplicationException {
        if (extendedTests) {
            validateExtended();
        } else {
            validateSimple();
        }
    }

    public ArchiveMedium getMedium() {
        return medium;
    }
    
    public void setMedium(ArchiveMedium medium) {
        this.medium = medium;
    }
    
    public void validateSimple() throws ApplicationException {
        FTPProxy px = buildProxy();
        px.setFileInfoCache(new FTPFileInfoCache());
        
        try {
            px.acquireLock("Basic FTP connection test.");
            px.noop();
        } catch (Exception e) {
            Logger.defaultLogger().error(e);
            throw new ApplicationException("Invalid FTP Parameters - Got the following error : \n" + e.getMessage());
        } finally {
            px.disconnect();
        }
    }
    
    public void validateExtended() throws ApplicationException {
        FTPProxy px = buildProxy();
        px.setFileInfoCache(new FTPFileInfoCache());
        
        Logger.defaultLogger().info("Testing FTP policy : " + this.toString() + " ...");
        int maxProxies = FTPFileSystemDriver.MAX_PROXIES - 1;
        
        Logger.defaultLogger().info("Making tests with " + maxProxies + " concurrent connections ...");
        
        FTPProxy[] pxs = new FTPProxy[maxProxies];
        long[] cnxIds = new long[maxProxies];
        for (int i=0; i<pxs.length; i++) {
            pxs[i] = px.cloneProxy();
            pxs[i].acquireLock("Proxy Test #" + i);
        }
        
        try {
            Logger.defaultLogger().info("Connecting proxies ...");
            for (int i=0; i<pxs.length; i++) {
                pxs[i].connect();
                cnxIds[i] = pxs[i].getConnectionId();
            }
            
            for (int i=0; i<pxs.length; i++) {
                Logger.defaultLogger().info("*** Testing Proxy #" + i + " ***");
                testProxy(pxs[i]);
                Logger.defaultLogger().info("Proxy #" + i + " OK.");
            }
            
            Logger.defaultLogger().info("Checking potential disconnections ...");
            for (int i=0; i<pxs.length; i++) {
                pxs[i].noop();
                if (pxs[i].hasReconnectSince(cnxIds[i])) {
                    throw new ApplicationException("Disconnection detected for proxy #" + i);
                }
            }
            
            Logger.defaultLogger().info("FTP Connection test successfull.");
        } catch (Throwable e) {
            Logger.defaultLogger().error("Error during FTP connextion test procedure", e);
            if (e instanceof ApplicationException) {
                throw (ApplicationException)e;
            } else {
                throw new ApplicationException(e.getMessage());
            }
        } finally {
            String msg = null;
            for (int i=0; i<maxProxies; i++) {
	            try {
	                pxs[i].disconnect();
	            } catch (Exception ex) { 
	                msg = ex.getMessage();
	            }
            }
            if (msg != null) {
                throw new ApplicationException("Error during disconnection : " + msg);
            }
        }
    }
    
    private void testProxy(FTPProxy px) throws Throwable {        
        // NOOP
        Logger.defaultLogger().info("Testing noop ...");            
        px.noop();
        
        // GET FILE INFOS
        Logger.defaultLogger().info("Getting remote directory informations ...");
        FictiveFile file = px.getRemoteFileInfos(remoteDirectory);
        Logger.defaultLogger().info("Remote Directory : " + file.toString());

        if (! file.exists()) {
            Logger.defaultLogger().error("Remote directory does not exist.", "FTPFileSystemPolicy.validate()");
            throw new ApplicationException("FTP Remote directory does not exist");
        }
        
        String subdir = this.remoteDirectory;
        if ((! subdir.endsWith("/")) && (! subdir.endsWith("\\"))) {
            subdir += "/";
        }
        subdir += "ftptest";
        String testFile = subdir + "/filetest.txt";
        String fileContent = "0123456789 0123456789 0123456789 0123456789 0123456789 ";
        
        // CREATE SUBDIRECTORY
        Logger.defaultLogger().info("Testing subdirectory creation ...");
        px.mkdir(subdir);
        FictiveFile sd = px.getRemoteFileInfos(subdir);
        if (! sd.exists()) {
            throw new ApplicationException("Invalid FTP Server : Unable to create subdirectories.");
        }
        
        // CREATE A FILE WITH SIMULTANEOUS "LS" CHGWORKINGDIR
        Logger.defaultLogger().info("Testing file creation ...");
        FTPProxy pxClone = px.cloneProxy();
        try {
            pxClone.acquireLock("outputStream");
            OutputStream out = pxClone.getFileOutputStream(testFile);
            out.write(fileContent.getBytes());
            
            // Concurrent instructions
            px.getRemoteFileInfos(testFile);
            px.getRemoteFileInfos(testFile + ".non_existing");
            
            // Write addtional content
            out.write(fileContent.getBytes());            
            out.close();
        } finally {
            pxClone.disconnect();
        }
        sd = px.getRemoteFileInfos(testFile);
        if (! sd.exists()) {
            throw new ApplicationException("Invalid FTP Server : Unable to create files.");
        }
        
        // READ FILE
        Logger.defaultLogger().info("Testing file reading ...");
        pxClone = px.cloneProxy();
        try {
            pxClone.acquireLock("inputStream");
            BufferedReader reader = new BufferedReader(new InputStreamReader(pxClone.getFileInputStream(testFile)));
            String result = reader.readLine();
            reader.close();
            if (result == null || (! result.equals(fileContent + fileContent))) {
                Logger.defaultLogger().error("Invalid FTP Server : Unable to read files : [" + result + "] was read instead of [" + fileContent + fileContent + "]", "FTPFileSystemPolicy.validate()");
                throw new ApplicationException("Invalid FTP Server : Unable to read and validate created files.");
            }
        } finally {
            pxClone.disconnect();
        }
        
        // DELETE FILE
        Logger.defaultLogger().info("Testing file deletion ...");
        px.delete(testFile);
        sd = px.getRemoteFileInfos(testFile);
        if (sd.exists()) {
            throw new ApplicationException("Invalid FTP Server : Unable to delete created files.");
        }
        
        // DESTROY SUBDIRECTORY
        Logger.defaultLogger().info("Testing subdirectory deletion ...");
        px.delete(subdir);
        sd = px.getRemoteFileInfos(subdir);
        if (sd.exists()) {
            throw new ApplicationException("Invalid FTP Server : Unable to delete created directories.");
        }
    }
    
    public String getBaseArchivePath() {
        return LOCAL_DIR_PREFIX + getUid() + "/storage_" + getUid() + "/" + getArchivePrefix();
    }
    
    public boolean isSecured() {
        return protocol != null && protocol.length() != 0;
    }
    
    private File getLocalDirectory() {
        return new File(LOCAL_DIR_PREFIX + getUid());
    }
    
    public String getDisplayableParameters() {
        StringBuffer sb = new StringBuffer();
        if (isSecured()) {
            sb.append("ftps://");            
        } else {
            sb.append("ftp://");
        }
        sb.append(login).append(":").append(password).append("@").append(remoteServer).append(":").append(remotePort);
        if (! (remoteDirectory.startsWith("/") || remoteDirectory.startsWith("\\"))) {
            sb.append("/");
        }
        sb.append(remoteDirectory);
        
        return sb.toString();
    }
    
    private FTPProxy buildProxy() {
        FTPProxy proxy = new FTPProxy();
        proxy.setLogin(login);
        proxy.setPassivMode(passivMode);
        proxy.setImpliciteSec(implicit);
        proxy.setProtocol(protocol);
        proxy.setPassword(password);
        proxy.setRemotePort(remotePort);
        proxy.setRemoteServer(remoteServer);
        
        return proxy;
    }

    public FileSystemDriver initFileSystemDriver() throws ApplicationException {
        return new FTPFileSystemDriver(buildProxy(), getLocalDirectory(), getRemoteDirectory());
    }
    
    public PublicClonable duplicate() {
        FTPFileSystemPolicy policy = new FTPFileSystemPolicy();
        policy.setRemoteServer(this.remoteServer);
        policy.setRemotePort(this.remotePort);
        policy.setLogin(this.login);
        policy.setPassword(this.password);
        policy.setPassivMode(this.passivMode);
        policy.setImplicit(this.implicit);
        policy.setProtocol(this.protocol);
        policy.setRemoteDirectory(this.remoteDirectory);
        policy.id = id;
        return policy;
    }
       
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public boolean isPassivMode() {
        return passivMode;
    }
    
    public void setPassivMode(boolean passivMode) {
        this.passivMode = passivMode;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getRemotePort() {
        return remotePort;
    }
    
    public void setRemotePort(int remotePort) {
        if (remotePort == 0) {
            remotePort = DEFAULT_PORT;
        }
        this.remotePort = remotePort;
    }
    
    public String getRemoteServer() {
        return remoteServer;
    }
    
    public void setRemoteServer(String remoteServer) {
        this.remoteServer = remoteServer;
    }

    public String getArchivePrefix() {
        return "bck";
    }
    
    public String getRemoteDirectory() {
        return remoteDirectory;
    }
    
    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }
    
    public String getUid() {
        return medium.getTarget().getUid();
    }

    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isImplicit() {
        return implicit;
    }
    
    public void setImplicit(boolean implicit) {
        this.implicit = implicit;
    }
    
    public String toString() {
        StringBuffer sb = ToStringHelper.init(this);
        ToStringHelper.append("SERVER", this.remoteServer, sb);
        ToStringHelper.append("PORT", this.remotePort, sb);
        ToStringHelper.append("LOGIN", this.login, sb);
        ToStringHelper.append("PASSWORD", this.password, sb);
        ToStringHelper.append("PASSIV", this.passivMode, sb);
        ToStringHelper.append("PROTOCOL", this.protocol, sb);
        ToStringHelper.append("IMPLICIT", this.implicit, sb);
        ToStringHelper.append("DIRECTORY", this.remoteDirectory, sb);
        return ToStringHelper.close(sb);
    }
}
