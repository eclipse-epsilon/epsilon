/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class ConfigureModeLinkWizard extends Wizard {
	
	protected IFile file;
	protected ModeLink modeLink;
	protected ConfigureLinkedModelsPage leftPage;
	protected ConfigureLinkedModelsPage middlePage;
	protected ConfigureLinkedModelsPage rightPage;
	
	public ConfigureModeLinkWizard() {
		
	}
	
	@Override
	public void addPages() {
		if (modeLink == null) modeLink = new ModeLink();
		//addPage(new ConfigureThreeWayPage("Three way", modeLink));
		leftPage = new ConfigureLinkedModelsPage("Left models", modeLink, ModelPosition.LEFT);
		addPage(leftPage);
		middlePage = new ConfigureLinkedModelsPage("Middle models", modeLink, ModelPosition.MIDDLE);
		addPage(middlePage);
		rightPage = new ConfigureLinkedModelsPage("Right models", modeLink, ModelPosition.RIGHT);
		addPage(rightPage);
	}
	
	@Override
	public boolean performFinish() {
		modeLink.store(file);
		return true;
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
		modeLink = new ModeLink();
		try {
			modeLink.load(file);
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.logException(e);
		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == leftPage && !modeLink.isThreeWay()) {
			return rightPage;
		}
		else {
			return super.getNextPage(page);
		}
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page == rightPage && !modeLink.isThreeWay()) {
			return leftPage;
		}
		else {
			return super.getPreviousPage(page);
		}
	}

	
	
}
