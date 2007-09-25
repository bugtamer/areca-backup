package com.myJava.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import com.myJava.configuration.FrameworkConfiguration;
import com.myJava.util.Utilitaire;
import com.myJava.util.log.Logger;


/**
 * Outil d�di� � la manipulation de fichiers
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

public class FileTool {
    
    private static final long DEFAULT_DELETION_DELAY = FrameworkConfiguration.getInstance().getFileToolDelay();
    private static final int BUFFER_SIZE = 200000; //FrameworkConfiguration.getInstance().getFileToolBufferSize();
    private static final int DELETION_GC_FREQUENCY = (int)(2000 / DEFAULT_DELETION_DELAY);
    private static final int DELETION_MAX_ATTEMPTS = 1000;
    
    private static FileTool instance = new FileTool();
    
    public static FileTool getInstance() {
        return instance;
    }
    
    private FileTool() {
    }
    
    /**
     * Copie le fichier ou r�pertoire source dans le r�pertoire parent destination.
     */
    public void copy(File sourceFileOrDirectory, File targetParentDirectory) throws IOException {
        if (sourceFileOrDirectory == null || targetParentDirectory == null) {
            throw new IllegalArgumentException("Source : " + sourceFileOrDirectory + ", Destination : " + targetParentDirectory);
        }
        
        if (FileSystemManager.isFile(sourceFileOrDirectory)) {
        	this.copyFile(sourceFileOrDirectory, targetParentDirectory);
        } else {
            // Cr�ation du r�pertoire
            File td = new File(targetParentDirectory, FileSystemManager.getName(sourceFileOrDirectory));
            this.createDir(td);
            
            // Copie du contenu de la source dans le r�pertoire nouvellement cr��.
            this.copyDirectoryContent(sourceFileOrDirectory, td);
        }
    }
    
    /**
     * Copie le fichier sourceFile vers le r�pertoire targetDirectory.
     * <BR>Le fichier est d�truit s'il existe d�j�.
     * <BR>
     * @param sourceFile Pointeur sur le fichier � copier
     * @param targetDirectory R�pertoire cible. Si ce r�pertoire n'existe pas, il est cr�� (r�cursivement).
     */
    private void copyFile(File sourceFile, File targetDirectory) throws IOException  {
        copyFile(sourceFile, targetDirectory, FileSystemManager.getName(sourceFile));
    }
    
    /**
     * Copie le fichier sourceFile vers le r�pertoire targetDirectory, sous le nom targetShortFileName.
     * <BR>Le fichier est d�truit s'il existe d�j�.
     * <BR>
     * @param sourceFile Pointeur sur le fichier � copier
     * @param targetDirectory R�pertoire cible. Si ce r�pertoire n'existe pas, il est cr�� (r�cursivement).
     */
    public void copyFile(File sourceFile, File targetDirectory, String targetShortFileName) throws IOException  {
        
        // V�rifications pr�alables
        if (! FileSystemManager.exists(targetDirectory)) {
            this.createDir(targetDirectory);
        }
        
        // Construction du fileOutputStream
        File tf = new File(targetDirectory, targetShortFileName);
        OutputStream outStream = FileSystemManager.getFileOutputStream(tf);
        
        // Copie
        this.copyFile(sourceFile, outStream, true);
    }
    
    /**
     * Copie le fichier sourceFile vers le flux outStream.
     * <BR>closeStream d�termine si le flux de sortie sera ferm� apr�s la copie ou non
     */
    public void copyFile(File sourceFile, OutputStream outStream, boolean closeStream) throws IOException  {      
        this.copy(FileSystemManager.getFileInputStream(sourceFile), outStream, true, closeStream);
    }
    
    /**
     * Copie le flux inStream vers le flux outStream.
     * <BR>inStream est ferm� apr�s copie.
     * <BR>closeStream d�termine si le flux de sortie sera ferm� apr�s la copie ou non
     */
    public void copy(InputStream inStream, OutputStream outStream, boolean closeInputStream, boolean closeOutputStream) throws IOException  {

        try {
            byte[] in = new byte[BUFFER_SIZE];
            int nbRead;
            while (true) {
                nbRead = inStream.read(in);
                if (nbRead == -1) {
                    break;
                }
                outStream.write(in, 0, nbRead);
            }
        } finally {
            try {
                if (closeInputStream) {
                    inStream.close();
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    if (closeOutputStream) {
                        outStream.close();
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }
    
    /**
     * Copie le contenu du r�pertoire sourceDirectory dans le r�pertoire targetDirectory.
     * <BR>Exemple :
     * <BR>Si :
     * <BR>- sourceDirectory = c:\toto\sourceDir
     * <BR>- targetDirectory = d:\myDir
     * <BR>
     * <BR>Alors apr�s la copie, le contenu de c:\toto\sourceDir sera copi� dans d:\myDir
     * <BR>targetDirectory est cr�� (r�cursivement) s'il n'existe pas.
     */
    public void copyDirectoryContent(File sourceDirectory, File targetDirectory) throws IOException {
        if (! FileSystemManager.exists(targetDirectory)) {
            this.createDir(targetDirectory);
        }
        
        // Copie du contenu
        File[] files = FileSystemManager.listFiles(sourceDirectory);
        for (int i=0; i<files.length; i++) {
        	this.copy(files[i], targetDirectory);
        }
    }
    
    /**
     * DEPLACE le contenu du r�pertoire source vers le r�pertoire destination
     * (tous les fichiers et sous r�pertoires de la source sont d�plac�s
     * dans le r�pertoire destination).
     * <BR>Les fichiers existants sont �cras�s.
     * <BR>Si le bool�en "waitForAvailability" est activ�, le processus attendra, pour chaque fichier ou r�pertoire
     * que celui ci soit disponible pour le d�placer (mise en attente du thread).
     * 
     * @param sourceDirectory
     * @param destinationDirectory
     * @throws IOException
     */
    public void moveDirectoryContent(File sourceDirectory, File destinationDirectory, boolean waitForAvailability) throws IOException {
        if (! FileSystemManager.exists(destinationDirectory)) {
            this.createDir(destinationDirectory);
        }
        
        // D�placement du contenu
        File[] files = FileSystemManager.listFiles(sourceDirectory);
        for (int i=0; i<files.length; i++) {
            this.move(files[i], destinationDirectory, waitForAvailability);
        }
    }
    
    public void move(File sourceFileOrDirectory, File targetParentDirectory, boolean waitForAvailability) throws IOException {
        // Cr�ation du r�pertoire d'accueil si n�cessaire
        if (! FileSystemManager.exists(targetParentDirectory)) {
            this.createDir(targetParentDirectory);
        }
        
        File destFile = new File(targetParentDirectory, FileSystemManager.getName(sourceFileOrDirectory));
        
        // D�placement
        if (! FileSystemManager.renameTo(sourceFileOrDirectory, destFile)) {

        	// Si la tentative standard �choue (m�thode "renameTo"), on tente une copie, puis suppression
        	this.copy(sourceFileOrDirectory, targetParentDirectory);
        	this.delete(sourceFileOrDirectory, waitForAvailability);
        }
    }
    
    /**
     * Supprime le r�pertoire et tout son contenu, r�cursivement, ou le fichier s'il s'agit d'un fichier.
     * <BR>Si le bool�en "waitForAvailability" est activ�, le processus attendra, pour chaque fichier ou r�pertoire
     * que celui ci soit disponible pour le supprimer (mise en attente du thread).
     * <BR>Une tentative de suppression sera faite toutes les "deletionDelay" millisecondes.
     */
    public void delete(File fileOrDirectory, boolean waitForAvailability, long deletionDelay) 
    throws IOException, IllegalArgumentException {
        if (FileSystemManager.isDirectory(fileOrDirectory)) {
            // Suppression du contenu
            File[] files = FileSystemManager.listFiles(fileOrDirectory);
            for (int i=0; i<files.length; i++) {
                this.delete(files[i], waitForAvailability, deletionDelay);
            }
        }

        if (waitForAvailability) {
            long retry = 0;
            try {
                while (! FileSystemManager.delete(fileOrDirectory)) {
                    retry++;
                    if (retry == 10 || retry == 100 || retry == 1000) {
                        Logger.defaultLogger().warn("Attempted to delete file (" + FileSystemManager.getAbsolutePath(fileOrDirectory) + ") during " + (retry * deletionDelay) + " ms but it seems to be locked !");
                    }
                    if (retry >= DELETION_MAX_ATTEMPTS) {
                        throw new IOException("Unable to delete file : " + FileSystemManager.getAbsolutePath(fileOrDirectory) + " - isFile=" + FileSystemManager.isFile(fileOrDirectory) + " - Exists="  + FileSystemManager.exists(fileOrDirectory));
                    }
                    if (retry%DELETION_GC_FREQUENCY == 0) {
                        Logger.defaultLogger().warn("File deletion (" + FileSystemManager.getAbsolutePath(fileOrDirectory) + ") : Performing a GC.");
                        System.gc(); // I know it's not very beautiful ... but it seems to be a bug with old file references (even if all streams are closed)
                    }
                    Thread.sleep(deletionDelay);
                }
            } catch (InterruptedException ignored) {
            }
        } else {
            FileSystemManager.delete(fileOrDirectory);
        }
    }
    
    public void delete(File fileOrDirectory, boolean waitForAvailability) throws IOException {
        delete(fileOrDirectory, waitForAvailability, DEFAULT_DELETION_DELAY);
    }
    
    /**
     * Retourne le contenu int�gral du fichier pass� en argument sous forme de cha�ne de
     * caract�res.
     */
    public String getFileContent(File sourceFile) throws IOException {
        InputStream inStream = FileSystemManager.getFileInputStream(sourceFile);
        return getInputStreamContent(inStream, true);
    }
    

    public String getInputStreamContent(InputStream inStream, boolean closeStreamOnExit) throws IOException {
        return getInputStreamContent(inStream, null, closeStreamOnExit);
    }
    
    /**
     * Retourne le contenu int�gral du stream pass� en argument sous forme de cha�ne de
     * caract�res.
     */
    public String getInputStreamContent(InputStream inStream, String encoding, boolean closeStreamOnExit) throws IOException {
    	if (inStream == null) {
    		return null;
    	}
    	
        StringBuffer content = new StringBuffer();
        try {
            InputStreamReader reader = encoding == null ? new InputStreamReader(inStream) : new InputStreamReader(inStream, encoding);            
            int c;
            while ((c = reader.read()) != -1) {
                content = content.append((char)c);
            }
        } finally {
            try {
                if (closeStreamOnExit) {
                    inStream.close();
                }
            } catch (Exception ignored) {
            }
        }
        return new String(content);
    }

    public void createFile(File destinationFile, String content) throws IOException {
        OutputStream fos = FileSystemManager.getFileOutputStream(destinationFile);
        OutputStreamWriter fw = new OutputStreamWriter(fos);
        fw.write(content);
        fw.flush();
        fw.close();
    }
    
    /**
     * Retourne le contenu du fichier sous forme de tableau de String.
     * <BR>Un String par ligne.
     * <BR>Les espaces superflus sont supprim�s et les lignes vides sont ignor�es.
     */
    public String[] getFileRows(File sourceFile) throws IOException {
        return parseStreamContent(this.getFileContent(sourceFile));
    }
    
    /**
     * Retourne le contenu du flux sous forme de tableau de String.
     * <BR>Un String par ligne.
     * <BR>Les espaces superflus sont supprim�s et les lignes vides sont ignor�es.
     */
    public String[] getInputStreamRows(InputStream stream, String encoding, boolean closeStreamOnExit) throws IOException {
        return parseStreamContent(this.getInputStreamContent(stream, encoding, closeStreamOnExit));
    }
    
    public String[] getInputStreamRows(InputStream stream, boolean closeStreamOnExit) throws IOException {
        return getInputStreamRows(stream, null, closeStreamOnExit);
    }
    
    public String getFirstRow(InputStream stream, String encoding) throws IOException {
        BufferedReader reader = null;
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(stream, encoding));
            line = reader.readLine();
        } finally {
            if (reader != null) {
                reader.close();
            } else if (stream != null) {
                stream.close();
            }
        }
        return line;
    }
    
    /**
     * Retourne le contenu sous forme de tableau de String.
     * <BR>Un String par ligne.
     * <BR>Les espaces superflus sont supprim�s et les lignes vides sont ignor�es.
     */
    private String[] parseStreamContent(String content) {
    	if (content == null) {
    		return new String[0];
    	}
    	
        StringTokenizer stt = new StringTokenizer(content, "\n");
        String elt = "";
        Vector v = new Vector();
        
        while (stt.hasMoreElements()) {
            elt = stt.nextToken().trim();
            if (elt != null && ! elt.equalsIgnoreCase("")) {
                v.addElement(elt);
            }
        }
        
        return Utilitaire.vectorToStringArray(v);
    }
    
    /**
     * Remplace toutes les occurences de "searchString" par "newString" dans le fichier sp�cifi�
     */
    public void replaceInFile(File baseFile, String searchString, String newString) throws IOException {
        String content = this.getFileContent(baseFile);
        content = Utilitaire.replace(content, searchString, newString);
        OutputStreamWriter fw = null;
        try {
            OutputStream fos = FileSystemManager.getFileOutputStream(FileSystemManager.getAbsolutePath(baseFile));
            fw = new OutputStreamWriter(fos);
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            fw.close();
        }
    }
    
    /**
     * Retourne "true" si le fichier contient "searchString" ... retourne false sinon.
     */
    public boolean checkContains(File baseFile, String searchString) throws IOException {
        String content = this.getFileContent(baseFile);
        return (content.indexOf(searchString) != -1);
    }
    
    /**
     * Returns true if "parent" contains or equals to "child"
     * @param parent
     * @param child
     * @return
     */
    public boolean isParentOf(File parent, File child) {
        if (child == null || parent == null) {
            return false;
        } else if (FileSystemManager.getAbsoluteFile(parent).equals(FileSystemManager.getAbsoluteFile(child))){
            return true;
        } else {
            return this.isParentOf(parent, FileSystemManager.getParentFile(child)); 
        }
    }
    
    /**
     * Returns the file's or directory's total length.
     */
    public long getSize(File fileOrDirectory) throws FileNotFoundException {
        if (FileSystemManager.isFile(fileOrDirectory)) {
            return FileSystemManager.length(fileOrDirectory);
        } else {
            File[] content = FileSystemManager.listFiles(fileOrDirectory);
            long l = 0;
            
            for (int i=0; i<content.length; i++) {
                l += getSize(content[i]);
            }
            
            return l;
        }
    }
    
    /**
     * Cr�ation r�cursive d'un r�pertoire
     * 
     * @param directory
     * @throws IOException
     */
    public void createDir(File directory) throws IOException {
        if (directory == null || FileSystemManager.exists(directory)) {
            return;
        } else {
            createDir(FileSystemManager.getParentFile(directory));
            FileSystemManager.mkdir(directory);
        }
    }
}