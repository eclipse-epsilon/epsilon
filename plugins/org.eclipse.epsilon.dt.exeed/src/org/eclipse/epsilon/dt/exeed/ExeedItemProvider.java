/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.epsilon.dt.exeed.extensions.IExeedCustomizer;
import org.eclipse.jface.resource.ImageDescriptor;

public class ExeedItemProvider extends ReflectiveItemProvider {
	protected ExeedImageTextProvider imageTextProvider;
	protected ExeedPlugin plugin = null;
	protected final Map<Class<?>, IExeedCustomizer> resourceClassToCustomizerMap;

	public ExeedItemProvider(AdapterFactory arg0, ExeedPlugin plugin, Map<Class<?>, IExeedCustomizer> resourceClassToCustomizerMap) {
		super(arg0);
		this.plugin = plugin;
		this.resourceClassToCustomizerMap = resourceClassToCustomizerMap;
	}

	@Override
	public Object getImage(Object arg0) {
		ImageDescriptor imageDescriptor = imageTextProvider.getEObjectImageDescriptor(arg0, null);
		if (imageDescriptor != null) {
			return imageDescriptor;
		} else {
			return super.getImage(arg0);
		}
	}

	@Override
	public String getText(Object arg0) {
		if (isCalledFromTreeViewer()) {
			return imageTextProvider.getEObjectLabel(arg0, super.getText(arg0), false);
		} else {
			return imageTextProvider.getEObjectReferenceLabel(arg0, imageTextProvider.getEObjectLabel(arg0, super.getText(arg0), false));
		}
	}

	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		itemPropertyDescriptors = new ArrayList<>();

		for (Iterator<EStructuralFeature> i = ((EObject) object).eClass()
				.getEAllStructuralFeatures().iterator(); i.hasNext();) {
			EStructuralFeature eFeature = i.next();
			if (!(eFeature instanceof EReference) || !((EReference) eFeature).isContainment()) {
				ItemPropertyDescriptor itemPropertyDescriptor = new ExeedItemPropertyDescriptor(
						((ComposeableAdapterFactory) adapterFactory)
								.getRootAdapterFactory(),
								getFeatureText(eFeature),
								getResourceLocator().getString(
								"_UI_Property_description",
								new Object[] { getFeatureText(eFeature),
										eFeature.getEType().getName() }),
						eFeature, eFeature.isChangeable(),
						getImageForEType(eFeature.getEType()),
						imageTextProvider);
				
				itemPropertyDescriptors.add(itemPropertyDescriptor);
				
			}
		}

		return itemPropertyDescriptors;
	}


	@Override
	protected boolean hasChildren(Object object, boolean optimized) {
		if (object instanceof EObject) {
			final EObject eob = (EObject)object;
			final Resource r = eob.eResource();
			if (r == null) {
				// we just removed this object from the resource
				return false;
			}

			IExeedCustomizer customizer = resourceClassToCustomizerMap.get(r.getClass());
			if (customizer != null && customizer.isEnabledFor(r)) {
				return customizer.hasChildren(r, eob);
			}
		}
		return super.hasChildren(object, optimized);
	}

	public void setImageTextProvider(ExeedImageTextProvider imageTextProvider) {
		this.imageTextProvider = imageTextProvider;
	}

	public void loadRegisteredEPackage(String nsUri) {
		initializeCache();
		EPackage ePackage = (EPackage) EPackage.Registry.INSTANCE.get(nsUri); 
		if (!allEPackages.contains(ePackage)) {
			allEPackages.add(ePackage);
			for (EClassifier next : ePackage.getEClassifiers()) {
				if (!allEClasses.contains(next)) {
					allEClasses.add((EClass)next);
				}
			}
		}
	}

	@Override
	protected String getFeatureText(Object feature) {
		String def = super.getFeatureText(feature);
		if (feature instanceof EStructuralFeature) {
			return imageTextProvider.getEStructuralFeatureLabel((EStructuralFeature) feature, def);
		}
		return def;
	}

	@Override
	protected void gatherMetaData(EModelElement eModelElement) {
		initializeCache();
		try { super.gatherMetaData(eModelElement); } catch (Exception ex) {}
	}

	protected Object getImageForEType(EClassifier eType) {
		if (eType instanceof EDataType) {
			if (eType.getName().equalsIgnoreCase("Integer")) {
				return ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE;
			} else if (eType.getName().equalsIgnoreCase("Boolean")) {
				return ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE;
			} else if (eType.getName().equalsIgnoreCase("String")) {
				return ItemPropertyDescriptor.TEXT_VALUE_IMAGE;
			} else if (eType.getName().equalsIgnoreCase("Real")) {
				return ItemPropertyDescriptor.REAL_VALUE_IMAGE;
			}
		}
		else {
			ImageDescriptor id = plugin.getImageDescriptor(ItemPropertyDescriptor.GENERIC_VALUE_IMAGE.toString());
			return imageTextProvider.getEClassImageDescriptor((EClass)eType, id);
		}
		return ItemPropertyDescriptor.GENERIC_VALUE_IMAGE;
	}

	protected boolean isCalledFromTreeViewer() {
		Exception ex = new Exception();
		StackTraceElement[] stackTrace = ex.getStackTrace();
		for (int i = 0; i < stackTrace.length; i++) {
			if (stackTrace[i].toString().indexOf("AbstractTreeViewer") > -1) {
				return true;
			}
		}
		return false;
	}

	protected void initializeCache() {
		// Override the initialization of super.gatherMetadata
		// to avoid double EClasses added by late discovery of extensions
		if (allRoots == null) {
			allRoots = new UniqueArrayList<>();
			allEClasses = new UniqueArrayList<>();
			allEPackages = new UniqueArrayList<>();
		}
	}
}
