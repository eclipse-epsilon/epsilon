/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.util.profiling;

import java.time.Duration;

/**
 * Convenience data class for storing profiling information.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ProfileDiagnostic implements java.io.Serializable {
	
	private static final long serialVersionUID = -1275024406100263604L;

	public static enum MemoryUnit {
		BYTES, KB, MB, GB, TB, PB, EB, ZB, YB;
		
		public static double convertFromBytes(MemoryUnit to, long amount) {
			return convertUnits(MemoryUnit.BYTES, to, amount);
		}
		
		public static double convertUnits(MemoryUnit from, MemoryUnit to, double amount) {
			double result = amount, factor = 1024d;
			int diff = from.compareTo(to);
			
			if (diff > 0) {		//from > to
				for (; diff != 0; diff--) {
					result *= factor;
				}
			}
			else if (diff < 0) {	//from < to
				for (; diff != 0; diff++) {
					result /= factor;
				}
				
			}
			
			return result;
		}
	}
	
	public final String stageName;
	public final Duration executionTime;
	public final double memoryUsage;
	public final MemoryUnit memoryUnits;
	
	public ProfileDiagnostic(String stage, Duration execTime, double memory, MemoryUnit units) {
		this.stageName = stage;
		this.executionTime = execTime;
		this.memoryUsage = memory;
		this.memoryUnits = units;
	}
	
	public ProfileDiagnostic(String stage, long execTimeNanos, long memoryBytes) {
		this(stage, Duration.ofNanos(execTimeNanos), memoryBytes, MemoryUnit.BYTES);
	}
}
