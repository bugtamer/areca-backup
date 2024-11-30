package com.application.areca;

/**
 * 
 * <BR>
 * @author Olivier PETRUCCI
 * @author bugtamer
 * <BR>
 *
 */

 /*
 Copyright 2005-2015, Olivier PETRUCCI.
 Copyright 2024, bugtamer.

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
public interface ArecaURLs {
    /** Only used to shorten Areca URLs. */
    static String BASE_URL = "https://areca-backup.sourceforge.io";

    /** Only used to shorten Areca URLs. */
    static String QUERY_PARAMS = "?fromApplication=1&currentVersion=";

	public String HELP_ROOT       = BASE_URL + "/documentation.php" + QUERY_PARAMS;
	public String TUTORIAL_ROOT   = BASE_URL + "/tutorial.php"      + QUERY_PARAMS;
	public String DONATION_URL = "https://www.paypal.com/cgi-bin/webscr?item_name=Donation+to+Areca+Backup&cmd=_donations&business=olivier.petrucci%40gmail.com&lc=US";
	public String ARECA_URL       = BASE_URL;
	public String REGEX_URL       = BASE_URL + "/regex.php";
    public String VERSION_URL     = BASE_URL + "/version_xml.php";
    public String BACKUP_COPY_URL = BASE_URL + "/config_backup.php";
    public String PLUGINS_URL     = BASE_URL + "/plugin_list.php";
}
