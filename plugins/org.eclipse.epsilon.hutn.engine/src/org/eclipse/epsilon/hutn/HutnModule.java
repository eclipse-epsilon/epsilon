/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.hutn.exceptions.HutnConfigFileNotFoundException;
import org.eclipse.epsilon.hutn.exceptions.HutnException;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;
import org.eclipse.epsilon.hutn.exceptions.HutnUnrecognisedNsUriException;
import org.eclipse.epsilon.hutn.generate.metamodel.MetaModelGenerator;
import org.eclipse.epsilon.hutn.generate.model.ModelGenerator;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.parse.HutnLexer;
import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.hutn.parse.postprocessor.HutnPostProcessor;
import org.eclipse.epsilon.hutn.translate.HutnTranslator;
import org.eclipse.epsilon.hutn.validation.model.HutnValidator;

public class HutnModule extends EolModule implements IHutnModule {

	protected HutnDocument document;
	protected Spec spec;
	protected boolean metaModelIsValid = true;
	protected File configFileDirectory;
	protected boolean hutnIsValid = false;
	
	public HutnModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	public HutnModule(IHutnContext context) {
		super(context != null ? context : new HutnContext());
		getContext().setModule(this);
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new HutnLexer(inputStream);
	}
	
	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new HutnParser(tokenStream);
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		
		if (parentAst == null) return this;
		
		if (cst.getParent() == null) {
			return new HutnDocument();
		}
		return null;
	}
	
	@Override
	public String getMainRule() {
		return "document";
	}
	
	@Override
	public IHutnContext getContext(){
		return (IHutnContext) super.getContext();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.AbstractModule#invokeMainRule()
	 * 
	 * Overridden to ensure that errors found during building
	 * the HUTN model cause parse() to return false.
	 */
	@Override
	protected boolean invokeMainRule(List<CommonToken> comments) throws Exception {
		boolean buildModelWasCalled = super.invokeMainRule(comments);
		
		if (buildModelWasCalled) {
			return parsingWasSuccessful();
		}
		
		return false; // parsing must have failed before build model was called
	}
	
	protected boolean parsingWasSuccessful() {
		for (ParseProblem p : getParseProblems()) {
			if (p.getSeverity() == ParseProblem.ERROR)
				return false;
		}
		
		return true;
	}

	@Override
	public void build(AST cst, IModule module) {
		document = (HutnDocument) module.createAst(cst, this);
		try {
			translateAst(cst);
		} catch (HutnException e) {
			throw new RuntimeException(e);
		}
	}

	protected void translateAst(AST ast) throws HutnException {
		try {
			final Ast astModel = new HutnPostProcessor(getParseProblems()).process(ast);
			
			final HutnTranslator translator = new HutnTranslator(configFileDirectory);
			getParseProblems().addAll(translator.validateConfigModel(astModel));
			
			hutnIsValid = parsingWasSuccessful();
			if (hutnIsValid) {
				spec = translator.createIntermediateModel(astModel, sourceFile);
				if (spec == null)
					throw new IllegalArgumentException("Could not generate Intermediate model for specified text.");
				
				getParseProblems().addAll(new HutnValidator().getProblemsForIntermediateModel(spec));
			}
		
		} catch (HutnConfigFileNotFoundException e) {
			getParseProblems().add(new ParseProblem(e.getLine(), e.getColumn(), "Configuration file not found: " + e.getPath()));
		
		} catch (HutnUnrecognisedNsUriException e) {
			getParseProblems().add(new ParseProblem(e.getLine(), e.getColumn(), "Unrecognised namespace URI: " + e.getUri()));
			metaModelIsValid = false;
		}
	}

	@Override
	public boolean hasValidHutn() {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		
		return hutnIsValid;
	}
	
	@Override
	public void setConfigFileDirectory(File configFileDirectory) {
		this.configFileDirectory = configFileDirectory;
	}
	
	@Override
	public boolean hasValidMetaModel() {
		return metaModelIsValid;
	}
	
	@Override
	public List<String> getNsUris() {
		final List<String> nsUris = new LinkedList<>();
		
		if (spec != null) {
			for (NsUri uri : spec.getNsUris()) {
				nsUris.add(uri.getValue());
			}
		}
		
		return Collections.unmodifiableList(nsUris);
	}
	
	@Override
	public String getModelFile() {
		if (spec != null) {
			return spec.getModelFile();
		}
		
		return null;
	}
	
	@Override
	public AbstractEmfModel generateEmfModel() throws HutnGenerationException {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		if (hasInferredMetaModel()) {
			throw new IllegalStateException("Cannot generate an in-memory model when the metamodel needs to be inferred.");
		} else {
			return new ModelGenerator(spec).generate(spec.getModelFile());
		}
	}
	
	@Override
	public List<File> storeEmfModel(File baseDirectory, String defaultModelPath, String inferredMetamodelPath) throws HutnGenerationException {
		final List<File> generated = new LinkedList<>();
		
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		
		// Construct new generator based on whether a metamodel needs to be inferred
		final ModelGenerator generator;
		
		if (hasInferredMetaModel()) {
			final File metamodel = new File(baseDirectory, inferredMetamodelPath);
			generateEmfMetaModel(metamodel);
			generator = new ModelGenerator(spec, metamodel);
			
			generated.add(metamodel);
			
		} else {
			generator = new ModelGenerator(spec);
		}
			
		// Generate and store model based on whether a model file has been specified in the @Spec
		final File model;
		
		if (spec.getModelFile() == null) {
			model = new File(baseDirectory, defaultModelPath);
		} else {
			model = new File(baseDirectory, spec.getModelFile());
		}
		
		generator.store(model);
		generated.add(model);
		
		return generated;
	}
	
	private boolean hasInferredMetaModel() {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		return spec.getNsUris().isEmpty();
	}
	
	private void generateEmfMetaModel(File destination) throws HutnGenerationException {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		new MetaModelGenerator(spec).store(destination);
	}
	
	
	@Override
	public Spec getIntermediateModel() {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		
		return spec;
	}
	
	@Override
	public void storeIntermediateModel(File destination) {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		
		final URI fileUri = URI.createFileURI(destination.getAbsolutePath());
		final Resource resource = EmfUtil.createResource(fileUri);
		resource.getContents().add(spec);
		
		try (EmfModel model = new InMemoryEmfModel("Intermediate", resource, HutnPackage.eINSTANCE)) {
			model.store(fileUri);
		}
	}
	
	@Override
	public void storeIntermediateModelTransformation(File destination) throws HutnGenerationException {
		storeIntermediateModelTransformation(destination, false);
	}
	
	@Override
	public void storeIntermediateModelTransformationForAllInputModels(File destination) throws HutnGenerationException {
		storeIntermediateModelTransformation(destination, true);
	}

	private void storeIntermediateModelTransformation(File destination, boolean generateCompleteTransformation) throws HutnGenerationException {
		if (spec == null) throw new IllegalStateException("No HUTN has been parsed.");
		
		try {
			final ModelGenerator modelGenerator = new ModelGenerator(spec);
			if (generateCompleteTransformation) {
				modelGenerator.forceGenerationOfTransformationForWholeMetamodel();
			}
			
			try (FileWriter writer = new FileWriter(destination)) {
				writer.write(modelGenerator.generateTransformation());
			}
		}
		catch (IOException e) {
			throw new HutnGenerationException(e);
		}
	}
}
