/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class ViewTreeContentProvider implements ITreeContentProvider {
	
	@Override
	public boolean hasChildren(Object element) {
		return !((ViewTree) element).getChildren().isEmpty();
	}
	
	@Override
	public Object getParent(Object element) {
		return ((ViewTree) element).getParent();
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		
		List<ViewTree> children = ((ViewTree) parentElement).getChildren();
		Object[] childrenArray = children.toArray();
		
		Arrays.parallelSort(childrenArray, (o1, o2) -> {
			ViewTree v1 = (ViewTree) o1;
			ViewTree v2 = (ViewTree) o2;
			
			int v1Position = children.indexOf(v1) + 1;
			if (v1.getPosition() != null) v1Position = v1.getPosition();
			
			int v2Position = children.indexOf(v2) + 1;
			if (v2.getPosition() != null) v2Position = v2.getPosition();
			
			return v1Position - v2Position;
		});
		
		return childrenArray;
	}
}
