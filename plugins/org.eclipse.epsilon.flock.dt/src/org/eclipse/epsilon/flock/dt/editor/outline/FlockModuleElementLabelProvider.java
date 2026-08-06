/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.editor.outline;

import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleElementLabelProvider;
import org.eclipse.epsilon.flock.dt.FlockDevelopmentToolsPlugin;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRule;
import org.eclipse.epsilon.flock.model.domain.typemappings.Deletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageDeletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageRetyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;
import org.eclipse.swt.graphics.Image;

public class FlockModuleElementLabelProvider extends ErlModuleElementLabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof MigrateRule) {
			return FlockDevelopmentToolsPlugin.getDefault().createImage("icons/constructs/migrate.png");
		} 
		else if (element instanceof Retyping || element instanceof PackageRetyping) {
			return FlockDevelopmentToolsPlugin.getDefault().createImage("icons/constructs/retyping.gif");
		} 
		else if (element instanceof Deletion || element instanceof PackageDeletion) {
			return FlockDevelopmentToolsPlugin.getDefault().createImage("icons/constructs/deletion.gif");
		} 
		else {
			return super.getImage(element);
		}
	}
	
	@Override
	public String getText(Object element) {
		// The implementation of MigrationRule#toString is good for tests and debugging,
		// but looks messy in outline view, so overriding getText for MigrationRules
		
		if (element instanceof MigrateRule) {
			return ((MigrateRule)element).getOriginalType() + " " + ((MigrateRule)element).getDescriptionOfIgnoredProperties();
		
		} else if (element instanceof Retyping) {
			return "retype " + ((Retyping)element).getOriginalType() + " to " + ((Retyping)element).getEvolvedType();

		} else if (element instanceof PackageRetyping) {
			return "retype package " + ((PackageRetyping)element).getOriginalPackage() + " to " + ((PackageRetyping)element).getEvolvedPackage();
		
		} else if (element instanceof Deletion) {
			return "delete " + ((Deletion)element).getOriginalType();

		} else if (element instanceof PackageDeletion) {
			return "delete package " + ((PackageDeletion)element).getOriginalPackage();
			
		} else {
			return super.getText(element);
		}
	}
	
}
