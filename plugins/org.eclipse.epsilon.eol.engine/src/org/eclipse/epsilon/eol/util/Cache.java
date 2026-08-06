/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.util;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
	
	protected ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
	protected Map<IdentityBasedWeakReference, V> map = new HashMap<>();
	protected Thread cleanUpThread;
	
	public Map<IdentityBasedWeakReference, V> getMap() {
		return map;
	}
	
	protected Thread createCleanUpThread() {
		Thread th = new Thread(() -> {
			while (!map.isEmpty() && !Thread.currentThread().isInterrupted()) try {
				Object reference = referenceQueue.remove();
				if (reference instanceof IdentityBasedWeakReference) synchronized (map) {
					map.remove(reference);
				}
			}
			catch (InterruptedException ie) {}
		});
		th.setDaemon(true);
		th.setName(getClass().getName()+"-cleanup");
		return th;
	}
	
	public V get(Object key) {
		synchronized (map) {
			return map.get(new IdentityBasedWeakReference(key, referenceQueue));
		}
	}
	
	public V put(K key, V value) {
		IdentityBasedWeakReference reference = new IdentityBasedWeakReference(key, referenceQueue);
		synchronized (map) {
			if (!map.containsKey(reference)) {
				map.put(reference, value);
				if (map.size() == 1 && cleanUpThread != null && !cleanUpThread.isAlive()) {
					cleanUpThread = createCleanUpThread();
					cleanUpThread.start();
				}
			}
			return value;
		}
	}
	
	public int size() {
		return map.size();
	}
	
	public void dispose() {
		if (cleanUpThread != null) {
			cleanUpThread.interrupt();
			cleanUpThread = null;
		}
		map.clear();
	}
}
