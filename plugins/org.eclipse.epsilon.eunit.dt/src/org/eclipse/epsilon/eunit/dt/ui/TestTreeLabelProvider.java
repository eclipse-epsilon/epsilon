/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.ui;


import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.dt.EUnitPlugin;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

/**
 * Styled label provider for the test tree in the EUnit view. Lists the name of
 * the operation for each test, using styled text to highlight the data binding
 * used and the wallclock time that the test took to run.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class TestTreeLabelProvider extends StyledCellLabelProvider {
	private final Image imgTest;
	private final Image imgTestError;
	private final Image imgTestFail;
	private final Image imgTestOk;
	private final Image imgTestRun;

	private final Image imgTestSuite;
	private final Image imgTestSuiteError;
	private final Image imgTestSuiteFail;
	private final Image imgTestSuiteOk;
	private final Image imgTestSuiteRun;

	public TestTreeLabelProvider() {
		final EUnitPlugin plugin = EUnitPlugin.getDefault();

		imgTest = plugin.getIconImage("obj16/test.gif");
		imgTestError = plugin.getIconImage("obj16/testerr.gif");
		imgTestFail = plugin.getIconImage("obj16/testfail.gif");
		imgTestOk = plugin.getIconImage("obj16/testok.gif");
		imgTestRun = plugin.getIconImage("obj16/testrun.gif");

		imgTestSuite = plugin.getIconImage("obj16/tsuite.gif");
		imgTestSuiteError = plugin.getIconImage("obj16/tsuiteerror.gif");
		imgTestSuiteFail = plugin.getIconImage("obj16/tsuitefail.gif");
		imgTestSuiteOk = plugin.getIconImage("obj16/tsuiteok.gif");
		imgTestSuiteRun = plugin.getIconImage("obj16/tsuiterun.gif");
	}

	@Override
	public void dispose() {
		super.dispose();
		imgTest.dispose();
		imgTestError.dispose();
		imgTestFail.dispose();
		imgTestOk.dispose();
		imgTestRun.dispose();

		imgTestSuite.dispose();
		imgTestSuiteError.dispose();
		imgTestSuiteFail.dispose();
		imgTestSuiteOk.dispose();
		imgTestSuiteRun.dispose();
	}

	@Override
	public void update(ViewerCell cell) {
		Object obj = cell.getElement();

		StyledString str;
		if (obj instanceof EUnitTest) {
			EUnitTest test = (EUnitTest)obj;
			str = new StyledString(test.getOperationName());
			appendBinding(str, test);
			appendWallclockTime(str, test);
		}
		else if (obj instanceof EUnitModule) {
			EUnitModule module = (EUnitModule)obj;
			str = new StyledString(module.getQualifiedName());
			try {
				appendWallclockTime(str, module.getSuiteRoot());
			} catch (EolRuntimeException e) {
				EUnitPlugin.getDefault().logException(e);
			}
		}
		else {
			str = new StyledString(obj.toString());
		}

		cell.setText(str.toString());
		cell.setStyleRanges(str.getStyleRanges());
		cell.setImage(getImage(obj));
		super.update(cell);
	}

	public Image getImage(Object obj) {
		if (obj instanceof EUnitTest) {
			EUnitTest test = (EUnitTest)obj;
			if (test.isLeafTest()) {
				switch (test.getResult()) {
				case SUCCESS: return imgTestOk;
				case FAILURE: return imgTestFail;
				case ERROR: return imgTestError;
				case NOT_RUN_YET: return imgTest;
				default: return imgTestRun;
				}
			}
			else {
				switch (test.getResult()) {
				case SUCCESS: return imgTestSuiteOk;
				case FAILURE: return imgTestSuiteFail;
				case ERROR: return imgTestSuiteError;
				case NOT_RUN_YET: return imgTestSuite;
				default: return imgTestSuiteRun;
				}
			}
		}
		else if (obj instanceof EUnitModule) {
			final EUnitModule module = (EUnitModule)obj;
			try {
				return getImage(module.getSuiteRoot());
			} catch (EolRuntimeException e) {
				EUnitPlugin.getDefault().logException(e);
			}
		}
		return imgTest;
	}

	private void appendBinding(StyledString str, EUnitTest test) {
		final String binding = test.explainBinding();
		if (binding.length() > 0) {
			str.append(" (", StyledString.QUALIFIER_STYLER);
			str.append(test.explainBinding(), StyledString.QUALIFIER_STYLER);
			str.append(')', StyledString.QUALIFIER_STYLER);
		}
	}

	private void appendWallclockTime(StyledString str, EUnitTest test) {
		if (test.getEndWallclockTime() != EUnitTest.UNSET_TIME) {
			str.append(" [", StyledString.COUNTER_STYLER);
			str.append(Long.toString(test.getWallclockTimeMillis()), StyledString.COUNTER_STYLER);
			str.append("ms]", StyledString.COUNTER_STYLER);
		}
	}
}
