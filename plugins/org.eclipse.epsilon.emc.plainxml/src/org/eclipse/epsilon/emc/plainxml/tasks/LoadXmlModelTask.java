/*******************************************************************************
 * Copyright (c) 2026 fhir-emc contributors.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.emc.plainxml.tasks;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;

/**
 * Ant task for loading a PlainXML model with expand entity references control.
 * Registered as epsilon.xml.loadModel via the plugin.xml extension point.
 * The declaring plugin's classloader loads this class.
 */
public class LoadXmlModelTask extends Task {

    protected String name;
    protected String alias;
    protected File file;
    protected String uri;
    protected boolean read = true;
    protected boolean store = false;
    protected boolean cached = true;
    protected boolean expand = true;
    protected String xml;

    @Override
    public void execute() throws BuildException {
        PlainXmlModel model = new PlainXmlModel();
        model.setName(name);
        if (alias != null) model.getAliases().add(alias);
        model.setReadOnLoad(read);
        model.setStoredOnDisposal(store);
        model.setCachingEnabled(cached);
        model.setExpandEntityReferences(expand);
        if (file != null) model.setFile(file);
        if (uri != null) model.setUri(uri);
        if (xml != null && !xml.trim().isEmpty()) model.setXml(xml.trim());
        try {
            model.load();
            getProject().addReference("epsilon.model." + name, model);
        } catch (Exception ex) {
            throw new BuildException(ex);
        }
    }

    public void addText(String msg) {
        if (msg != null) {
            if (xml == null) xml = "";
            xml += getProject().replaceProperties(msg);
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public File getFile() { return file; }
    public void setFile(File file) { this.file = file; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public boolean isStore() { return store; }
    public void setStore(boolean store) { this.store = store; }
    public boolean isCached() { return cached; }
    public void setCached(boolean cached) { this.cached = cached; }
    public boolean isExpand() { return expand; }
    public void setExpand(boolean expand) { this.expand = expand; }
}
