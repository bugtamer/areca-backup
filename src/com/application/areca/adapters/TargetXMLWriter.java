package com.application.areca.adapters;

import java.io.File;
import java.util.Iterator;

import com.application.areca.filter.ArchiveFilter;
import com.application.areca.filter.DirectoryArchiveFilter;
import com.application.areca.filter.FileDateArchiveFilter;
import com.application.areca.filter.FileExtensionArchiveFilter;
import com.application.areca.filter.FileSizeArchiveFilter;
import com.application.areca.filter.FilterGroup;
import com.application.areca.filter.LinkFilter;
import com.application.areca.filter.LockedFileFilter;
import com.application.areca.filter.RegexArchiveFilter;
import com.application.areca.impl.AbstractIncrementalFileSystemMedium;
import com.application.areca.impl.FileSystemRecoveryTarget;
import com.application.areca.impl.IncrementalDirectoryMedium;
import com.application.areca.impl.IncrementalZipMedium;
import com.application.areca.impl.policy.EncryptionPolicy;
import com.application.areca.impl.policy.FileSystemPolicy;
import com.application.areca.plugins.StoragePlugin;
import com.application.areca.plugins.StoragePluginRegistry;
import com.application.areca.postprocess.FileDumpPostProcessor;
import com.application.areca.postprocess.MailSendPostProcessor;
import com.application.areca.postprocess.MergePostProcessor;
import com.application.areca.postprocess.ShellScriptPostProcessor;
import com.myJava.file.FileSystemManager;

