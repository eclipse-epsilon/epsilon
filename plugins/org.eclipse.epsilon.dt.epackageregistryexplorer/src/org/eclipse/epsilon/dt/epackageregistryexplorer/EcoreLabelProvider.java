/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.dt.epackageregistryexplorer;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class EcoreLabelProvider extends LabelProvider implements IFontProvider, IColorProvider {
	
	protected PackageRegistryExplorerView view;
	protected Font bold;
	protected Font italic;
	
	public EcoreLabelProvider(PackageRegistryExplorerView view) {
		this.view = view;		
	}
	
	@Override
	public Image getImage(Object element) {
		if (element instanceof EPackage) {
			return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.emf.ecore.edit", "icons/full/obj16/EPackage.gif").createImage();
		}
		else if (element instanceof EClass) {
			if (DecoratorSupport.isDecorator((EClass)element)) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/decorator.gif").createImage();
			}
			return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.emf.ecore.edit", "icons/full/obj16/EClass.gif").createImage();	
		}
		else if (element instanceof EDataType) {
			return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.emf.ecore.edit", "icons/full/obj16/EDataType.gif").createImage();
		}
		else if (element instanceof EAttribute) {
			return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.emf.ecore.edit", "icons/full/obj16/EAttribute.gif").createImage();
		}
		else if (element instanceof EOperation) {
			return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.emf.ecore.edit", "icons/full/obj16/EOperation.gif").createImage();
		}
		else if (element instanceof EReference) {
			EReference eReference = (EReference) element;
			if (DecoratorSupport.isHook(eReference)) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/hook.gif").createImage();
			}
			return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.emf.ecore.edit", "icons/full/obj16/EReference.gif").createImage();
		}
		else if (element instanceof DecoratorHookDescriptor) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/hookFeature.gif").createImage();			
		}
		else if (element instanceof SubTypesDescriptor) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/subtypes.png").createImage();			
		}
		else if (element instanceof SuperTypesDescriptor) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/supertypes.png").createImage();			
		}
		else return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof EPackage) {
			EPackage ePackage = (EPackage) element;
			return ePackage.getName() + " (" + ePackage.getNsURI() + ")";
		}
		else if (element instanceof EClassifier) {
			return ((EClassifier) element).getName();
		}
		else if (element instanceof EStructuralFeature) {
			EStructuralFeature sf = ((EStructuralFeature) element);
			String signature = sf.getName();
			if (sf.getEType() != null) {
				signature = signature + " : " + sf.getEType().getName();
			}
			if (view.isShowOppositeReference() && sf instanceof EReference) {
				EReference opposite = ((EReference) sf).getEOpposite();
				if (opposite != null) {
					signature = signature + "#" + opposite.getName();
				}
			}
			if (sf.isMany()) {
				signature = signature + " [*]";
			}
			return signature;
		}
		else if (element instanceof EOperation) {
			EOperation eOperation = (EOperation) element;
			String signature =
				eOperation.getName() + "(" +
				eOperation.getEParameters().stream()
					.map(p -> p.getName() + ":" + getTypeName(p.getEType()))
					.collect(Collectors.joining(", ")) +
				") : " + getTypeName(eOperation.getEType());
			
			if (eOperation.isMany()) {
				signature += " [*]";
			}
			return signature;
		}
		else if (element instanceof DecoratorHookDescriptor) {
			return getText(((DecoratorHookDescriptor) element).getEStructuralFeature());
		}
		else if (element instanceof SubTypesDescriptor || element instanceof SuperTypesDescriptor) {
			return "";
		}
		else {
			return element.toString();
		}
	}
	
	protected String getTypeName(EClassifier classifier) {
		if (classifier == null) {
			return "void";
		}
		else {
			return classifier.getName();
		}
	}
	
	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Font getFont(Object element) {
		
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			if (eClass.isAbstract()) {
				return getItalic();
			}
		}
		if (element instanceof EReference) {
			EReference eReference = (EReference) element;
			if (eReference.isContainment()) {
				return getBold();
			}
		}
		if (element instanceof EStructuralFeature) {
			EStructuralFeature eStructuralFeature = (EStructuralFeature) element;
			if (eStructuralFeature.isDerived()) {
				return getItalic();
			}
		}
		return null;
	}
	
	protected Font getItalic() {
		if (italic == null) {
			italic = FontDescriptor.createFrom(view.getFeatureViewer().getControl().getFont().getFontData()).
					setStyle(SWT.ITALIC).createFont(Display.getCurrent());
		}
		return italic;
	}
	
	protected Font getBold() {
		if (bold == null) {
			bold = FontDescriptor.createFrom(view.getFeatureViewer().getControl().getFont().getFontData()).
					setStyle(SWT.BOLD).createFont(Display.getCurrent());
		}
		return bold;
	}
	
}
 
