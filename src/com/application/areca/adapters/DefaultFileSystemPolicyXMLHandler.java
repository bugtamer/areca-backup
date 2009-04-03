package com.application.areca.adapters;

import java.io.File;

import org.w3c.dom.Node;

import com.application.areca.AbstractRecoveryTarget;
import com.application.areca.impl.policy.DefaultFileSystemPolicy;
import com.application.areca.impl.policy.FileSystemPolicy;
import com.myJava.file.FileSystemManager;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 7299034069467778562
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
public class DefaultFileSystemPolicyXMLHandler
extends AbstractFileSystemPolicyXMLHandler {
	public FileSystemPolicy read(
			Node mediumNode, 
			AbstractRecoveryTarget target, 
			TargetXMLReader reader
	) throws AdapterException {
        DefaultFileSystemPolicy policy = new DefaultFileSystemPolicy();
        policy.setId(POLICY_HD);
		if (version == 1) {
	        Node pathNode = mediumNode.getAttributes().getNamedItem(XML_MEDIUM_ARCHIVEPATH_DEPRECATED);
	        File f = new File(pathNode.getNodeValue());
	        policy.setArchivePath(FileSystemManager.getParent(f));
	        policy.setArchiveName(buildDeprecatedArchiveName(FileSystemManager.getName(f), TargetXMLReader.isOverwrite(mediumNode)));
		} else {
	        Node pathNode = mediumNode.getAttributes().getNamedItem(XML_MEDIUM_PATH);
	        Node nameNode = mediumNode.getAttributes().getNamedItem(XML_MEDIUM_ARCHIVENAME);
	        policy.setArchivePath(pathNode.getNodeValue());
	        policy.setArchiveName(nameNode.getNodeValue());
		}
        return policy;
    }

    public void write(FileSystemPolicy policy, TargetXMLWriter writer, boolean removeSensitiveData, StringBuffer sb) {
        sb.append(" ");
        sb.append(XML_MEDIUM_PATH);
        sb.append("=");
        sb.append(AbstractXMLWriter.encode(policy.getArchivePath()));   

        sb.append(" ");
        sb.append(XML_MEDIUM_ARCHIVENAME);
        sb.append("=");
        sb.append(AbstractXMLWriter.encode(policy.getArchiveName()));
    }
}
