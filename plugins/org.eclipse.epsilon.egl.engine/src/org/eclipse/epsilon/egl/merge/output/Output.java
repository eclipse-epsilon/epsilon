/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge.output;

import java.util.*;
import java.util.stream.Collectors;

public class Output {

	private final List<Region> regions = new ArrayList<>(0);
	private final Collection<String> locatedRegionIds = new LinkedHashSet<>();
	private final Collection<String> duplicatedLocatedRegionIds = new LinkedHashSet<>();
	private final Map<String, LocatedRegion> locatedRegionCache = new HashMap<>();
	
	public Output(Region... regions) {
		this(Arrays.asList(regions));	
	}
	
	public Output(Collection<? extends Region> regions) {
		this.regions.addAll(regions);
		processIds();
	}
	
	private void processIds() {
		for (Region r : regions) {
			if (r instanceof LocatedRegion) {
				final LocatedRegion lr = (LocatedRegion) r;
				final String id = lr.getId();
				if (locatedRegionIds.contains(id)) {
					duplicatedLocatedRegionIds.add(id);
				}
				else {
					locatedRegionIds.add(id);
					locatedRegionCache.put(id, lr);
				}
			}
		}
	}
	
	public List<Region> getRegions() {
		return Collections.unmodifiableList(regions);
	}
	
	public List<LocatedRegion> getLocatedRegions() {
		return regions.stream()
				.filter(LocatedRegion.class::isInstance)
				.map(LocatedRegion.class::cast)
				.collect(Collectors.toList());
	}
	
	public LocatedRegion getLocatedRegion(String id) {
		return locatedRegionCache.get(id);
	}
	
	public List<String> getProblems() {
		final List<String> problems = new ArrayList<>(duplicatedLocatedRegionIds.size());
		for (String id : duplicatedLocatedRegionIds) {
			problems.add("Output contains more than one region with the identifier '"+id+"'");
		}
		return Collections.unmodifiableList(problems);
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (Region region : regions) {
			builder.append(region);
		}
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Output)) return false;
		final Output that = (Output) o;
		return Objects.equals(this.regions, that.regions);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(regions);
	}
}
