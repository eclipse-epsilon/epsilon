/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.common.dt.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewFileWizardPage2 extends WizardNewFileCreationPage {

    public NewFileWizardPage2(IStructuredSelection selection, String title, String description, String fileExtension) {
        super("page", selection);
        setTitle(title);
        setDescription(description);
        setFileExtension(fileExtension);
    }
}
 
