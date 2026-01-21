/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.module;

import java.io.File;
import java.util.Objects;

import org.eclipse.epsilon.common.parse.Region;

public class ModuleMarker {
	
	protected File file;
	protected Region region;
	protected String message;
	protected Severity severity;
	
	public ModuleMarker() {}
	
	public ModuleMarker(AbstractModuleElement element, String message, Severity severity) {
		this(element.getFile(), element.getRegion(), message, severity);
	}
	
	public ModuleMarker(File file, Region region, String message, Severity severity) {
		super();
		this.file = file;
		this.region = region;
		this.message = message;
		this.severity = severity;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public enum Severity {
		Information,
		Warning,
		Error
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(file, message, region, severity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModuleMarker other = (ModuleMarker) obj;
		return Objects.equals(file, other.file) 
				&& Objects.equals(message, other.message)
				&& Objects.equals(region, other.region) 
				&& severity == other.severity;
	}
	
	public String toString() {
		String severityString;
		switch (this.severity) {
		case Information:
			severityString = "Information";
			break;
		case Warning:
			severityString = "Warning";
			break;
		case Error:
			severityString = "Error";
			break;
		default:
			severityString = "Missing severity level";
			break;
		}

		if (null == this.region) {
			return String.format("%s [region null] %s", severityString, this.message);
		}

		return String.format("%s [%s] %s", severityString, this.region, this.message);
	}
}
