/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.emf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;

public class WizardsExtensionPreference {
	
	static final String EXTRA_PKG_SEPARATOR = ",";

	protected String namespaceURI;
	protected String wizards;
	protected String commaSepExtraPackages;
	protected boolean enabled = true;
	protected static final String preferenceKey = "wizards.extension.preference";

	public String getNamespaceURI() {
		return namespaceURI;
	}
	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}
	public String getWizards() {
		return wizards;
	}
	public void setWizards(String wizards) {
		this.wizards = wizards;
	}
	public String[] getExtraPackages() {
		if (commaSepExtraPackages == null) {
			return new String[0];
		}
		return commaSepExtraPackages.split(EXTRA_PKG_SEPARATOR);
	}
	public void setExtraPackages(String extraPackages) {
		this.commaSepExtraPackages = extraPackages;
	}

	@Override
	public String toString() {
		return namespaceURI + "@" + wizards + "@" + enabled + "@" + commaSepExtraPackages;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public static WizardsExtensionPreference create(String str) {
		WizardsExtensionPreference w = new WizardsExtensionPreference();
		String[] parts = str.split("@");
		w.setNamespaceURI(parts[0]);
		if (parts.length > 1) w.setWizards(parts[1]);
		if (parts.length > 2) w.setEnabled(Boolean.parseBoolean(parts[2]));
		if (parts.length > 3) w.setExtraPackages(parts[3]);
		return w;
	}
	
	public static List<WizardsExtensionPreference> getPreferences() {
		List<WizardsExtensionPreference> preferences = new ArrayList<>();
		IPreferenceStore preferenceStore = EwlEmfPlugin.getDefault().getPreferenceStore();
		
		for (String str : Objects.toString(preferenceStore.getString(preferenceKey),"").split(";")) {
			if (str.trim().length() > 0) {
				preferences.add(WizardsExtensionPreference.create(str));
			}
		}
		
		return preferences;
	}
	
	public static void storePreferences(List<WizardsExtensionPreference> preferences) {
		IPreferenceStore preferenceStore = EwlEmfPlugin.getDefault().getPreferenceStore();
		String preferenceValue = "";
		
		//Set the new value
		for (WizardsExtensionPreference preference : preferences) {
			preferenceValue = preferenceValue + preference.toString() + ";";
		}
		
		preferenceStore.putValue(preferenceKey, preferenceValue);
		
		try {
			((IPersistentPreferenceStore)preferenceStore).save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
