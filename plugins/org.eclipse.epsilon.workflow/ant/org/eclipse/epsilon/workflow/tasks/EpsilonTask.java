/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.eol.execute.context.ExtendedProperties;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.models.ModelRepositoryManager;
import org.eclipse.epsilon.workflow.tasks.debug.DebugServerSession;
import org.eclipse.epsilon.workflow.tasks.transactions.NamedTransactionSupport;

public abstract class EpsilonTask extends Task {

	static final String EPSILON_REPOSITORY = "epsilon.modelRepository";
	static final String EPSILON_EXTENDEDPROPERTIES = "epsilon.extendedProperties";
	static final String EPSILON_FRAME = "epsilon.frame";
	static final String EPSILON_ACTIVETRANSACTIONS = "epsilon.activeTransactions";

	static final String EPSILON_DEBUG_SESSION = "epsilon.debug.session";

	protected boolean profile;
	protected boolean failOnErrors = true;
	protected boolean failOnWarnings = false;


	@Override
	public final void execute() throws BuildException {
		try {
			executeImpl();
		}
		catch (BuildException ex) {
			throw ex;
		}
	}
	
	public abstract void executeImpl() throws BuildException;
	
	protected void fail(String message, Exception exception) throws BuildException {
		if (failOnErrors) {
			throw new BuildException(message, exception);
		}
		else {
			log(message, Project.MSG_ERR);
		}
	}
	
	protected void warn(String message) throws BuildException {
		if (failOnWarnings) {
			// getProjectRepository().dispose();
			throw new BuildException(message);
		}
		else {
			log(message, Project.MSG_WARN);
		}		
	}
	
	protected ExtendedProperties getExtendedProperties() {
		
		ExtendedProperties extendedProperties = 
			(ExtendedProperties) getProject().getReference(EpsilonTask.EPSILON_EXTENDEDPROPERTIES);
		
		if (extendedProperties == null) {
			extendedProperties = new ExtendedProperties();
			getProject().addReference(EpsilonTask.EPSILON_EXTENDEDPROPERTIES, extendedProperties);
		}
		
		return extendedProperties;
	}

	
	protected ModelRepository getProjectRepository() {
		
		ModelRepository repository = 
			(ModelRepository) getProject().getReference(EpsilonTask.EPSILON_REPOSITORY);
		
		if (repository == null) {
			
			if (usesSharedModelRepository(getProject())) {
				repository = getModelRepository(getProject());
			}
			else {
				repository = new ModelRepository();
				ShutdownProjectRepositoryListener.activate(getProject(), repository);
			}
			setProjectRepository(repository);
		}
		
		return repository;
	
	}

	protected void setProjectRepository(ModelRepository repository) {
		getProject().addReference(EpsilonTask.EPSILON_REPOSITORY, repository);
	}

	protected DebugServerSession getDebugSession() {
		return (DebugServerSession) getProject().getReference(EpsilonTask.EPSILON_DEBUG_SESSION);
	}

	protected void setDebugSession(DebugServerSession session) {
		getProject().addReference(EpsilonTask.EPSILON_DEBUG_SESSION, session);
	}

	protected List<NamedTransactionSupport> getActiveTransactions() {
		
		@SuppressWarnings("unchecked")
		List<NamedTransactionSupport> activeTransactions = 
			(List<NamedTransactionSupport>) getProject().getReference(EpsilonTask.EPSILON_ACTIVETRANSACTIONS);
		
		if (activeTransactions == null) {
			activeTransactions = new ArrayList<>();
			getProject().addReference(EpsilonTask.EPSILON_ACTIVETRANSACTIONS, activeTransactions);
		}
		
		return activeTransactions;
	
	}
	
	protected Frame getProjectStackFrame() {
		
		Frame frame = (Frame) getProject().getReference(EpsilonTask.EPSILON_FRAME);
		
		if (frame == null) {
			frame = new SingleFrame(FrameType.PROTECTED, null);
			getProject().addReference(EpsilonTask.EPSILON_FRAME, frame);
		}
		
		return frame;
	}
	
	protected File getBaseDir() {
		return new File(getProject().getBaseDir(),"\\");
	}

	public boolean isProfile() {
		return profile;
	}

	public void setProfile(boolean profile) {
		this.profile = profile;
	}
	
	public boolean isFailOnErrors() {
		return failOnErrors;
	}

	public void setFailOnErrors(boolean failOnErrors) {
		this.failOnErrors = failOnErrors;
	}

	public boolean isFailOnWarnings() {
		return failOnWarnings;
	}

	public void setFailOnWarnings(boolean failOnWarnings) {
		this.failOnWarnings = failOnWarnings;
	}
	
	public boolean usesSharedModelRepository(Project project) {
		return project.getUserProperty(EpsilonTask.EPSILON_REPOSITORY) != null;
	}
	
	public ModelRepository getModelRepository(Project project) {
		String repositoryName = project.getUserProperty(EpsilonTask.EPSILON_REPOSITORY);
		HashMap<String, ModelRepository> repositories = ModelRepositoryManager.getInstance().getRepositories();
		if (repositoryName == null) return null;
		ModelRepository repository = repositories.get(repositoryName);
		if (repository == null) {
			repository = new ModelRepository();
			repositories.put(repositoryName, repository);
		}
		return repository;
	}
}
