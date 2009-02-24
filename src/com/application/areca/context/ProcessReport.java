package com.application.areca.context;

import java.util.Date;
import java.util.GregorianCalendar;

import com.application.areca.AbstractRecoveryTarget;

/**
 * Contains reporting data.
 * <BR>ProcessReports are created during backup/recovery/merge processes.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : 8156499128785761244
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
public class ProcessReport {

    /**
     * Ignored files (because not modified)
     */
    protected int ignoredFiles = 0;
    
    /**
     * Number of really stored files
     */
    protected int savedFiles = 0;   
    
    /**
     * Number of files which have been filtered
     */
    protected int filteredEntries = 0;
    
    /**
     * Number of files that have not been found in the source directory
     * --> That means that they have been deleted from the disk
     */
    protected int deletedFiles = 0;
    
    /**
     * Number of processed directories
     */
    protected int unfilteredDirectories;

    
    /**
     * Start date used to compute the data flow
     */
    protected long dataFlowStart = 0;
    
    /**
     * Stop date used to compute the data flow
     */
    protected long dataFlowStop = 0;
    
    /**
     * Number of processed files
     */
    protected int unfilteredFiles;    
    
    /**
     * Archives which have been restored during the recovery/merge process
     */
    protected RecoveryResult recoveryResult;
    
    /**
     * Process start date (ms)
     */
    protected long startMillis = System.currentTimeMillis();

    protected AbstractRecoveryTarget target;

    /**
     * Tells wether the process is being validated
     */
    protected boolean commited = false;
    
    public ProcessReport(AbstractRecoveryTarget target) {
        this.target = target;
    } 
    
    public AbstractRecoveryTarget getTarget() {
        return target;
    }
    /**
     * Resets all counters ... except the duration counter
     */
    public void reset() {
        this.unfilteredDirectories = 0;
        this.unfilteredFiles = 0;
        this.filteredEntries = 0;
        this.ignoredFiles = 0;
        this.recoveryResult = null;
        this.savedFiles = 0;
    }

    public RecoveryResult getRecoveryResult() {
        return recoveryResult;
    }

    public void setRecoveryResult(RecoveryResult recoveryResult) {
        this.recoveryResult = recoveryResult;
    }

    public GregorianCalendar getStartDate() {
        Date d = new Date(this.startMillis);
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);
        return c;
    }
    
    public int getIgnoredFiles() {
        return ignoredFiles;
    }
    
    public void startDataFlowTimer() {
        dataFlowStart = System.currentTimeMillis();
    }
    
    public void stopDataFlowTimer() {
        dataFlowStop = System.currentTimeMillis() + 1;
    }
    
    public long getDataFlowTimeInSecond() {
        return (long)((dataFlowStop - dataFlowStart)/1000.0);
    }
    
    public int getProcessedEntries() {
        return this.filteredEntries + this.unfilteredDirectories + this.unfilteredFiles;
    }
    
    public int getSavedFiles() {
        return savedFiles;
    }
    
    public void addSavedFile() {
        this.savedFiles++;
    }
    
    public void addIgnoredFile() {
        this.ignoredFiles++;
    }  
    
    public void addDeletedFile() {
        this.deletedFiles++;
    }   

    public void setFilteredEntries(int filteredEntries) {
		this.filteredEntries = filteredEntries;
	}

	public long getStartMillis() {
        return startMillis;
    }
    
    public int getFilteredEntries() {
        return filteredEntries;
    }

    public int getUnfilteredDirectories() {
        return unfilteredDirectories;
    }
    
    public int getUnfilteredFiles() {
        return unfilteredFiles;
    }

    public void setUnfilteredDirectories(int unfilteredDirectories) {
		this.unfilteredDirectories = unfilteredDirectories;
	}

	public void setUnfilteredFiles(int unfilteredFiles) {
		this.unfilteredFiles = unfilteredFiles;
	}

	public boolean isCommited() {
        return commited;
    }

    public long getDataFlowStart() {
		return this.dataFlowStart;
	}

	public long getDataFlowStop() {
		return this.dataFlowStop;
	}
	
	public void unsetCommited() {
        this.commited = false;
    }

	public void setCommited() {
        this.commited = true;
    }
    
    public int getDeletedFiles() {
        return deletedFiles;
    }

    public void setDeletedFiles(int deletedFiles) {
        this.deletedFiles = deletedFiles;
    }
}
