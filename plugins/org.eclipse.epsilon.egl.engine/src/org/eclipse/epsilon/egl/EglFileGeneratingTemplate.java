/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.formatter.NullFormatter;
import org.eclipse.epsilon.egl.incremental.IncrementalitySettings;
import org.eclipse.epsilon.egl.merge.output.LocatedRegion;
import org.eclipse.epsilon.egl.patch.Line;
import org.eclipse.epsilon.egl.patch.Patch;
import org.eclipse.epsilon.egl.patch.PatchValidationDiagnostic;
import org.eclipse.epsilon.egl.patch.TextBlock;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecificationFactory;
import org.eclipse.epsilon.egl.status.ProtectedRegionWarning;
import org.eclipse.epsilon.egl.traceability.OutputFile;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.eclipse.epsilon.eol.ImportManager;

public class EglFileGeneratingTemplate extends EglPersistentTemplate {

	private File target;
	private String targetName;
	private OutputFile currentOutputFile;
	private String existingContents;
	private String newContents;
	private String positiveMessage;
	private OutputMode outputMode;

	public static enum OutputMode {
		WRITE, MERGE, APPEND, PATCH;
	}
	
	// For tests
	protected EglFileGeneratingTemplate(URI path, IEglContext context, URI outputRoot) throws Exception {
		this(new EglTemplateSpecificationFactory(new NullFormatter(), new IncrementalitySettings(), new ImportManager()).fromResource(path.toString(), path), context, outputRoot);
	}

	public EglFileGeneratingTemplate(EglTemplateSpecification spec, IEglContext context, URI outputRoot, String outputRootPath) throws Exception {
		super(spec, context, outputRoot, outputRootPath);
	}
	
	/**
	 * 
	 * @param spec
	 * @param context
	 * @param outputRoot
	 * @throws Exception
	 * @since 1.6
	 */
	public EglFileGeneratingTemplate(EglTemplateSpecification spec, IEglContext context, URI outputRoot) throws Exception {
		super(spec, context, outputRoot);
	}
	
	public File append(String path) throws EglRuntimeException {
		return write(path, OutputMode.APPEND);
	}
	
	public File patch(String path) throws EglRuntimeException {
		return write(path, OutputMode.PATCH);
	}
	
	protected File write(String path, OutputMode outputMode) throws EglRuntimeException {
		try {
			final File target = resolveFile(path);

			if (!isProcessed()) {
				process();
			}
			
			this.target = target;
			this.targetName = name(path);			
			this.existingContents = FileUtil.readIfExists(target);
			this.outputMode = outputMode;
			
			prepareNewContents();
			writeNewContentsIfDifferentFromExistingContents();

			return target;
		} catch (URISyntaxException e) {
			throw new EglRuntimeException("Could not resolve path: " + target, e, module);
		} catch (IOException ex) {
			throw new EglRuntimeException("Could not generate to: " + target, ex, module);
		}
	}

	@Override
	protected void doGenerate(File target, String targetName, boolean overwrite, boolean merge) throws EglRuntimeException {
		try {
			this.target = target;
			this.targetName = targetName;			
			this.existingContents = FileUtil.readIfExists(target);
			this.outputMode = (merge && target.exists()) ? OutputMode.MERGE : OutputMode.WRITE;
			
			prepareNewContents();
			writeNewContentsIfDifferentFromExistingContents();
			
		} catch (URISyntaxException e) {
			throw new EglRuntimeException("Could not resolve path: " + target, e, module);
		} catch (IOException ex) {
			throw new EglRuntimeException("Could not generate to: " + target, ex, module);
		}
	}
	
	protected void prepareNewContents() throws EglRuntimeException {
		switch (outputMode) {
			case APPEND: {
				newContents = getExistingContents() != null ?
					getExistingContents() + FileUtil.NEWLINE + getContents() :
					getContents();
				positiveMessage = "Successfully appended to ";
				break;
			}
			case MERGE: {
				newContents = merge(getExistingContents());
				positiveMessage = "Protected regions preserved in ";
				break;
			}
			case WRITE: {
				newContents = getContents();
				positiveMessage = "Successfully wrote to ";
				break;
			}
			case PATCH: {
				positiveMessage = "Successfully patched ";
				
				TextBlock existingContentsBlock = new TextBlock(getExistingContents().split(System.lineSeparator()));
				Patch patch = new Patch(getContents().split(System.lineSeparator()));
				List<PatchValidationDiagnostic> patchValidationDiagnostics = patch.validate();
				if (!patchValidationDiagnostics.isEmpty()) {
					PatchValidationDiagnostic patchValidationDiagnostic = patchValidationDiagnostics.get(0);
					throw new EglRuntimeException("Invalid patch. Line " + 
							patchValidationDiagnostic.getLine().getNumber() + ": " + 
							patchValidationDiagnostic.getReason(), new IllegalStateException());
				}
				
				TextBlock newContentsBlock = patch.apply(existingContentsBlock);
				
				newContents = newContentsBlock.getLines().stream()
					.map(Line::getText)
					.collect(Collectors.joining(System.lineSeparator()));
				
				break;
			}
			default:
				throw new EglRuntimeException("Unsupported output mode " + outputMode, new IllegalStateException());
		}
	}
	
	protected void writeNewContentsIfDifferentFromExistingContents() throws URISyntaxException, IOException {
		if (isOverwriteUnchangedFiles() || !newContents.equals(existingContents)) {
			write();
			addMessage(getPositiveMessage() + getTargetName());
		} else {
			addMessage("Content unchanged for " + getTargetName());			
		}
	}
	
	protected boolean isOverwriteUnchangedFiles() {
		return getIncrementalitySettings().isOverwriteUnchangedFiles();
	}

	protected void write() throws IOException, URISyntaxException {
		if (getTarget() != null) {
			FileUtil.write(getTarget(), getNewContents());
		}
		
		currentOutputFile = getTemplate().addOutputFile(getTargetName(), UriUtil.fileToUri(getTarget()));
		
		if (getOutputMode() == OutputMode.MERGE) {
			for (LocatedRegion pr : module.getContext().getPartitioner().partition(getNewContents()).getLocatedRegions()) {
				getCurrentOutputFile().addProtectedRegion(pr.getId(), pr.isEnabled(), pr.getOffset());
			}
		}
	}

	@Override
	protected void addProtectedRegionWarning(ProtectedRegionWarning warning) {
		super.addProtectedRegionWarning(new ProtectedRegionWarning(warning.getId(), target.getAbsolutePath()));
	}

	public File getTarget() {
		return target;
	}

	public void setTarget(File target) {
		this.target = target;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public OutputFile getCurrentOutputFile() {
		return currentOutputFile;
	}

	public void setCurrentOutputFile(OutputFile currentOutputFile) {
		this.currentOutputFile = currentOutputFile;
	}

	public String getExistingContents() {
		return existingContents;
	}

	public void setExistingContents(String existingContents) {
		this.existingContents = existingContents;
	}

	public String getNewContents() {
		return newContents;
	}

	public void setNewContents(String newContents) {
		this.newContents = newContents;
	}

	public String getPositiveMessage() {
		return positiveMessage;
	}

	public void setPositiveMessage(String positiveMessage) {
		this.positiveMessage = positiveMessage;
	}

	public OutputMode getOutputMode() {
		return outputMode;
	}

	public void setOutputMode(OutputMode outputMode) {
		this.outputMode = outputMode;
	}
}
