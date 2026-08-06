/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.validation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.hutn.exceptions.HutnValidationException;
import org.eclipse.epsilon.hutn.util.EpsilonUtil;

public abstract class AbstractValidator {

	private final AbstractFixer fixer;
	private final File evlSource;
	
	/**
	 * 
	 * @param fixer
	 * @param filename
	 * @param relativeTo
	 * @throws HutnException 
	 * @since 1.6
	 */
	protected AbstractValidator(AbstractFixer fixer, String filename) throws HutnValidationException {
		this.fixer = fixer;
		try {
			this.evlSource = FileUtil.getFileStandalone(filename, getClass());
		}
		catch (IOException ex) {
			throw new HutnValidationException(ex);
		}
	}
	
	protected List<ParseProblem> validate(IModel model, List<IModel> extraModels) throws HutnValidationException {
		return validate(model, extraModels.toArray(new IModel[extraModels.size()]));
	}
	
	protected List<ParseProblem> validate(IModel model, IModel... extraModels) throws HutnValidationException {
		List<ParseProblem> problems;
		
		do {
			fixer.reset();
			problems = doValidate(model, extraModels);
		}
		while (fixer.hasChangedModel());
		
		return problems;
	}
	
	private List<ParseProblem> doValidate(IModel model, IModel... extraModels) throws HutnValidationException {
		try {		
			final IEvlModule validator = EpsilonUtil.initialseEvlModule(fixer, model, extraModels);
			validator.parse(evlSource);
			validator.execute();
			return collectParseProblems(validator);
		}
		catch (Exception e) {
			throw new HutnValidationException(e);
		}
	}

	private List<ParseProblem> collectParseProblems(final IEvlModule validator) {
		final ArrayList<ParseProblem> problems = new ArrayList<>(1);
		if (validator != null) {
			Collection<UnsatisfiedConstraint> unsatisfieds = validator.getContext().getUnsatisfiedConstraints();
			problems.ensureCapacity(unsatisfieds.size());
			for (UnsatisfiedConstraint uc : unsatisfieds) {
				problems.add(fixer.interpretUnsatisfiedConstraint(uc));
			}
		}
		return problems;
	}

}
