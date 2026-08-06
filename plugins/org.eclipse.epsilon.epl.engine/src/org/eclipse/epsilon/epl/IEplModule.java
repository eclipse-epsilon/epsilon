/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl;

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.eclipse.epsilon.erl.IErlModule;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public interface IEplModule extends IErlModule {

	List<Pattern> getDeclaredPatterns();
	
	List<Pattern> getPatterns();
	
	int getMaxLoops();
	
	void setMaxLoops(int maxLoops);
	
	boolean isRepeatWhileMatches();
	
	void setRepeatWhileMatches(boolean repeatWhileMatches);
	
	PatternMatchModel matchPatterns() throws EolRuntimeException;

	Collection<PatternMatch> match(Pattern pattern) throws EolRuntimeException;
	
	default int getMaximumLevel() {
		return !getPatterns().isEmpty() ? getPatterns()
			.stream()
			.mapToInt(Pattern::getLevel)
			.max().getAsInt() : 0;
	}
}
