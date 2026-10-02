/********************************************************************************
 * Copyright (c) 2026 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 * <p>
 * Corrects ill-formed {@code xsd:dateTime}, {@code xsd:dateTimeStamp} and
 * {@code xsd:date} literals into valid ones, by adding or removing components
 * as needed. For instance, {@code "2026-09-10"^^xsd:dateTime} is corrected to
 * {@code "2026-09-10T00:00:00"^^xsd:dateTime}.
 * </p>
 *
 * <p>
 * Missing time components default to zero, missing timezones are only added
 * for {@code xsd:dateTimeStamp} (as UTC), and month/day/hour/minute/second
 * values are zero-padded. Literals which cannot be corrected are left as is.
 * </p>
 */
public class IllFormedDateCorrector {

	private static final Pattern LENIENT_DATE_TIME = Pattern.compile(
		"(-?\\d{4,})-(\\d{1,2})-(\\d{1,2})"
		+ "(?:[Tt ](\\d{1,2}):(\\d{1,2})(?::(\\d{1,2})(\\.\\d+)?)?)?"
		+ "\\s*([Zz]|[+-]\\d{2}:?\\d{2})?");

	private IllFormedDateCorrector() {}

	/**
	 * Replaces all the ill-formed date/time literals in the model with corrected
	 * versions, where possible.
	 *
	 * @return Number of corrected literals.
	 */
	public static int correct(Model model) {
		List<Statement> toRemove = new ArrayList<>();
		List<Statement> toAdd = new ArrayList<>();

		for (StmtIterator it = model.listStatements(); it.hasNext(); ) {
			Statement stmt = it.next();
			RDFNode object = stmt.getObject();
			if (object.isLiteral()) {
				Literal corrected = correct(object.asLiteral());
				if (corrected != null) {
					toRemove.add(stmt);
					toAdd.add(model.createStatement(stmt.getSubject(), stmt.getPredicate(), corrected));
				}
			}
		}

		model.remove(toRemove);
		model.add(toAdd);
		return toAdd.size();
	}

	/**
	 * Returns a corrected version of the literal if it is an ill-formed date/time
	 * literal that can be corrected, or {@code null} otherwise.
	 */
	public static Literal correct(Literal l) {
		RDFDatatype dt = l.getDatatype();
		if (!isSupportedDatatype(dt) || dt.isValid(l.getLexicalForm())) {
			return null;
		}

		String corrected = correctLexicalForm(l.getLexicalForm(), dt);
		if (corrected == null) {
			return null;
		}
		return l.getModel() != null
			? l.getModel().createTypedLiteral(corrected, dt)
			: ResourceFactory.createTypedLiteral(corrected, dt);
	}

	/**
	 * Returns a valid lexical form for the given datatype from a possibly
	 * ill-formed one, or {@code null} if it cannot be corrected.
	 */
	public static String correctLexicalForm(String lexicalForm, RDFDatatype dt) {
		if (!isSupportedDatatype(dt)) {
			return null;
		}

		Matcher m = LENIENT_DATE_TIME.matcher(lexicalForm.trim());
		if (!m.matches()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(m.group(1));
		sb.append('-').append(pad(m.group(2)));
		sb.append('-').append(pad(m.group(3)));

		if (!XSDDatatype.XSDdate.equals(dt)) {
			sb.append('T').append(pad(m.group(4)));
			sb.append(':').append(pad(m.group(5)));
			sb.append(':').append(pad(m.group(6)));
			if (m.group(7) != null) {
				sb.append(m.group(7));
			}
		}

		String tz = normaliseTimezone(m.group(8));
		if (tz == null && XSDDatatype.XSDdateTimeStamp.equals(dt)) {
			tz = "Z";
		}
		if (tz != null) {
			sb.append(tz);
		}

		String result = sb.toString();
		return dt.isValid(result) ? result : null;
	}

	private static boolean isSupportedDatatype(RDFDatatype dt) {
		return XSDDatatype.XSDdateTime.equals(dt)
			|| XSDDatatype.XSDdateTimeStamp.equals(dt)
			|| XSDDatatype.XSDdate.equals(dt);
	}

	private static String pad(String component) {
		if (component == null) {
			return "00";
		}
		return component.length() < 2 ? "0" + component : component;
	}

	private static String normaliseTimezone(String tz) {
		if (tz == null) {
			return null;
		} else if ("z".equalsIgnoreCase(tz)) {
			return "Z";
		} else if (tz.indexOf(':') == -1) {
			// e.g. +0100 -> +01:00
			return tz.substring(0, 3) + ":" + tz.substring(3);
		}
		return tz;
	}

}
