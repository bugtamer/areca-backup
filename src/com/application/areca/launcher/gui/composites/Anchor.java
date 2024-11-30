package com.application.areca.launcher.gui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

import com.application.areca.ArecaURLs;
import com.myJava.util.log.Logger;


/**
 * @see ArecaURLs.java
 * @see DonationLink.java
 */
public class Anchor {

	/**
	 * @param composite
	 * @param uri (href)
	 * @param label of HTML Anchor
	 * @return new Link(composite, SWT.NONE);
	 */
	public static Link build(Composite composite, String uri, String label) {
		final String anchor = String.format("<a href=\"%s\">%s</a>", uri, label);
		
		final Link link = new Link(composite, SWT.NONE);
		link.setText(anchor);
		link.addSelectionListener(new SelectionAdapter()  {

			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					Program.launch(uri);
				} catch (Exception e) {
					Logger.defaultLogger().error(e);
				}
			}

		});
		return link;
	}

}
