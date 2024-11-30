package com.application.areca.launcher.gui.composites;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

import com.application.areca.ArecaURLs;
import com.application.areca.launcher.gui.resources.ResourceManager;


/**
 * 
 * <BR>
 * @author Olivier PETRUCCI
 * @author bugtamer
 * <BR>
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
public class DonationLink implements ArecaURLs {

	public static Link build(Composite composite) {
		final String label = ResourceManager.instance().getLabel("about.support");
		return Anchor.build(composite, DONATION_URL, label);
	}

}
