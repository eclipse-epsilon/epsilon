/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.epsilon.egl.merge.output.LocatedRegion;
import org.eclipse.epsilon.egl.merge.output.Output;
import org.eclipse.epsilon.egl.merge.output.RegionType;
import org.eclipse.epsilon.egl.merge.partition.Partitioner;
import org.eclipse.epsilon.egl.status.ProtectedRegionWarning;

public class Merger {

	protected final Partitioner partitioner;
	protected final Output generated, existing;
	
	protected final List<ProtectedRegionWarning> warnings = new LinkedList<>();
	
	public Merger(Partitioner partitioner, String generated, String existing) {
		this.partitioner = partitioner;
		this.generated   = partitioner.partition(generated);
		this.existing    = partitioner.partition(existing);
	}
	
	public List<ProtectedRegionWarning> getMergeWarnings() {
		return Collections.unmodifiableList(warnings); 
	}
	
	public String merge() {
		warnings.clear();
		
		//boolean hasProtectedRegions = false;
		boolean hasControlledRegions = false;
		
		for (LocatedRegion region : generated.getLocatedRegions()) {
			if (region.getType() == RegionType.Protected) {
				//hasProtectedRegions = true;
				break;
			}
			else if (region.getType() == RegionType.Controlled) {
				hasControlledRegions = true;
				break;
			}
		}
		
		if (hasControlledRegions) {
			return mergeControlledRegions();
		}
		else /*if (hasProtectedRegions)*/ {
			return mergeProtectedRegions();
		}
		/*else {
			return generated.toString();
		}*/
		
	}
	
	public String mergeControlledRegions() {
		for (LocatedRegion existingRegion : existing.getLocatedRegions()) {
			if (existingRegion != null) {
				final LocatedRegion generatedRegion = generated.getLocatedRegion(existingRegion.getId());
							
				if (existingRegion.isEnabled() && existingRegion.getType() == RegionType.Controlled) {
					generatedRegion.setEnabled(true);
					existingRegion.setContents(generatedRegion.getContents());
				}
			}
		}
		
		return existing.toString();
	}
	
	public String mergeProtectedRegions() {
		warnings.clear();
		final Set<String> idsPresentInGenerated = new HashSet<>();
		
		for (LocatedRegion generatedRegion : generated.getLocatedRegions()) {
			final LocatedRegion existingRegion = existing.getLocatedRegion(generatedRegion.getId());
			
			idsPresentInGenerated.add(generatedRegion.getId());
			
			if (existingRegion != null && existingRegion.isEnabled() && existingRegion.getType() == RegionType.Protected) {
				generatedRegion.setEnabled(true);
				generatedRegion.setContents(existingRegion.getContents());
			}
		}
		
		for (LocatedRegion existingRegion : existing.getLocatedRegions()) {
			if (!idsPresentInGenerated.contains(existingRegion.getId())) {
				warnings.add(new ProtectedRegionWarning(existingRegion.getId()));
			}
		}
		
		return generated.toString();
	}
}
