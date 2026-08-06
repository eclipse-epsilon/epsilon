/*********************************************************************
* Copyright (c) 2022 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.yaml.test;

import org.eclipse.epsilon.emc.yaml.YamlModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class YamlModelTests {
	
	protected YamlModel model;
	protected EolEvaluator evaluator;
	
	@Before
	public void setup() throws Exception {
		model = new YamlModel();
		model.setName("M");
		model.setReadOnLoad(true);
		model.setYamlContent(
				  "- firstName:" + newline()
				+ "    age: 45" + newline()
				+ "  data:" + newline()
				+ "    isEncrypted: true" + newline()
				+ "  firewall:" + newline()
				+ "  - type: \"true\"" + newline()
				+ "  - type: warning" + newline()
				+ "    number: 4567868" + newline()
				+ "- coordinate:" + newline()
				+ "    x_axis: 33" + newline()
				+ "    y_axis: 10" + newline()
				+ "  lastName: Melzer" + newline()
				+ "  dataTypes:" + newline()
				+ "  - numerics" + newline()
				+ "  - strings" + newline()
				+ "  - booleans"
		);
		model.load();
		evaluator = new EolEvaluator(model);
	}
	
	public String newline() {
		return System.getProperty("line.separator");
	}
	
	@Test
	public void testIsPropertyCollection() {
		assertEquals(evaluator.evaluate("l_firewall.all.sc_type").toString(), "EolSequence [[type=true, type=warning]]");
	}
	
	@Test
	public void testIsPropertyOneElement() {
		assertEquals(evaluator.evaluate("l_firewall.all.se_type").toString(), "EolSequence [type=true]");
	}
	
	@Test
	public void testGetName() {
		assertEquals(evaluator.evaluate("s_number.all.first().name"), "number");
	}
	
	@Test
	public void testGetType() {
		assertEquals(evaluator.evaluate("s_number.all.first().type").toString(), "ScalarNode");
	}
	
	@Test
	public void testGetValue() {
		assertEquals(evaluator.evaluate("s_number.all.first().value"), 4567868);
	}
	
	@Test
	public void testGetValueString() {
		assertEquals(evaluator.evaluate("s_number.all.first().s_value"), "4567868");
	}
	
	@Test
	public void testGetValueBoolean() {
		assertEquals(evaluator.evaluate("l_firewall.all.se_type.first().b_value"), true);
	}
	
	@Test
	public void testGetValueInteger() {
		assertEquals(evaluator.evaluate("s_number.all.first().i_value"), 4567868);
	}
	
	@Test
	public void testGetValueDouble() {
		assertEquals(evaluator.evaluate("s_number.all.first().d_value"), 4567868.0d);
	}
	
	@Test
	public void testGetValueFloat() {
		assertEquals(evaluator.evaluate("s_number.all.first().f_value"), 4567868.0f);
	}
	
	@Test
	public void testGetScalarNode() {
		assertEquals(evaluator.evaluate("s_lastName.all.first()").toString(), "lastName=Melzer");
	}
	
	@Test
	public void testGetMappingNode() {
		assertEquals(evaluator.evaluate("m_data.all.first()").toString(), "data={isEncrypted=true}");
	}
	
	@Test
	public void testGetListNode() {
		assertEquals(evaluator.evaluate("l_firewall.all.first()").toString(), "firewall=[{type=true}, {type=warning, number=4567868}]");
	}
	
	@Test
	public void testGetScalarNodes() {
		assertEquals(evaluator.evaluate("ScalarNode.all").toString(), "[age=45, isEncrypted=true, type=true, type=warning, number=4567868, x_axis=33, y_axis=10, lastName=Melzer]");
	}
	
	@Test
	public void testGetMappingNodes() {
		assertEquals(evaluator.evaluate("MappingNode.all").toString(), "[firstName={age=45}, data={isEncrypted=true}, coordinate={x_axis=33, y_axis=10}]");
	}

	@Test
	public void testGetListNodes() {
		assertEquals(evaluator.evaluate("ListNode.all").toString(), "[firewall=[{type=true}, {type=warning, number=4567868}], dataTypes=[numerics, strings, booleans]]");
	}
	
	@Test
	public void testSetNode() {
		evaluator.execute("m_data.all.at(0).value = 100;");
		assertEquals(evaluator.evaluate("s_data.all.at(0).value"), 100);
	}
	
	@Test
	public void testDeleteNode() {
		evaluator.execute("delete m_firstName.all.at(0);");
		assertEquals(evaluator.evaluate("m_firstName.all").toString(), "[]");
	}
	
	@Test
	public void testCreateNodes() {
		String previousYamlContent = evaluator.evaluate("M.root").toString();
		evaluator.execute(
				  "M.setRootAsList();" + newline()
				+ "var root = M.root;" + newline()
				+ "root.value.addRows(2);" + newline()
				+ "var firstRow = root.value.at(0);" + newline()
				+ "var secondRow = root.value.at(1);" + newline()
				+ "var firstNameNode = new m_firstName;" + newline()
				+ "var ageNode = new s_age(45);" + newline()
				+ "firstNameNode.value.appendNode(ageNode);" + newline()
				+ "var dataNode = new m_data;" + newline()
				+ "var isEncryptedNode = new s_isEncrypted(true);" + newline()
				+ "dataNode.value.appendNode(isEncryptedNode);" + newline()
				+ "var firewallNode = new l_firewall(2);" + newline()
				+ "var typeNode1 = new s_type(\"true\");" + newline()
				+ "var typeNode2 = new s_type(\"warning\");" + newline()
				+ "var numberNode = new s_number(4567868);" + newline()
				+ "firewallNode.value.at(0).appendNode(typeNode1);" + newline()
				+ "firewallNode.value.at(1).appendNode(typeNode2);" + newline()
				+ "firewallNode.value.at(1).appendNode(numberNode);" + newline()
				+ "var coordinate = new m_coordinate;" + newline()
				+ "var x_axisNode = new s_x_axis(33);" + newline()
				+ "var y_axisNode = new s_y_axis(10);" + newline()
				+ "coordinate.value.appendNode(x_axisNode);" + newline()
				+ "coordinate.value.appendNode(y_axisNode);"
				+ "var lastNameNode = new s_lastName(\"Melzer\");" + newline()
				+ "var dataTypesNode = new l_dataTypes(3);" + newline()
				+ "dataTypesNode.value.set(0, \"numerics\");" + newline()
				+ "dataTypesNode.value.set(1, \"strings\");" + newline()
				+ "dataTypesNode.value.set(2, \"booleans\");" + newline()
				+ "firstRow.appendNode(firstNameNode);" + newline()
				+ "firstRow.appendNode(dataNode);" + newline()
				+ "firstRow.appendNode(firewallNode);" + newline()
				+ "secondRow.appendNode(coordinate);" + newline()
				+ "secondRow.appendNode(lastNameNode);" + newline()
				+ "secondRow.appendNode(dataTypesNode);");
		assertEquals(previousYamlContent, evaluator.evaluate("M.root").toString());
	}
	
}