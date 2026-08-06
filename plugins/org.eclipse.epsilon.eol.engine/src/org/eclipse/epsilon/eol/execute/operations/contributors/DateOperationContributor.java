/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof Date;
	}
	
	@Override
	protected Date getTarget() {
		return (Date) super.getTarget();
	}
	
	public int getDayOfTheWeek() {
		Date date = getTarget();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public long getDifferenceInDays(Date other) {
		Date date = getTarget();
		long diff = date.getTime() - other.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
}
