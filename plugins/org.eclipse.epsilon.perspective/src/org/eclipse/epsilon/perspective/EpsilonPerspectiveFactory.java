/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.perspective;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

public class EpsilonPerspectiveFactory implements IPerspectiveFactory{
	
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}
	
	public void defineActions(IPageLayout layout) {
        // Add "show views".
		layout.addShowViewShortcut("org.eclipse.ui.views.ResourceNavigator");	// TODO: this is deprecated
        layout.addPerspectiveShortcut("org.eclipse.epsilon.perspective");
	}
	
	public void defineLayout(IPageLayout layout){
        
		// editors are placed for free.
        String editorArea = layout.getEditorArea();
     
        // place navigator to the left (of editor area)
        IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, (float) 0.25, editorArea);
        left.addView(IPageLayout.ID_PROJECT_EXPLORER);
        //left.addView("org.eclipse.ui.navigator.ProjectExplorer");
        
        //IFolderLayout leftBottom = layout.createFolder("leftBottom", IPageLayout.BOTTOM, 0.7f, "left");
        //leftBottom.addView("org.eclipse.epsilon.common.dt.tools.ToolsView");
        
        // problem view at the bottom (of editor area)
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.70, editorArea);
        bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
        bottom.addView(IPageLayout.ID_PROP_SHEET); 
        bottom.addView("org.eclipse.pde.runtime.LogView");
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
        bottom.addView("org.eclipse.epsilon.profiling.dt.ProfilerView");
        bottom.addView("org.eclipse.epsilon.common.dt.tools.ToolsView");
        
        //bottom.addView(IPageLayout.ID_PROP_SHEET); 
        //bottom.addView("org.eclipse.pde.runtime.LogView");
        
        // outline view to right (of editor area)
        IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.75, editorArea);
        //right.addView(IPageLayout.ID_PROBLEM_VIEW);
        
        //IFolderLayout rightBottom = layout.createFolder("rightBottom", IPageLayout.BOTTOM, (float) 0.5, "right" );
        //rightBottom.addView(IPageLayout.ID_TASK_LIST);
        right.addView(IPageLayout.ID_OUTLINE);
        //right.addView("org.eclipse.epsilon.evl.dt.views.ValidationView");
        //right.addView("org.eclipse.epsilon.egl.dt.views.TemplateView");
        
        // add shortcuts
        layout.addNewWizardShortcut("org.eclipse.epsilon.eol.dt.wizards.NewEolFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.evl.dt.wizards.NewEvlFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.etl.dt.wizards.NewEtlFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.ecl.dt.wizards.NewEclFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.eml.dt.wizards.NewEmlFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.egl.dt.wizards.NewEglFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.egl.dt.wizards.NewEgxFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.ewl.dt.wizards.NewEwlFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.emf.util.NewEmfModelWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.emf.util.NewRegisteredEPackageWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.dt.exeed.modelink.NewModeLinkWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.hutn.dt.wizards.NewHutnFileWizard");
        layout.addNewWizardShortcut("org.eclipse.epsilon.flock.dt.wizards.NewMigrationFileWizard");

        
        //layout.addNewWizardShortcut("org.eclipse.weaver.WeaverWizardID");
        
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
		
        layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
        
        layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
        
        layout.addPerspectiveShortcut("org.eclipse.epsilon.perspective");
	}
}
