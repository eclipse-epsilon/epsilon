/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.console;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.dt.util.ThemeChangeListener;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.part.FileEditorInput;

public class EpsilonConsole {
	
	private IOConsole ioConsole = null;
	private static EpsilonConsole instance = null;
	private MirrorPrintStream infoPrintStream;
	private MirrorPrintStream debugPrintStream;
	private MirrorPrintStream errorPrintStream;
	private MirrorPrintStream warningPrintStream;
	private InputStream inputStream;
	
	private IOConsoleOutputStream debugOutputStream = null;
	private IOConsoleOutputStream errorOutputStream = null;
	private IOConsoleOutputStream warningOutputStream = null;
	private IOConsoleOutputStream infoOutputStream = null;
	
	private EpsilonConsole() {
		
		ioConsole = new IOConsole("Epsilon", null);
		
		// Necessary because colors are acquired through a non-UI thread
		// and that used to cause an SWT illegal thread exception
		
		debugOutputStream = createConsoleOutputStream();
		infoOutputStream = createConsoleOutputStream();
		warningOutputStream = createConsoleOutputStream();
		errorOutputStream = createConsoleOutputStream();
		
		initPrintStreams();
		inputStream = ioConsole.getInputStream();				
		
		PlatformUI.getWorkbench().getThemeManager().addPropertyChangeListener(new ThemeChangeListener() {
			
			@Override
			public void themeChange() {
				initialiseColours();
			}
		});
		initialiseColours();
		
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ioConsole});
		ioConsole.addPatternMatchListener(new EolRuntimeExceptionHyperlinkListener(ioConsole));
	}
	
	/**
	 * Create the print streams by using {@link MirrorPrintStream}
	 * @see MirrorPrintStream
	 * @since 1.6
	 */
	private void initPrintStreams() {
		debugPrintStream = new MirrorPrintStream(debugOutputStream);
		errorPrintStream = new MirrorPrintStream(errorOutputStream);
		warningPrintStream = new MirrorPrintStream(warningOutputStream);
		infoPrintStream = new MirrorPrintStream(infoOutputStream);
	}
	
	public static EpsilonConsole getInstance() {
		if (instance == null) {
			instance = new EpsilonConsole();
		}
		return instance;
	}
	
	public void initialiseColours() {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(() -> {
			if (EclipseUtil.isDarkThemeEnabled()) {
				infoOutputStream.setColor(new Color(display, 190, 218, 0));
				errorOutputStream.setColor(new Color(display, 243, 0, 70));
				debugOutputStream.setColor(new Color(display, 235, 235, 235));
				warningOutputStream.setColor(new Color(display, 131, 176, 207));
				ioConsole.getInputStream().setColor(new Color(display, 118, 167, 37));				
			}
			else {
				infoOutputStream.setColor(display.getSystemColor(SWT.COLOR_BLUE));
				errorOutputStream.setColor(display.getSystemColor(SWT.COLOR_RED));
				debugOutputStream.setColor(display.getSystemColor(SWT.COLOR_BLACK));
				warningOutputStream.setColor(display.getSystemColor(SWT.COLOR_DARK_YELLOW));
				ioConsole.getInputStream().setColor(display.getSystemColor(SWT.COLOR_GREEN));
			}
		});
	}
	
	public IOConsoleOutputStream createConsoleOutputStream() {
		IOConsoleOutputStream mcs = ioConsole.newOutputStream();
		mcs.setActivateOnWrite(true);
		return mcs;
	}
	
	
	public void clear() {
		//This doesn't clear the console immediately
		//but schedules a job instead
		ioConsole.clearConsole();
		
		// Wait until the console is clear and then return
		// Update: Don't do this as it can it can lead to an infinite loop
		// while (ioConsole.getDocument().getLength() > 0){System.out.print("");};
	}
	
	public PrintStream getDebugStream() {
		return debugPrintStream;
	}
	
	public PrintStream getErrorStream() {
		return errorPrintStream;
	}
	
	public PrintStream getInfoStream() {
		return infoPrintStream;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void reportParseAnomaly (ParseProblem anomaly) {
		
	}

	public PrintStream getWarningStream() {
		return warningPrintStream;
	}
	
	/**
	 * Enable mirroring in all output streams of the console
	 * @param outputFile			The file to which the output will be mirrored.
	 * @see #disableMirroring()
	 * @since 1.6
	 */
	public void enableMirroring(String outputFile, boolean append) {
		
		ConsoleLogFileHyperlink hyperlink = new ConsoleLogFileHyperlink(outputFile);
		String outputMessage = String.format("[Console output redirected to file:%s]\n", outputFile);
		int offset = outputMessage.indexOf(":")+1;
		int length = outputFile.length();
		try {
			infoOutputStream.write(outputMessage);
		} catch (IOException e1) {
			System.err.println("Unable to print redirection info message. " + e1.getMessage());
		}
		ioConsole.addPatternMatchListener(new IPatternMatchListener() {
			
			@Override
			public void matchFound(PatternMatchEvent event) {
				try {
					ioConsole.addHyperlink(hyperlink, offset, length);
				} catch (BadLocationException e) {
					// No harm done
				}
			}
			
			@Override
			public void disconnect() {
			}
			
			@Override
			public void connect(TextConsole console) {
			}
			
			@Override
			public String getPattern() {
				return "\\[Console output redirected to file:[^\\]]*\\]";
			}
			
			@Override
			public String getLineQualifier() {
				return "\\[.*";
			}
			
			@Override
			public int getCompilerFlags() {
				return 0;
			}
		});
		infoPrintStream.mirrorToFile(outputFile, append);
		errorPrintStream.mirrorToFile(outputFile, append);
		debugPrintStream.mirrorToFile(outputFile, append);
		warningPrintStream.mirrorToFile(outputFile, append);
	}
	
	/**
	 * Disable mirroring in all outputstreams
	 * @see #enableMirroring(String, boolean)
	 * @since 1.6
	 */
	public void disableMirroring() {
		infoPrintStream.disableMirror();
		errorPrintStream.disableMirror();
		debugPrintStream.disableMirror();
		warningPrintStream.disableMirror();
	}
	
	private class ConsoleLogFileHyperlink implements IHyperlink {
        String fFilePath;
        ConsoleLogFileHyperlink(String filePath) {
            fFilePath = filePath;
        }

        @Override
		public void linkActivated() {
            IEditorInput input;
            Path path = new Path(fFilePath);
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            IFile ifile = root.getFileForLocation(path);
            if (ifile == null) { // The file is not in the workspace
                File file = new File(fFilePath);
                LocalFileStorage lfs = new LocalFileStorage(file);
                input = new StorageEditorInput(lfs, file);

            } else {
                input = new FileEditorInput(ifile);
            }

            IWorkbenchPage activePage = DebugUIPlugin.getActiveWorkbenchWindow().getActivePage();
            try {
                activePage.openEditor(input, EditorsUI.DEFAULT_TEXT_EDITOR_ID, true);
            } catch (PartInitException e) {
            }
        }
        @Override
		public void linkEntered() {
        }
        @Override
		public void linkExited() {
        }
    }
	
	private class StorageEditorInput extends PlatformObject implements IStorageEditorInput {
        private File fFile;
        private IStorage fStorage;

        public StorageEditorInput(IStorage storage, File file) {
            fStorage = storage;
            fFile = file;
        }

        @Override
		public IStorage getStorage() {
            return fStorage;
        }

        @Override
		public ImageDescriptor getImageDescriptor() {
            return null;
        }

        @Override
		public String getName() {
            return getStorage().getName();
        }

        @Override
		public IPersistableElement getPersistable() {
            return null;
        }

        @Override
		public String getToolTipText() {
            return getStorage().getFullPath().toOSString();
        }

        @Override
		public boolean equals(Object object) {
            return object instanceof StorageEditorInput &&
             getStorage().equals(((StorageEditorInput)object).getStorage());
        }

        @Override
		public int hashCode() {
            return getStorage().hashCode();
        }

        @Override
		public boolean exists() {
            return fFile.exists();
        }
    }
	
}
