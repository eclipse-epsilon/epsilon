/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.tools;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.launching.extensions.ToolExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;


public class ToolsView extends ViewPart {
	private TreeViewer viewer;
	
	class ViewContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			
			ArrayList<ToolVariable> toolClasses = new ArrayList<>();
			
			for (ToolExtension toolExtension : ToolExtension.getInstances()) {
				Bundle bundle = Platform.getBundle(toolExtension.getConfigurationElement().getContributor().getName());
				try {
					ToolVariable toolVariable = new ToolVariable();
					toolVariable.setClazz(bundle.loadClass(toolExtension.getClazz()));
					toolVariable.setName(toolExtension.getDefaultName());
					toolClasses.add(toolVariable);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			return toolClasses.toArray();
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ToolVariable) {
				Class<?> clazz = ((ToolVariable) parentElement).getClazz();
				return getMethods(clazz, true);
			}
			else if (parentElement instanceof Method) {
				Method method = (Method) parentElement;
				if (method.getReturnType() != Void.TYPE) {
					return getMethods(method.getReturnType(), false);
				}
				else {
					return Collections.EMPTY_LIST.toArray();
				}
			}
			else {
				return Collections.EMPTY_LIST.toArray();
			}
		}
		
		protected Object[] getMethods(Class<?> clazz, boolean declaredOnly) {
			ArrayList<Method> methods = new ArrayList<>();
			Method[] classMethods;
			
			if (declaredOnly) classMethods = clazz.getDeclaredMethods();
			else classMethods = clazz.getMethods();
			
			for (Method method : classMethods) {
				if ((method.getModifiers() & Modifier.PUBLIC) != 0) {
					methods.add(method);
				}
			}
			return methods.toArray();
		}
		
		@Override
		public Object getParent(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof ToolVariable) return true;
			else if (element instanceof Method) return ((Method) element).getReturnType()!=Void.TYPE;
			else return false;
		}
	
	}
	
	class ViewLabelProvider extends LabelProvider {

		Image classImage = EpsilonCommonsPlugin.getDefault().getImageDescriptor("icons/class.png").createImage();	
		Image methodImage = EpsilonCommonsPlugin.getDefault().getImageDescriptor("icons/method.gif").createImage();
		
		@Override
		public String getText(Object obj) {
			if (obj instanceof ToolVariable) {
				Class<?> clazz = ((ToolVariable) obj).getClazz();
				return clazz.getSimpleName() + " (" + clazz.getCanonicalName() + ")";
			}
			else {
				Method method = (Method) obj;
				String sig = "";
				sig = method.getName() + "(";
				int loop = 0;
				for (Class<?> parameterType : method.getParameterTypes()) {
					sig = sig + parameterType.getSimpleName();
					loop ++;
					if (loop < method.getParameterTypes().length) sig = sig + ", ";
				}
				sig = sig + ") : " + method.getReturnType().getSimpleName(); 
				
				return sig;
			}
		}
		@Override
		public Image getImage(Object obj) {
			if (obj instanceof ToolVariable) {
				return classImage;
			}
			else {
				return methodImage;
			}
		}
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setComparator(new ViewerComparator());
		viewer.setInput(getViewSite());		
		contributeToActionBars();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(new InsertToolVariableDeclarationAction());
	}

	protected class InsertToolVariableDeclarationAction extends Action {
		
		public InsertToolVariableDeclarationAction() {
			setText("Insert tool variable declaration");
			setImageDescriptor(EpsilonCommonsPlugin.getDefault().getImageDescriptor("icons/insert.png"));
		}
		
		@Override
		public void run() {
			IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editor instanceof AbstractModuleEditor) {
				Object selection = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
				if (selection instanceof ToolVariable) {
					Class<?> toolClass = ((ToolVariable) selection).getClazz();
					String toolVariableDeclaration = "var " + ((ToolVariable) selection).getName() + " : new Native(\"" + toolClass.getCanonicalName() + "\");";
					((AbstractModuleEditor) editor).insertText(toolVariableDeclaration);
				}
			}
		}
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
