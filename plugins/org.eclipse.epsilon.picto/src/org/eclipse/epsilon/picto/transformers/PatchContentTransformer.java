/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.egl.patch.Line;
import org.eclipse.epsilon.egl.patch.TextBlock;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.picto.Layer;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;
import org.eclipse.epsilon.picto.dom.Patch;

public class PatchContentTransformer implements ViewContentTransformer {

	@Override
	public boolean canTransform(ViewContent content) {
		String contentFormat = content.getFormat();
		return content.getPatches()
			.stream().map(Patch::getFormat)
			.anyMatch(f -> f != null && f.equalsIgnoreCase(contentFormat));
	}

	@Override
	public String getLabel(ViewContent content) {
		return "Patched " + content.getLabel();
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		List<Patch> appliedPatches = new ArrayList<>();
		String patchedText = content.getText();
		for (Patch patch : content.getPatches()) {
			if (patch.getFormat().equals(content.getFormat())) {
				
				if (applies(patch, content)) {
					org.eclipse.epsilon.egl.patch.Patch p = new org.eclipse.epsilon.egl.patch.Patch(patch.getContent().split(System.lineSeparator()));
					TextBlock b = new TextBlock(patchedText.split(System.lineSeparator()));
					
					patchedText = p.apply(b).getLines().stream()
							.map(Line::getText)
							.collect(Collectors.joining(System.lineSeparator()));
				}
				
				appliedPatches.add(patch);
			}
		}
		
		List<Patch> remainingPatches = new ArrayList<>(content.getPatches());
		remainingPatches.removeAll(appliedPatches);
		return new ViewContent(content.getFormat(), patchedText, content.getFile(), content.getLayers(), remainingPatches, content.getBaseUris());
		
	}
	
	protected boolean applies(Patch patch, ViewContent content) {
		if (patch.getApplies() == null) return true;
		
		try {
			EolModule module = new EolModule();
			module.parse("return " + patch.getApplies() + ";");
			module.getContext().getFrameStack().put(
				content.getLayers().stream()
					.collect(Collectors.toMap(Layer::getId, Layer::isActive)),
				true
			);
			return (boolean) module.execute();
		}
		catch (Exception ex) {
			LogUtil.log(ex);
			return false;
		}
	}
	
}
