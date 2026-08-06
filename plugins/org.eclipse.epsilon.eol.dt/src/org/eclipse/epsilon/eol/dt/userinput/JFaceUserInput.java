/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.userinput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolUserException;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.userinput.AbstractUserInput;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class JFaceUserInput extends AbstractUserInput {
	
	protected PrettyPrinterManager prettyPrinterManager = null;

	public Object choose(final String question, final Collection<?> choices, final Object default_) {
		
		ReturningRunnable runnable = new ReturningRunnable() { 

			@Override
			public Object runImpl() {
				
				List<?> values;
				
				if (choices instanceof List) {
					values = (List<?>) choices;
				}
				else {
					values = new ArrayList<Object>(choices);
				}
				
				
				ElementListSelectionDialog dialog = new ElementListSelectionDialog( 
						Display.getDefault().getActiveShell(), 
						new LabelProvider() {

							@Override
							public String getText(Object element) {
								return prettyPrinterManager.toString(element);
							}
							
						});
				
				dialog.setMessage(question);
				dialog.setTitle(question);
				dialog.setElements(values.toArray());
				dialog.setInitialElementSelections(Arrays.asList(default_));

				if (dialog.open() == Window.OK) {
					if (dialog.getResult().length > 0) return dialog.getResult()[0];
					else return null;
				}
				else {
					return default_;
				}
				
			}
			
		};
		
		return exec(runnable);
	}
	
	public Object chooseMany(final String question, final Collection<?> choices,
			final Collection<?> default_) {
		ReturningRunnable runnable = new ReturningRunnable() { 

			@Override
			public Object runImpl() {
				
				List<?> values;
				
				if (choices instanceof List) {
					values = (List<?>) choices;
				}
				else {
					values = new ArrayList<Object>(choices);
				}
				
				ElementListSelectionDialog dialog = new ElementListSelectionDialog( 
						Display.getDefault().getActiveShell(), 
						new LabelProvider() {

							@Override
							public String getText(Object element) {
								return prettyPrinterManager.toString(element);
							}
							
						});
				
				dialog.setMessage(question);
				dialog.setTitle(question);
				dialog.setMultipleSelection(true);
				dialog.setElements(values.toArray());
				dialog.setInitialElementSelections(new ArrayList<Object>(default_));

				if (dialog.open() == Window.OK) {
					return Arrays.asList(dialog.getResult());
				}
				else {
					return default_;
				}
			}
			
		};
		
		return exec(runnable);
	}
	
	
	
	public JFaceUserInput(PrettyPrinterManager prettyPrinterManager) {
		this.prettyPrinterManager = prettyPrinterManager;
	}
	
	protected PrettyPrinterManager getPrettyPrinterManager() {
		return this.prettyPrinterManager;
	}
	

	
	public boolean confirm(final String question, final boolean default_) throws EolUserException {
		
		ReturningRunnable runnable = new ReturningRunnable() {
			@Override
			public Object runImpl() {
				return MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Confirm", question);
			}
		};
		
		return (Boolean) exec(runnable);
		
	}

	public String prompt(final String question, final String default_) {
		
		ReturningRunnable runnable = new ReturningRunnable() {

			@Override
			public Object runImpl() {
				InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(),"String value prompt",question,default_,null);
				if (dialog.open() == Window.OK) {
					return dialog.getValue();
				}
				else {
					return default_;
				}
			}
			
		};
		
		return (String) exec(runnable);
		

	}

	public int promptInteger(final String question, final int default_) {
		
		ReturningRunnable runnable = new ReturningRunnable() {

			@Override
			public Object runImpl() {
				InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(),"Integer value prompt",question,default_ + "",new IntegerValidator());
				if (dialog.open() == Window.OK) {
					return Integer.parseInt(dialog.getValue());
				}
				else {
					return default_;
				}
			}
			
		};
		
		return (Integer) exec(runnable);

	}

	public float promptReal(String question, float default_) {
		// For backwards compatibility
		return (float) promptReal(question, (double)default_);
	}

	public double promptReal(String question, double default_) {
		InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "Real value prompt", question, default_ + "", new FloatValidator());
		if (dialog.open() == Window.OK) {
			return Double.parseDouble(dialog.getValue());
		}
		else {
			return default_;
		}
	}
	
	protected Object exec(ReturningRunnable runnable) {
		Display.getDefault().syncExec(runnable);
		//while (!runnable.isReady()){}
		return runnable.getResult();
	}
	
	class IntegerValidator implements IInputValidator {

		public String isValid(String newText) {
			try {
				Integer.parseInt(newText);
				return null;
			}
			catch (Exception ex) {
				return newText + " is not a valid integer";
			}
		}
		
	}

	class FloatValidator implements IInputValidator {

		public String isValid(String newText) {
			try {
				Float.parseFloat(newText);
				return null;
			}
			catch (Exception ex) {
				return newText + " is not a valid real number";
			}
		}
		
	}
	
	abstract class ReturningRunnable implements Runnable {
		
		Object result = null;
		//protected boolean ready = false;
		
		public void run() {
			result = runImpl();
		//	ready = true;
		}
		
		abstract public Object runImpl();
	
		//public boolean isReady() {
		//	return ready;
		//}
		
		public Object getResult() {
			return result;
		}
	}

	public void inform(final String message) {
		exec(new ReturningRunnable() {

			@Override
			public Object runImpl() {
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Information", message);
				return null;
			}
			
		});
	}
	
}
