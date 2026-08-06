/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.translate;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.hutn.exceptions.HutnConfigFileNotFoundException;
import org.eclipse.epsilon.hutn.exceptions.HutnException;
import org.eclipse.epsilon.hutn.exceptions.HutnMetaModelRegistrationException;
import org.eclipse.epsilon.hutn.exceptions.HutnTranslationException;
import org.eclipse.epsilon.hutn.exceptions.HutnValidationException;
import org.eclipse.epsilon.hutn.model.config.HutnConfigMetamodel;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.HutnConfigPackage;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage;
import org.eclipse.epsilon.hutn.util.EmcUtil;
import org.eclipse.epsilon.hutn.util.EpsilonUtil;
import org.eclipse.epsilon.hutn.util.StringUtil;
import org.eclipse.epsilon.hutn.validation.config.HutnConfigFileValidator;

public class HutnTranslator {
	
	private final File configFileDirectory;
	
	public HutnTranslator() {
		this(null);
	}
	
	public HutnTranslator(File configFileDirectory) {
		this.configFileDirectory = configFileDirectory;
	}
	
	private static void registerMetaModels() throws HutnMetaModelRegistrationException {
		EmcUtil.register(HutnPackage.eNS_URI,         HutnPackage.eINSTANCE);
		EmcUtil.register(HutnConfigPackage.eNS_URI,   HutnConfigPackage.eINSTANCE);
		EmcUtil.register(HutnAntlrAstPackage.eNS_URI, HutnAntlrAstPackage.eINSTANCE);
	}
	
	private static Node findNodeWithText(List<Node> nodes, String text) {
		for (Node node : nodes)
			if (text.equals(node.getText()))
				return node;
		
		return null;
	}
	
	private Detail determineDetail(Node metaModelNode, String parent) {
		final Node node = findNodeWithText(metaModelNode.getChildren(), parent);
		
		if (node != null && !node.getChildren().isEmpty()) {
			return new Detail(node.getLine(), node.getColumn(), StringUtil.stripQuotes(node.getChildren().get(0).getText()));
		}
		
		return null;
	}
	
	private MetaModelDetail determineMetaModelDetails(Ast ast) {
		Detail nsUriDetail      = null;
		Detail configFileDetail = null;
		
		final Node specNode = findNodeWithText(ast.getRoots(), "@Spec");
		
		if (specNode != null && !specNode.getChildren().isEmpty()) {
			final Node metaModelNode  = specNode.getChildren().get(0);
			
			nsUriDetail      = determineDetail(metaModelNode, "nsUri");
			configFileDetail = determineDetail(metaModelNode, "configFile");
		}
		
		return new MetaModelDetail(nsUriDetail, configFileDetail);
	}
	
	private IModel initialiseConfigModel(MetaModelDetail details) throws HutnConfigFileNotFoundException {
		final EmfModel configModel = new EmfModel();
		
		if (details.getConfigFileDetail() == null || details.getConfigFileDetail().getText() == null) {
			try {
				configModel.setModelFileUri(HutnConfigMetamodel.getDefaultConfigModelUri());
			}
			catch (IOException ex) {
				throw new HutnConfigFileNotFoundException(ex);
			}
		}
		else {
			final String configFilePath = details.getConfigFileDetail().getText();
			
			File configFile = new File(configFilePath);
			
			if (!configFile.isAbsolute() && configFileDirectory != null)
				configFile = new File(configFileDirectory, configFilePath);
			
			if (configFile.exists()) {
				configModel.setModelFile(configFile.getAbsolutePath());
			} else {
				throw new HutnConfigFileNotFoundException(configFile.getAbsolutePath(),
				                                          details.getConfigFileDetail().getLine(),
				                                          details.getConfigFileDetail().getColumn());
			}
		}
	
		configModel.setName("Config");
		configModel.setMetamodelUri(HutnConfigPackage.eNS_URI);
		
		return configModel;
	}
	
	public List<ParseProblem> validateConfigModel(Ast ast) throws HutnException {		
		final List<ParseProblem> problems = new LinkedList<>();
		
		final MetaModelDetail details = determineMetaModelDetails(ast);
		
		if (details.getConfigFileDetail() != null) {
		
			if (details.getNsUriDetail() == null) {
				final ParseProblem problem = new ParseProblem(details.getConfigFileDetail().getLine(),
				                                              details.getConfigFileDetail().getColumn(),
				                                              "No nsUri defined for the metamodel with configFile: " +
				                                              details.getConfigFileDetail().getText());
				
				problems.add(problem);
				
			}
			else {
				final HutnConfigFileValidator validator = new HutnConfigFileValidator(details.getConfigFileDetail().getLine(),
				                                                                      details.getConfigFileDetail().getColumn());
				
				try {
					registerMetaModels();
				} catch (HutnMetaModelRegistrationException e) {
					throw new HutnValidationException(e);
				}
				
				return validator.getProblemsForConfigurationModel(initialiseConfigModel(details),
				                                                  details.getNsUriDetail().getText());
			}
		}
		
		return problems;
	}
	
	public Spec createIntermediateModel(Ast ast, File sourceFile) throws HutnTranslationException {
		final IModel configModel = initialiseConfigModel(determineMetaModelDetails(ast));
		
		try {
			final IModel astModel = new InMemoryEmfModel("AntlrAst", ast.eResource(), HutnAntlrAstPackage.eINSTANCE);
			
			final Spec spec = HutnFactory.eINSTANCE.createSpec();
			spec.setSourceFile(sourceFile == null ? null : sourceFile.getAbsolutePath());
			
			final EmfModel intermediateModel = new InMemoryEmfModel("Intermediate", EmfUtil.createResource(spec), HutnPackage.eINSTANCE);
			intermediateModel.setMetamodelUri(HutnPackage.eNS_URI);
			
			registerMetaModels();
			
			final IEtlModule transformer = EpsilonUtil.initialiseEtlModule(astModel, intermediateModel, configModel);
			
			transformer.parse(FileUtil.getFileStandalone("AntlrAst2Intermediate.etl", HutnTranslator.class));
			transformer.execute();
			
			return spec;
 
		} catch (Exception e) {
			if (e instanceof HutnTranslationException)
				throw (HutnTranslationException)e;
			else
				throw new HutnTranslationException(e);
		
		} finally {
			configModel.dispose();
		}
	}
}
