package com.application.areca.launcher.gui.composites;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.application.areca.ApplicationException;
import com.application.areca.ArchiveMedium;
import com.application.areca.ResourceManager;
import com.application.areca.Utils;
import com.application.areca.impl.AggregatedViewContext;
import com.application.areca.launcher.gui.Application;
import com.application.areca.launcher.gui.RecoveryFilter;
import com.application.areca.launcher.gui.common.AbstractWindow;
import com.application.areca.launcher.gui.common.ArecaImages;
import com.application.areca.launcher.gui.common.Colors;
import com.application.areca.metadata.MetadataConstants;
import com.application.areca.metadata.trace.TraceEntry;
import com.myJava.util.log.Logger;

/**
 * <BR>
 * 
 * @author Olivier PETRUCCI <BR>
 * <BR>Areca Build ID : 5570316944386086207
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
public class ArchiveExplorer extends Composite implements MouseListener,
		Listener {
	private static int ITEM_STYLE = SWT.NONE;
	private final ResourceManager RM = ResourceManager.instance();

	private Tree tree;
	private boolean displayNonStoredItemsSize = false;
	private boolean logicalView = false;
	private Font italic;
	private ArchiveMedium medium;
	private AggregatedViewContext context = new AggregatedViewContext();
	private GregorianCalendar fromDate;
	private boolean aggregated = false;

	public ArchiveExplorer(Composite parent, boolean aggregated) {
		super(parent, SWT.NONE);
		this.aggregated = aggregated;
		
		setLayout(new FillLayout());
		TreeViewer viewer = new TreeViewer(this, SWT.BORDER | SWT.MULTI);
		tree = viewer.getTree();
		tree.setLinesVisible(AbstractWindow.getTableLinesVisible());
		tree.setHeaderVisible(true);
		tree.addMouseListener(this);
		tree.addListener(SWT.Selection, this);

		TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
		column1.setText(RM.getLabel("mainpanel.name.label"));
		column1.setWidth(AbstractWindow.computeWidth(400));
		TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
		column2.setText(RM.getLabel("mainpanel.size.label"));
		column2.setWidth(AbstractWindow.computeWidth(120));

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				TreeItem item = tree.getSelection()[0];
				item.setExpanded(!item.getExpanded());
			}
		});

		TreeListener listener = new TreeListener() {
			public void treeCollapsed(TreeEvent arg0) {
				TreeItem item = (TreeItem) arg0.item;
				item.removeAll();
				new TreeItem(item, ITEM_STYLE);
			}

			public void treeExpanded(TreeEvent event) {
				try {
					TreeItem item = (TreeItem) event.item;
					refreshNode(item, (TraceEntry) item.getData(), null);
				} catch (ApplicationException e) {
					Logger.defaultLogger().error(e);
				}
			}
		};
		tree.addTreeListener(listener);
	}

	public GregorianCalendar getFromDate() {
		return fromDate;
	}

	public void setFromDate(GregorianCalendar fromDate) {
		this.fromDate = fromDate;
	}

	public ArchiveMedium getMedium() {
		return medium;
	}

	public void setMedium(ArchiveMedium medium) {
		this.medium = medium;
	}

	public void setDisplayNonStoredItemsSize(boolean displayNonStoredItemsSize) {
		this.displayNonStoredItemsSize = displayNonStoredItemsSize;
	}

	public void setLogicalView(boolean logicalView) {
		this.logicalView = logicalView;
	}

	public void refresh(boolean aggregated) throws ApplicationException {
		this.aggregated = aggregated;
		reset();
		if (medium != null) {
			TraceEntry entry = new TraceEntry();
			entry.setKey("");
			entry.setType(MetadataConstants.T_DIR);

			refreshNode(null, entry, tree);
		}
	}

	private void refreshNode(TreeItem item, TraceEntry entry, Tree tree)
			throws ApplicationException {
		// Get data to display
		List entries;
		if (logicalView) {
			entries = this.medium.getLogicalView(context, entry.getKey(), aggregated);
		} else {
			entries = this.medium.getEntries(context, entry.getKey(), fromDate);
		}

		// Remove existing items
		if (item != null) {
			item.removeAll();
		}
		if (tree != null) {
			tree.removeAll();
		}

		// Add new items
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			TreeItem chld;
			if (tree == null) {
				chld = new TreeItem(item, ITEM_STYLE);
			} else {
				chld = new TreeItem(tree, ITEM_STYLE);
			}
			chld.setData(iter.next());
			configure(chld);
		}
	}

	private void configure(final TreeItem item) {
		TraceEntry data = (TraceEntry) item.getData();
		long length = 0;
		boolean stored = true;
		if (data.getType() != MetadataConstants.T_SYMLINK) {
			length = Math.max(0, Long.parseLong(data.getData().substring(1)));
			stored = data.getData().charAt(0) == '1';
		}

		if (stored) {
			item.setForeground(Colors.C_BLACK);
		} else {
			item.setForeground(Colors.C_LIGHT_GRAY);
		}

		if (data.getType() == MetadataConstants.T_SYMLINK) {
			// SymLinks
			item.setFont(deriveItalicFont(item));
		}

		if (data.getType() == MetadataConstants.T_DIR
				|| (data.getType() == MetadataConstants.T_SYMLINK && data
						.getData().equals("0"))) {
			item.setImage(ArecaImages.ICO_FS_FOLDER);
		} else {
			item.setImage(ArecaImages.ICO_FS_FILE);
		}

		int idx = data.getKey().lastIndexOf('/');
		String label = data.getKey();
		if (idx != -1) {
			label = data.getKey().substring(idx + 1);
		}
		item.setText(0, label);
		if (((!stored) && (!displayNonStoredItemsSize))) {
			item.setText(1, " ");
		} else {
			item.setText(1, Utils.formatFileSize(length));
		}

		if (data.getType() == MetadataConstants.T_DIR) {
			new TreeItem(item, ITEM_STYLE);
		}
	}

	private Font deriveItalicFont(TreeItem item) {
		if (this.italic == null) {
			FontData dt = item.getFont().getFontData()[0];
			FontData dtItalic = new FontData(dt.getName(), dt.height,
					SWT.ITALIC);
			return new Font(item.getDisplay(), new FontData[] { dtItalic });
		}
		return italic;
	}

	public void reset() {
		this.tree.removeAll();
		this.context.setData(null);
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

	public void setSelectedEntry(TraceEntry entry) {
		if (entry != null) {
			StringTokenizer stt = new StringTokenizer(entry.getKey(), "/");
			TreeItem parent = null;
			String element = null;
			while (stt.hasMoreTokens()) {
				element = element == null ? stt.nextToken() : element + "/"
						+ stt.nextToken();
				parent = getElement(parent, element);
				try {
					refreshNode(parent, (TraceEntry) parent.getData(), null);
				} catch (ApplicationException e) {
					Logger.defaultLogger().error(e);
				}
			}
			this.tree.setSelection(parent);

			Application.getInstance().setCurrentEntry(entry);
			Application.getInstance().setCurrentFilter(buildFilter(entry));
		}
	}

	private TreeItem getElement(TreeItem parent, String name) {
		TreeItem[] items = parent == null ? tree.getItems() : parent.getItems();
		for (int i = 0; i < items.length; i++) {
			TreeItem child = items[i];
			TraceEntry data = (TraceEntry) child.getData();
			if (name.equals(data.getKey())) {
				return child;
			}
		}
		return null;
	}

	private RecoveryFilter buildFilter(TreeItem[] nodes) {
        RecoveryFilter ret = new RecoveryFilter();
        
        String[] filter = new String[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            TreeItem current = nodes[i];
            TraceEntry data = (TraceEntry)current.getData();
            filter[i] = data.getKey() + (data.getType() == MetadataConstants.T_DIR ? "/" : "");
            
            if (data.getType() != MetadataConstants.T_SYMLINK && data.getData() != null && data.getData().length() > 0 && data.getData().charAt(0) == '0') {
                ret.setContainsDeletedDirectory(true);
            }
        }
        
        ret.setFilter(filter);
        return ret;
    }

	private RecoveryFilter buildFilter(TraceEntry entry) {
		RecoveryFilter filter = new RecoveryFilter();
		filter.setContainsDeletedDirectory(false);
		filter.setFilter(new String[] { entry.getKey() });
		return filter;
	}

	public void mouseDoubleClick(MouseEvent e) {
	}

	public void mouseUp(MouseEvent e) {
	}

	public void mouseDown(MouseEvent e) {
		showMenu(e, logicalView ? Application.getInstance()
				.getArchiveContextMenuLogical() : Application.getInstance()
				.getArchiveContextMenu());
	}

	private void showMenu(MouseEvent e, Menu m) {
		if (e.button == 3) {
			m.setVisible(true);
		}
	}

	public void handleEvent(Event event) {
		TreeItem[] selection = tree.getSelection();

		if (selection.length == 1) {
			TraceEntry data = (TraceEntry) (selection[0].getData());
			Application.getInstance().setCurrentEntry(data);
		} else {
			Application.getInstance().setCurrentEntry(null);
		}

		Application.getInstance().setCurrentFilter(buildFilter(selection));
	}

	public Tree getTree() {
		return tree;
	}
}