/**
 * Target serializer
 * 
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
public class TargetXMLWriter extends AbstractXMLWriter {

    protected boolean removeEncryptionData = false;
    
    public TargetXMLWriter() {
        this(new StringBuffer());
    }
    
    public TargetXMLWriter(StringBuffer sb) {
        super(sb);
    }

    public void setRemoveEncryptionData(boolean removeEncryptionData) {
        this.removeEncryptionData = removeEncryptionData;
    }
    
    public void serializeTarget(FileSystemRecoveryTarget tg) {
        sb.append("\n\n<");
        sb.append(XML_TARGET);
        
        sb.append(" ");
        sb.append(XML_TARGET_ID);
        sb.append("=");
        sb.append(encode("" + tg.getId()));
        
        sb.append(" ");
        sb.append(XML_TARGET_UID);
        sb.append("=");
        sb.append(encode("" + tg.getUid()));
        
        sb.append(" ");
        sb.append(XML_TARGET_FOLLOW_SYMLINKS);
        sb.append("=");
        sb.append(encode("" + ! tg.isTrackSymlinks()));
        
        sb.append(" ");
        sb.append(XML_TARGET_NAME);
        sb.append("=");
        sb.append(encode("" + tg.getTargetName()));     
        
        sb.append(" ");
        sb.append(XML_TARGET_DESCRIPTION);
        sb.append("=");
        sb.append(encode(tg.getComments()));     
        
        sb.append(">");   
        
        // Sources
        Iterator sources = tg.getSources().iterator();
        while (sources.hasNext()) {
            File source = (File)sources.next();
            serializeSource(source);
        }
        
        // Support
        if (IncrementalDirectoryMedium.class.isAssignableFrom(tg.getMedium().getClass())) {
            serializeMedium((IncrementalDirectoryMedium)tg.getMedium());            
        } else if (IncrementalZipMedium.class.isAssignableFrom(tg.getMedium().getClass())) {
            serializeMedium((IncrementalZipMedium)tg.getMedium());
        }
       
        // Filtres
        serializeFilter(tg.getFilterGroup());
        
        // Postprocessors
        Iterator iter = tg.getPostProcessors().iterator();
        while (iter.hasNext()) {
            Object pp = iter.next();
            if (FileDumpPostProcessor.class.isAssignableFrom(pp.getClass())) {
                serializeProcessor((FileDumpPostProcessor)pp);
            } else if (MailSendPostProcessor.class.isAssignableFrom(pp.getClass())) {
                serializeProcessor((MailSendPostProcessor)pp);            
            } else if (ShellScriptPostProcessor.class.isAssignableFrom(pp.getClass())) {
                serializeProcessor((ShellScriptPostProcessor)pp); 
            } else if (MergePostProcessor.class.isAssignableFrom(pp.getClass())) {
                serializeProcessor((MergePostProcessor)pp); 
            }
        }
        
        sb.append("\n</");
        sb.append(XML_TARGET);
        sb.append(">");            
    }
    
    protected void serializeSource(File source) {
        sb.append("\n<");
        sb.append(XML_SOURCE);
        sb.append(" ");
        sb.append(XML_SOURCE_PATH);
        sb.append("=");
        sb.append(encode(FileSystemManager.getAbsolutePath(source)));
        sb.append("/>");     
    }

    protected void serializeProcessor(FileDumpPostProcessor pp) {
        sb.append("\n<");
        sb.append(XML_POSTPROCESSOR_DUMP);
        sb.append(" ");
        sb.append(XML_PP_DUMP_DIRECTORY);
        sb.append("=");
        sb.append(encode(FileSystemManager.getAbsolutePath(pp.getDestinationFolder())));
        sb.append(" ");     
        sb.append(XML_PP_ONLY_IF_ERROR);
        sb.append("=");
        sb.append(encode("" + pp.isOnlyIfError()));     
        sb.append(" ");     
        sb.append(XML_PP_LIST_FILTERED);
        sb.append("=");
        sb.append(encode("" + pp.isListFiltered()));  
        sb.append(" ");     
        sb.append(XML_PP_DUMP_NAME);
        sb.append("=");
        sb.append(encode("" + pp.getReportName()));  
        sb.append("/>");        
    }
    
    protected void serializeProcessor(MergePostProcessor pp) {
        sb.append("\n<");
        sb.append(XML_POSTPROCESSOR_MERGE);
        sb.append(" ");
        sb.append(XML_PP_MERGE_DELAY);
        sb.append("=");
        sb.append(encode("" + pp.getDelay()));
        sb.append(" ");
        sb.append(XML_PP_MERGE_KEEP_DELETED);
        sb.append("=");
        sb.append(encode("" + pp.isKeepDeletedEntries()));
        sb.append("/>");        
    }
    
    protected void serializeProcessor(MailSendPostProcessor pp) {
        sb.append("\n<");
        sb.append(XML_POSTPROCESSOR_EMAIL);
        sb.append(" ");
        sb.append(XML_PP_EMAIL_RECIPIENTS);
        sb.append("=");
        sb.append(encode(pp.getRecipients()));
        sb.append(" ");
        sb.append(XML_PP_EMAIL_SMTP);
        sb.append("=");
        sb.append(encode(pp.getSmtpServer()));
        sb.append(" ");
        sb.append(XML_PP_EMAIL_USER);
        sb.append("=");
        sb.append(encode(pp.getUser()));      
        sb.append(" ");
        sb.append(XML_PP_EMAIL_PASSWORD);
        sb.append("=");
        sb.append(encode(pp.getPassword()));  
        sb.append(" ");     
        sb.append(XML_PP_ONLY_IF_ERROR);
        sb.append("=");
        sb.append(encode("" + pp.isOnlyIfError()));     
        sb.append(" ");     
        sb.append(XML_PP_EMAIL_SMTPS);
        sb.append("=");
        sb.append(encode("" + pp.isSmtps()));    
        sb.append(" ");     
        sb.append(XML_PP_LIST_FILTERED);
        sb.append("=");
        sb.append(encode("" + pp.isListFiltered()));  
        sb.append(" ");
        sb.append(XML_PP_EMAIL_TITLE);
        sb.append("=");
        sb.append(encode(pp.getTitle()));   
        sb.append(" ");
        sb.append(XML_PP_EMAIL_FROM);
        sb.append("=");
        sb.append(encode(pp.getFrom()));   
        sb.append(" ");
        sb.append(XML_PP_EMAIL_INTRO);
        sb.append("=");
        sb.append(encode(pp.getIntro())); 
        sb.append("/>");        
    }
    
    protected void serializeProcessor(ShellScriptPostProcessor pp) {
        sb.append("\n<");
        sb.append(XML_POSTPROCESSOR_SHELL);
        sb.append(" ");
        sb.append(XML_PP_SHELL_SCRIPT);
        sb.append("=");
        sb.append(encode(pp.getCommand()));
        sb.append(" ");
        sb.append(XML_PP_SHELL_PARAMS);
        sb.append("=");
        sb.append(encode(pp.getCommandParameters()));
        sb.append("/>");        
    }
    
    protected void serializeFilter(FilterGroup filters) {
        sb.append("\n<");
        sb.append(XML_FILTER_GROUP);
        sb.append(" ");
        sb.append(XML_FILTER_EXCLUDE);
        sb.append("=");
        sb.append(encode("" + filters.isExclude()));
        sb.append(" ");
        sb.append(XML_FILTER_GROUP_OPERATOR);
        sb.append("=");
        sb.append(encode(filters.isAnd() ? XML_FILTER_GROUP_OPERATOR_AND : XML_FILTER_GROUP_OPERATOR_OR));
        sb.append(" ");
        sb.append(">");             
        Iterator iter = filters.getFilterIterator();
        while (iter.hasNext()) {
            Object filter = iter.next();
            if (DirectoryArchiveFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((DirectoryArchiveFilter)filter);
            } else if (FileExtensionArchiveFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((FileExtensionArchiveFilter)filter);            
            } else if (RegexArchiveFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((RegexArchiveFilter)filter); 
            } else if (FileSizeArchiveFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((FileSizeArchiveFilter)filter); 
            } else if (LinkFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((LinkFilter)filter); 
            } else if (LockedFileFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((LockedFileFilter)filter); 
            } else if (FileDateArchiveFilter.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((FileDateArchiveFilter)filter); 
            } else if (FilterGroup.class.isAssignableFrom(filter.getClass())) {
                serializeFilter((FilterGroup)filter); 
            }
        }
        sb.append("\n</");
        sb.append(XML_FILTER_GROUP);
        sb.append(">");     
    }
    
    protected void serializeFilter(RegexArchiveFilter filter) {
        sb.append("\n<");
        sb.append(XML_FILTER_REGEX);
        sb.append(" ");
        sb.append(XML_FILTER_EXCLUDE);
        sb.append("=");
        sb.append(encode("" + filter.isExclude()));
        sb.append(" ");
        sb.append(XML_FILTER_RG_PATTERN);
        sb.append("=");
        sb.append(encode(filter.getStringParameters()));
        sb.append("/>");        
    }
    
    protected void serializeFilter(DirectoryArchiveFilter filter) {
        sb.append("\n<");
        sb.append(XML_FILTER_DIRECTORY);
        sb.append(" ");
        sb.append(XML_FILTER_EXCLUDE);
        sb.append("=");
        sb.append(encode("" + filter.isExclude()));
        sb.append(" ");
        sb.append(XML_FILTER_DIR_PATH);
        sb.append("=");
        sb.append(encode(filter.getStringParameters()));
        sb.append("/>");        
    }
    
    protected void serializeFilter(FileExtensionArchiveFilter filter) {
        sb.append("\n<");
        sb.append(XML_FILTER_FILEEXTENSION);
        sb.append(" ");
        sb.append(XML_FILTER_EXCLUDE);
        sb.append("=");
        sb.append(encode("" + filter.isExclude()));
        sb.append(">");
        
        Iterator iter = filter.getExtensionIterator();
        while (iter.hasNext()) {
            sb.append("\n<");
            sb.append(XML_FILTER_EXTENSION);
            sb.append(">");
            sb.append(iter.next().toString());
            sb.append("</");
            sb.append(XML_FILTER_EXTENSION);
            sb.append(">");            
        }
        
        sb.append("\n</");
        sb.append(XML_FILTER_FILEEXTENSION);
        sb.append(">");         
    }    
    
    protected void serializeFilter(FileSizeArchiveFilter filter) {
        serializeFilterGenericData(filter, XML_FILTER_FILESIZE, true);
    }
    
    protected void serializeFilter(FileDateArchiveFilter filter) {
        serializeFilterGenericData(filter, XML_FILTER_FILEDATE, true);
    }
    
    protected void serializeFilter(LinkFilter filter) {
        serializeFilterGenericData(filter, XML_FILTER_LINK, false);
    }

    protected void serializeFilter(LockedFileFilter filter) {
        serializeFilterGenericData(filter, XML_FILTER_LOCKED, false);
    }
    
    protected void serializeFilterGenericData(ArchiveFilter filter, String filterName, boolean addParam) {
        sb.append("\n<");
        sb.append(filterName);
        sb.append(" ");
        sb.append(XML_FILTER_EXCLUDE);
        sb.append("=");
        sb.append(encode("" + filter.isExclude()));
        if (addParam) {
            sb.append(" ");
            sb.append(XML_FILTER_PARAM);
            sb.append("=");
            sb.append(encode(filter.getStringParameters()));
        }
        sb.append("/>");        
    }
    
    protected void serializeMedium(IncrementalZipMedium medium) {
        sb.append("\n<");
        sb.append(XML_MEDIUM);
        sb.append(" ");
        sb.append(XML_MEDIUM_TYPE);
        sb.append("=");
        if (medium.isUseZip64()) {
            sb.append(encode(XML_MEDIUM_TYPE_ZIP64));
        } else {
            sb.append(encode(XML_MEDIUM_TYPE_ZIP));
        }
        sb.append(" ");
        
        if (medium.isMultiVolumes()) {
            sb.append(XML_MEDIUM_VOLUME_SIZE);
            sb.append("=");
            sb.append(encode("" + medium.getVolumeSize()));
            sb.append(" ");            
        }
        
        if (medium.getComment() != null) {
            sb.append(XML_MEDIUM_ZIP_COMMENT);
            sb.append("=");
            sb.append(encode(medium.getComment()));
            sb.append(" ");            
        }
        
        if (medium.getCharset() != null) {
            sb.append(XML_MEDIUM_ZIP_CHARSET);
            sb.append("=");
            sb.append(encode(medium.getCharset().name()));
            sb.append(" ");            
        }
        
        this.serializeMediumGeneralData(medium);     
        sb.append("/>");   
    } 
    
    protected void serializeMedium(IncrementalDirectoryMedium medium) {
        sb.append("\n<");
        sb.append(XML_MEDIUM);
        sb.append(" ");
        sb.append(XML_MEDIUM_TYPE);
        sb.append("=");
        sb.append(encode(XML_MEDIUM_TYPE_DIR));
        sb.append(" ");
        this.serializeMediumGeneralData(medium);
        sb.append("/>");   
    }   
    
    protected void serializeMediumGeneralData(AbstractIncrementalFileSystemMedium medium) {
        serializeFileSystemPolicy(medium.getFileSystemPolicy());
        serializeEncryptionPolicy(medium.getEncryptionPolicy());
        
        sb.append(" ");
        sb.append(XML_MEDIUM_TRACK_DIRS);
        sb.append("=");
        sb.append(encode("" + medium.isTrackDirectories())); 
        
        sb.append(" ");
        sb.append(XML_MEDIUM_TRACK_PERMS);
        sb.append("=");
        sb.append(encode("" + medium.isTrackPermissions())); 
        
        sb.append(" ");
        sb.append(XML_MEDIUM_OVERWRITE);
        sb.append("=");
        sb.append(encode("" + medium.isOverwrite())); 
    }
    
    protected void serializeEncryptionPolicy(EncryptionPolicy policy) {
        sb.append(" ");
        sb.append(XML_MEDIUM_ENCRYPTED);
        sb.append("=");
        sb.append(encode("" + policy.isEncrypted()));     
        
        if (policy.isEncrypted() && (! removeEncryptionData)) {
	        sb.append(" ");
	        sb.append(XML_MEDIUM_ENCRYPTIONKEY);
	        sb.append("=");
	        sb.append(encode(policy.getEncryptionKey())); 
	        
	        sb.append(" ");
	        sb.append(XML_MEDIUM_ENCRYPTIONALGO);
	        sb.append("=");
	        sb.append(encode(policy.getEncryptionAlgorithm()));         
        }
    }
    
    protected void serializeFileSystemPolicy(FileSystemPolicy policy) {
        String id = policy.getId();
        
        sb.append(" ");
        sb.append(XML_MEDIUM_POLICY);
        sb.append("=");
        sb.append(encode(id)); 
        
        StoragePlugin plugin = StoragePluginRegistry.getInstance().getById(id);
        plugin.getFileSystemPolicyXMLHandler().write(policy, sb);
    }
}