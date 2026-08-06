/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - add options for forcing the Exeed editors
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ModeLink {
	
	private static final String ATTR_FORCE_EXEED_LEFT = "forceExeedL";
	private static final String ATTR_FORCE_EXEED_MIDDLE = "forceExeedM";
	private static final String ATTR_FORCE_EXEED_RIGHT = "forceExeedR";
	private static final boolean ATTR_FORCE_EXEED_DEFAULT = false;

	private static final String ATTR_MODEL_POS = "position";
	private static final String ATTR_MODEL_PATH = "path";
	private static final String ATTR_THREE_WAY = "threeWay";
	private static final boolean ATTR_THREE_WAY_DEFAULT = true;

	private List<LinkedModel> linkedModels = new ArrayList<>();

	private boolean threeWay = ATTR_THREE_WAY_DEFAULT;
	private boolean forceExeedL = ATTR_FORCE_EXEED_DEFAULT;
	private boolean forceExeedM = ATTR_FORCE_EXEED_DEFAULT;
	private boolean forceExeedR = ATTR_FORCE_EXEED_DEFAULT;

	public void load(IFile file) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(file.getContents());
		Element root = doc.getDocument().getRootElement();
		linkedModels.clear();

		threeWay = getBooleanAttribute(root, ATTR_THREE_WAY, ATTR_THREE_WAY_DEFAULT);
		forceExeedL = getBooleanAttribute(root, ATTR_FORCE_EXEED_LEFT, ATTR_FORCE_EXEED_DEFAULT);
		forceExeedM = getBooleanAttribute(root, ATTR_FORCE_EXEED_MIDDLE, ATTR_FORCE_EXEED_DEFAULT);
		forceExeedR = getBooleanAttribute(root, ATTR_FORCE_EXEED_RIGHT, ATTR_FORCE_EXEED_DEFAULT);

		for (Object next : root.getChildren()) {
			if (next instanceof Element) {
				Element el = (Element) next;
				linkedModels.add(new LinkedModel(el.getAttributeValue(ATTR_MODEL_PATH), el.getAttributeValue(ATTR_MODEL_POS)));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void store(IFile file) {
		Document doc = new Document();
		Element root = new Element("modelink");
		root.getAttributes().add(new Attribute(ATTR_THREE_WAY, threeWay + ""));
		root.getAttributes().add(new Attribute(ATTR_FORCE_EXEED_LEFT, forceExeedL + ""));
		root.getAttributes().add(new Attribute(ATTR_FORCE_EXEED_MIDDLE, forceExeedM + ""));
		root.getAttributes().add(new Attribute(ATTR_FORCE_EXEED_RIGHT, forceExeedR + ""));
		doc.setRootElement(root);

		for (LinkedModel m : linkedModels) {
			Element el = new Element("model");
			el.getAttributes().add(new Attribute(ATTR_MODEL_PATH, m.getPath()));
			el.getAttributes().add(new Attribute(ATTR_MODEL_POS, m.getPosition().toString()));
			root.addContent(el);
		}
		
		try {
			new StoreXmlDocumentOperation(doc, file).run(null);
			file.refreshLocal(0, null);
		} catch (Exception e) {
			LogUtil.log(e);
		}
	}

	public List<LinkedModel> getLinkedModels() {
		return linkedModels;
	}

	public boolean isThreeWay() {
		return threeWay;
	}

	public void setThreeWay(boolean threeWay) {
		this.threeWay = threeWay;
	}

	public boolean isForceExeed(ModelPosition position) {
		switch (position) {
		case LEFT:
			return forceExeedL;
		case RIGHT:
			return forceExeedR;
		default:
			return forceExeedM;
		}
	}

	public void setForceExeed(ModelPosition position, boolean force) {
		switch (position) {
			case LEFT:
				forceExeedL = force;
				break;
			case RIGHT:
				forceExeedR = force;
				break;
			default:
				forceExeedM = force;
				break;
		}
	}

	private boolean getBooleanAttribute(Element e, final String attribute, final boolean defaultValue) {
		final String value = e.getAttributeValue(attribute);
		if (value == null) {
			return defaultValue;
		}
		else {
			return !value.equalsIgnoreCase("false");
		}
	}
}
