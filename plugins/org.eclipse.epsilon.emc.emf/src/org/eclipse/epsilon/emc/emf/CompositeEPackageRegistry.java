package org.eclipse.epsilon.emc.emf;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;

public class CompositeEPackageRegistry implements EPackage.Registry {
	
	protected Set<EPackage.Registry> registries;
	
	public CompositeEPackageRegistry(EPackage.Registry... registries) {
		this.registries = new LinkedHashSet<EPackage.Registry>(Arrays.asList(registries));
	}
	
	@Override
	public EPackage getEPackage(String nsURI) {
		return registries.stream()
		        .map(registry -> registry.getEPackage(nsURI))
		        .filter(Objects::nonNull)
		        .findFirst()
		        .orElse(null);
	}

	@Override
	public EFactory getEFactory(String nsURI) {
		return registries.stream()
		        .map(registry -> registry.getEFactory(nsURI))
		        .filter(Objects::nonNull)
		        .findFirst()
		        .orElse(null);
	}

	@Override
	public int size() {
		return registries.stream()
		        .mapToInt(EPackage.Registry::size)
		        .sum();
	}

	@Override
	public boolean isEmpty() {
		return registries.stream()
		        .allMatch(EPackage.Registry::isEmpty);
	}

	@Override
	public boolean containsKey(Object key) {
		return registries.stream()
			.anyMatch(registry -> registry.containsKey(key));
	}

	@Override
	public boolean containsValue(Object value) {
		return registries.stream()
			.anyMatch(registry -> registry.containsValue(value));
	}

	@Override
	public Object get(Object key) {
		return registries.stream()
		        .map(registry -> registry.get(key))
		        .filter(Objects::nonNull)
		        .findFirst()
		        .orElse(null);
	}

	@Override
	public Object put(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		return registries.stream()
		        .flatMap(registry -> registry.keySet().stream())
		        .collect(Collectors.toSet());
	}

	@Override
	public Collection<Object> values() {
		return registries.stream()
		        .flatMap(registry -> registry.values().stream())
		        .collect(Collectors.toList());
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return registries.stream()
		        .flatMap(registry -> registry.entrySet().stream())
		        .collect(Collectors.toSet());
	}

	

}
