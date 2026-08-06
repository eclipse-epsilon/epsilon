/*******************************************************************************
 * Copyright (c) 2012-2018 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - move id() to ModelElementOperationContributor
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IUndefined;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolMapType;
import org.eclipse.epsilon.eol.types.EolNoType.EolNoTypeInstance;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.eol.types.EolType;

public class AnyOperationContributor extends OperationContributor {
	
	@Override
	public boolean contributesTo(Object target) {
		return !(target instanceof EolNoTypeInstance);
	}
	
	public Object type() {
		Object target = getTarget();
		
		if (EolPrimitiveType.String.isType(target)) {
			return EolPrimitiveType.String;
		}
		else if (EolPrimitiveType.Boolean.isType(target)) {
			return EolPrimitiveType.Boolean;
		}
		else if (EolPrimitiveType.Real.isType(target)) {
			return EolPrimitiveType.Real;
		}
		else if (EolPrimitiveType.Integer.isType(target)) {
			return EolPrimitiveType.Integer;
		}
		else if (EolCollectionType.Bag.isType(target)) {
			return EolCollectionType.Bag;
		}
		else if (EolCollectionType.Sequence.isType(target)) {
			return EolCollectionType.Sequence;
		}
		else if (EolCollectionType.Set.isType(target)) {
			return EolCollectionType.Set;
		}
		else if (EolCollectionType.OrderedSet.isType(target)) {
			return EolCollectionType.OrderedSet;
		}
		else if (EolCollectionType.ConcurrentBag.isType(target)) {
			return EolCollectionType.ConcurrentBag;
		}
		else if (EolCollectionType.ConcurrentSet.isType(target)) {
			return EolCollectionType.ConcurrentSet;
		}
		else if (EolMapType.Map.isType(target)) {
			return EolMapType.Map;
		}
		else if (EolMapType.ConcurrentMap.isType(target)) {
			return EolMapType.ConcurrentMap;
		}
		
		IModel model = getContext().getModelRepository().getOwningModel(target);
		if (model != null) {
			return model.getTypeOf(target);
		}
		
		return null;
	}
	
	public boolean instanceOf(EolType type) {
		return isKindOf(type);
	}
	
	public boolean isTypeOf(EolType type) {
		return type.isType(getTarget());
	}
	
	public boolean isKindOf(EolType type) {
		return type.isKind(getTarget());
	}
	
	public Class<?> getNativeType() {
		return getTarget().getClass();
	}
	
	public Class<?> nativeType() {
		return getNativeType();
	}
	
	public IModel getOwningModel() {
		return owningModel();
	}
	
	public IModel owningModel() {
		return getContext().getModelRepository().getOwningModel(getTarget());
	}
	
	public boolean hasProperty(String property) throws Exception {
		Object target = getTarget();
		IPropertyGetter getter = getContext().getIntrospectionManager().getPropertyGetterFor(target, property, getContext());
		return getter.hasProperty(target, property, getContext());
	}
	
	public String asString() {
		return getContext().getPrettyPrinterManager().print(getTarget());
	}
	
	public boolean isDefined() {
		Object target = getTarget();
		if (target == null || target instanceof IUndefined) {
			return false;
		}
		else if (target instanceof String && (((String) target).isEmpty())) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public final boolean isUndefined() {
		return !isDefined();
	}
	
	public Object ifUndefined(Object alternative) {
		if (isUndefined()) return alternative;
		else return getTarget();
	}
	
	public Object println() {
		return println("", "");
	}
	
	public Object println(Object prefix) {
		return println(prefix, "");
	}
	
	public Object println(Object prefix, Object suffix) {
		getContext().getOutputStream().
			println(prefix + getContext().getPrettyPrinterManager().print(getTarget()) + suffix);
		return getTarget();
	}
	
	public Object print() {
		return print("", "");
	}
	
	public Object print(Object prefix) {
		return print(prefix, "");
	}
	
	public Object print(Object prefix, Object suffix) {
		getContext().getOutputStream().
			print(prefix + getContext().getPrettyPrinterManager().print(getTarget()) + suffix);
		return getTarget();
	}
	
	public Object err() {
		return err("", "");
	}
	
	public Object err(Object prefix) {
		return err(prefix, "");
	}
	
	public Object err(Object prefix, Object suffix) {
		getContext().getErrorStream().
			print(prefix + getContext().getPrettyPrinterManager().print(getTarget()) + suffix);
		return getTarget();
	}
	
	public Object errln() {
		return errln("", "");
	}
	
	public Object errln(Object prefix) {
		return errln(prefix, "");
	}
	
	public Object errln(Object prefix, Object suffix) {
		getContext().getErrorStream().
			println(prefix + getContext().getPrettyPrinterManager().print(getTarget()) + suffix);
		return getTarget();
	}
	
	public String format(String formatString) {
		return String.format(formatString, getTarget());
	}
	
	public int asInteger() {
		Object target = getTarget();
		try {
			return Integer.parseInt(target.toString());
		}
		catch (Exception ex) {
			if (target instanceof CharSequence && ((CharSequence)target).length() == 1) {
				return ((CharSequence)target).charAt(0);
			}
			else throw ex;
		}
	}
	
	public boolean isInteger() {
		String value = getTarget().toString();
		try {
			Integer.parseInt(value);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public boolean isReal() {
		String value = getTarget().toString();
		try {
			Double.parseDouble(value);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public float asReal() {
		String value = getTarget().toString();
		return Float.parseFloat(value);
	}
	
	public double asDouble() {
		String value = getTarget().toString();
		return Double.parseDouble(value);
	}
	
	public float asFloat() {
		String value = getTarget().toString();
		return Float.parseFloat(value);
	}
	
	public long asLong() {
		String value = getTarget().toString();
		return Long.parseLong(value);
	}
	
	public boolean asBoolean() {
		String value = getTarget().toString();
		return Boolean.parseBoolean(value);
	}
	
	public String asUnicode() {
		String value = getTarget().toString();
		return "" + (char) Integer.parseInt(value, 16);
	}
	
	public Date asDate(String format) throws ParseException {
		return new SimpleDateFormat(format).parse(getTarget().toString());
	}
	
	public Object asVar(String name) {
		Object target = getTarget();
		getContext().getFrameStack().put(new Variable(name, getTarget(), EolAnyType.Instance));
		return target;
	}
}
