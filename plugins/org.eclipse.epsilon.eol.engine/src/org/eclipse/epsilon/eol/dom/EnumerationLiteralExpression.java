/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.ModelRepository.AmbiguousEnumerationValue;

public class EnumerationLiteralExpression extends LiteralExpression<Object> {

	protected String enumerationLiteral;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		enumerationLiteral = cst.getText();
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		Object rawValue = context.getModelRepository().getEnumerationValue(enumerationLiteral);
		
		if (rawValue instanceof AmbiguousEnumerationValue) {
			AmbiguousEnumerationValue ambiEnum = (AmbiguousEnumerationValue) rawValue;

			List<String> quotedNames = ambiEnum.namesOfAlternatives.stream().map((e) -> "'" + e + "'").collect(Collectors.toList());
			String spacedQuotedNames = String.join(" ", quotedNames);
			context.getWarningStream().println(String.format(
				"Warning: The enumeration literal '%s' is ambiguous and could refer to any of the following: %s. The enumeration literal '%s' has been assumed. %s",
				enumerationLiteral, spacedQuotedNames,
				ambiEnum.nameOfSelectedAlternative,
				determineLocation(context.getFrameStack().getCurrentStatement())
			));

			return ambiEnum.selectedValue;
		}
		
		return rawValue;
	}
	
	public String getEnumerationLiteral() {
		return enumerationLiteral;
	}
	
	public void setEnumerationLiteral(String enumerationLiteral) {
		this.enumerationLiteral = enumerationLiteral;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}

	private static String determineLocation(ModuleElement statement) {
		if (statement == null)
			return "";
		else
			return "(" + statement.getFile() + "@" + statement.getRegion().getStart().getLine() + ":" + statement.getRegion().getStart().getColumn() + ")";
	}
	
}
