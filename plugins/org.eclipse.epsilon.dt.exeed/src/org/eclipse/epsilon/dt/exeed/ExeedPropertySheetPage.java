/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

public class ExeedPropertySheetPage extends ExtendedPropertySheetPage {

	protected ExeedEditor editor;
	protected boolean alphabeticallySorted = false;
	protected ExeedPlugin plugin;
	
	public ExeedPropertySheetPage(AdapterFactoryEditingDomain editingDomain, ExeedEditor editor, ExeedPlugin plugin) {
		super(editingDomain);
		this.alphabeticallySorted = !plugin.getPreferenceStore().getBoolean(ExeedPreferencePage.SHOW_ALL_RESOURCES);
		this.editor = editor;
		updateSorting();
	}
	
    @Override
    public void setSelectionToViewer(List<?> selection)
    {
      editor.setSelectionToViewer(selection);
      editor.setFocus();
    }

    @Override
    public void setActionBars(IActionBars actionBars)
    {
      super.setActionBars(actionBars);
      editor.getActionBarContributor().shareGlobalActions(this, actionBars);
    }

    
	public void updateSorting() {
		super.setSorter(new PropertySheetSorter() {

			@Override
			public void sort(IPropertySheetEntry[] entries) {
				
				if (alphabeticallySorted) {
					Arrays.sort(entries, new Comparator<IPropertySheetEntry>() {
						public int compare(IPropertySheetEntry a, IPropertySheetEntry b) {
							return a.getDisplayName().compareTo(b.getDisplayName());
						}
					});
				}
			}
			
		});

		// Only refresh if the view is out, otherwise we may have an NPE in 2018-09 (#539644)
		if (getControl() != null) {
			super.refresh();
		}
	}

	public boolean isAlphabeticallySorted() {
		return alphabeticallySorted;
	}

	public void setAlphabeticallySorted(boolean alphabeticallySorted) {
		this.alphabeticallySorted = alphabeticallySorted;
		updateSorting();
	}
	
}
