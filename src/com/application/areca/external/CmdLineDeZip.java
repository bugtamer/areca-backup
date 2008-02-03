package com.application.areca.external;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.application.areca.AbstractArecaLauncher;
import com.myJava.commandline.CmdLineParserException;
import com.myJava.commandline.CommandLineParser;
import com.myJava.commandline.StringCmdLineOption;
import com.myJava.file.CompressionArguments;
import com.myJava.file.FileSystemManager;
import com.myJava.file.FileTool;
import com.myJava.file.archive.ArchiveReader;
import com.myJava.file.archive.zip64.ZipArchiveAdapter;
import com.myJava.file.archive.zip64.ZipVolumeStrategy;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 8290826359148479344
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
public class CmdLineDeZip
extends AbstractArecaLauncher {
    
    private static final String ARG_CHARSET = "charset";
    private static final String ARG_SOURCE = "source";
    private static final String ARG_DESTINATION = "destination";
    
    private static final String DESCRIPTION = "Areca's external decompression tool.";
    
	private String source = "";
	private String destination = "";
    private String charset = "";
	
    public static void main(String[] args) {
        CmdLineDeZip launcher = new CmdLineDeZip();
        launcher.launch(args);
    }
    
	public CmdLineDeZip() {
	}
		
	public boolean init(String args[]) {
		CommandLineParser parser=new CommandLineParser();
        parser.setDescription(DESCRIPTION);
        
        parser.addParameter(new StringCmdLineOption(true,ARG_SOURCE, "Zip archive"));
        parser.addParameter(new StringCmdLineOption(true,ARG_DESTINATION,"Destination Directory "));
		parser.addParameter(new StringCmdLineOption(false,ARG_CHARSET, "Character Set [UTF-8, cp1252 ... default = UTF-8]"));

		try {
			parser.parse(args, null);
            source =  (String)parser.getParameter(ARG_SOURCE).getValue();
			destination =  (String)parser.getParameter(ARG_DESTINATION).getValue();
            charset =  (String)parser.getParameter(ARG_CHARSET).getValue();
		} catch (CmdLineParserException e) {
			System.out.println("Syntax error : " + e.getMessage());
			System.out.println(parser.usage());
			return false;
		}	
		return true;
	}

	protected void process() throws IOException{
	    ZipArchiveAdapter adapter = null;
        File archive = new File(source);
        File dir = new File(destination); 
        
        if (! FileSystemManager.exists(dir)) {
            FileTool.getInstance().createDir(dir);
        }
        
        if (! FileSystemManager.exists(archive)) {
            System.out.println("Error ! Zip archive does not exist : " + FileSystemManager.getAbsolutePath(archive));
        } else {
            boolean multivolume = false;
            String name = FileSystemManager.getName(archive);
            int index = name.lastIndexOf('.');
            if (index != -1) {
                String baseName = name.substring(0, index);
                String ext = name.substring(index + 1);
                ZipVolumeStrategy strat = new ZipVolumeStrategy(
                        new File(FileSystemManager.getParentFile(archive), baseName),
                        ext
                );
                multivolume = FileSystemManager.exists(strat.getFirstVolume());
                
                if (multivolume) {
                    System.out.println(" * Multi-volumes archive * ");
                    adapter = new ZipArchiveAdapter(strat);
                } 
            }
            
            if (! multivolume) {
                System.out.println("Opening " + FileSystemManager.getAbsolutePath(archive) + " ...");
                adapter = new ZipArchiveAdapter(FileSystemManager.getFileInputStream(archive));
            }
            
            if (charset != null && charset.trim().length() != 0) {
                adapter.setCharset(Charset.forName(charset));
            }
            ArchiveReader reader = new ArchiveReader(adapter);
            reader.injectIntoDirectory(dir);
            System.out.println("Decompression completed.");
        }
	}

	protected void launchImpl(String[] args) {
		try {
            CmdLineDeZip main = new CmdLineDeZip();

            if (main.init(args)) {
            	main.process();
            }
        } catch (Throwable e) {
            System.out.println("\nWARNING : An error occured during decompression. You should check that all your arguments are valid.");
            showLine();
            e.printStackTrace();
            showLine();
        }
	}
}
