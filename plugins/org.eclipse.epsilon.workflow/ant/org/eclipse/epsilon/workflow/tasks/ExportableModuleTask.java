/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.util.Collection;

import org.eclipse.epsilon.eol.models.java.JavaModel;

/**
 * Adds the exportAsModel string property. When this
 * property has a non-null value, the module will 
 * export an execution model after it is executed.
 * The value of the property controls the name of
 * the exported model. For example:
 * 
 * <etl.module ... exportAsModel="Trace"/>
 * 
 * The contents of the exported model are dictated by
 * the type of module. For example, an ETL module
 * might export a transformation trace model, while an
 * EVL module might export a model of unsatisfied
 * constraints.  
 */
public abstract class ExportableModuleTask extends ExecutableModuleTask {

	protected String exportAsModel = null;

	public String getExportAsModel() {
		return exportAsModel;
	}
	
	public void setExportAsModel(String exportAsModel) {
		this.exportAsModel = exportAsModel;
	}

	@Override
	protected void examine() throws Exception {
		if (shouldExportAsModel()) {
			exportJavaModel(getObjectsForExportedModel(), getClassesForExportedModel());
		}
	}
	
	protected boolean shouldExportAsModel() {
		return exportAsModel != null;
	}
	
	private void exportJavaModel(Collection<? extends Object> objects, Collection<? extends Class<?>> classes) {
		final JavaModel model = new JavaModel(objects, classes);
		model.setName(exportAsModel);
		getProjectRepository().addModel(model);
	}

	protected abstract Collection<? extends Object>   getObjectsForExportedModel();
	protected abstract Collection<? extends Class<?>> getClassesForExportedModel();
	
}
