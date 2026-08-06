/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance;

import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions.AllPrintsContributeToTrace;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions.DynamicOutputSectionsContributeToTrace;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions.ExplicitPrintsShouldContributeToTrace;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions.StaticSectionsDoNotContributeToTrace;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions.TemplateOperationsContributeToTrace;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.misc.TraceShouldNotContainDuplicateFeatureAccessesPerTextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.subtemplates.ReusedTemplateContributesTwiceToTrace;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.textlocations.GenerateShouldUpdateTextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.textlocations.ShouldRespectNewLines;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AllPrintsContributeToTrace.class, DynamicOutputSectionsContributeToTrace.class, StaticSectionsDoNotContributeToTrace.class, ExplicitPrintsShouldContributeToTrace.class,
               ReusedTemplateContributesTwiceToTrace.class, TraceShouldNotContainDuplicateFeatureAccessesPerTextLocation.class,
               ShouldRespectNewLines.class, GenerateShouldUpdateTextLocation.class, TemplateOperationsContributeToTrace.class})
public class EglFineGrainedTraceabilityAcceptanceTestSuite {

}
