package com.application.areca;

import com.myJava.util.errors.ActionReport;

/**
 * 
 * 
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
public class ApplicationException extends Exception {

    public ApplicationException() {
        super();
    }
    
    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(ActionReport report) {
        super(buildErrorMessage(report));
    }
    
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ApplicationException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Construit un message d'erreur � partir du report
     */
    private static String buildErrorMessage(ActionReport report) {
        if (report.isDataValid()) {
            return Errors.ERR_ALL_OK;
        } else {
            String err = "";
            
            for (int i=0; i<report.getErrorCount(); i++) {
                err += "\n" + report.getErrorAt(i).getMessage() + " ";
            }
            
            return err.substring(1);            
        }
    }
}