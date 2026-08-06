/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.examples.egl.gmf.papyrus;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityDiagramEditPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ListActivities implements IObjectActionDelegate {

	private Resource resource;

	@Override
	public void run(IAction action) {
		try {
			final IEolModule eglModule = new EglTemplateFactoryModuleAdapter(new EglTemplateFactory());
			final ModelRepository modelRepo = eglModule.getContext().getModelRepository();
			modelRepo.addModel(new InMemoryEmfModel(resource));
			eglModule.parse(ListActivities.class.getResource("/listActivities.egl").toURI());

			final Path pathToModel = new Path(resource.getURI().toPlatformString(true));
			final IFile ifModel = ResourcesPlugin.getWorkspace().getRoot().getFile(pathToModel);
			final File fModel = ifModel.getLocation().toFile().getCanonicalFile();
			final File fTarget = new File(fModel.getParentFile(), "report.txt");

			final FileWriter fW = new FileWriter(fTarget);
			try {
				fW.write((String)eglModule.execute());
			} finally {
				fW.close();
			}
			ifModel.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (Exception e) {
			ExampleM2TPlugin.getDefault().logError(e.getMessage(), e);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection sel = (IStructuredSelection)selection;

			for (Iterator<?> it = sel.iterator(); it.hasNext(); ) {
				final Object e = it.next();
				if (e instanceof ActivityDiagramEditPart) {
					final ActivityDiagramEditPart aed = (ActivityDiagramEditPart)e;
					final EObject model = aed.resolveSemanticElement();
					this.resource = model.eResource();
				}
			}
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// ignore
	}

}
