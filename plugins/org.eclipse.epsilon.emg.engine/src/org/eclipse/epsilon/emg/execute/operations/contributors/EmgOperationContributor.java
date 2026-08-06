/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - additional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg.execute.operations.contributors;

import java.util.Base64;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.emg.EmgModule;
import org.eclipse.epsilon.emg.random.EmgRandomGenerator;
import org.eclipse.epsilon.emg.random.IEmgRandomGenerator;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

/**
 * The Class ObjectOperationContributor delegates all the random generating
 * functions to a EmgRandomGenerator but overloads the nextXXXList operations to
 * allow the user to specify @list annotations values as listIDs.
 */
public class EmgOperationContributor extends OperationContributor
	implements IEmgRandomGenerator<IEmgRandomGenerator.DefaultCharacterSet> {

	/** The delegate. */
	private final EmgRandomGenerator delegate;

	/** The module. */
	private final EmgModule module;
	/** The list samples. */
	private Map<String, List<Integer>> listSamples;

	/** The hasids salt. */
	private String hasids_salt = "Epsilon EMG";

	/**
	 * Instantiates a new emg operation contributor.
	 * 
	 * @param module the module
	 */
	public EmgOperationContributor(EmgModule module) {
		delegate = new EmgRandomGenerator(module.getContext());
		this.module = module;
	}

	/**
	 * Instantiates a new emg operation contributor.
	 *
	 * @param module the module
	 * @param seed   the seed
	 */
	public EmgOperationContributor(EmgModule module, long seed) {
		delegate = new EmgRandomGenerator(module.getContext(), seed);
		this.module = module;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor#
	 * contributesTo(java.lang.Object)
	 */
	@Override
	public boolean contributesTo(Object target) {
		return target != null;
	}

	/**
	 * Decode string to numbers.
	 *
	 * @param hash the encrypt string
	 * @return decoded numbers
	 */
	public long[] decode(String hash) {
		byte[] code = Base64.getDecoder().decode(hash);
		// convert byte [] to long []
		int numLength = code.length >>> 2;
		long[] num = new long[numLength];
		for (int i = 0; i < numLength; i++) {
			int j = i << 2;
			int x = 0;
			x += (code[j++] & 0xff) << 0;
			x += (code[j++] & 0xff) << 8;
			x += (code[j++] & 0xff) << 16;
			x += (code[j++] & 0xff) << 24;
			num[i] = x;
		}
		return num;
	}

	/**
	 * Encrypt numbers to string.
	 *
	 * @param number the number
	 * @return the encrypt string
	 */
	public String encode(int number) {
		BigInteger num = BigInteger.valueOf(number);
		return Base64.getEncoder().encodeToString(num.toByteArray());
	}

	/**
	 * Gets the hasids salt.
	 * 
	 * @return the hasids_salt
	 */
	public String getHasids_salt() {
		return hasids_salt;
	}

	/**
	 * Gets the elements from create rules with the @list annotation with the given
	 * name parameter.
	 *
	 * @param name the name
	 * @return the named list
	 */
	public Collection<Object> getNamedList(String name) {
		Map<String, List<Object>> existing = module.getNamedCreatedObjects();
		if (existing.containsKey(name)) {
			return existing.get(name);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextAddTo(int, int)
	 */
	@Override
	public List<Integer> nextAddTo(int n, int m) throws EolRuntimeException {
		return delegate.nextAddTo(n, m);
	}

	/**
	 * Next binomial value.
	 *
	 * @param numberOfTrials       the number of trials
	 * @param probabilityOfSuccess the probability of success
	 * @return the double
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextBinomialValue(int,
	 *      double)
	 */
	@Override
	public double nextBinomialValue(int numberOfTrials, double probabilityOfSuccess) {
		return delegate.nextBinomialValue(numberOfTrials, probabilityOfSuccess);
	}

	/**
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextBoolean()
	 */
	public boolean nextBoolean() {
		return delegate.nextBoolean();
	}

	/**
	 * @param bytes
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextBytes(byte[])
	 */
	public void nextBytes(byte[] bytes) {
		delegate.nextBytes(bytes);
	}

	/**
	 * Next camel case string.
	 *
	 * @param length        the length
	 * @param minWordLength the min word length
	 * @return the string
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextCamelCaseString(int,
	 *      int)
	 */
	@Override
	public String nextCamelCaseString(int length, int minWordLength) throws EolRuntimeException {
		return delegate.nextCamelCaseString(length, minWordLength);
	}

	/**
	 * Next capitalised string.
	 *
	 * @param charSet the char set
	 * @param length  the length
	 * @return the string
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextCapitalisedString(java.lang.String,
	 *      int)
	 */
	@Override
	public String nextCapitalisedString(String charSet, int length) {
		return delegate.nextCapitalisedString(charSet, length);
	}

	/**
	 * Next dobule.
	 *
	 * @param upper the upper
	 * @return the double
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextDouble(double)
	 */
	@Override
	public double nextDouble(double upper) throws EolRuntimeException {
		return delegate.nextDouble(upper);
	}

	/**
	 * Next dobule.
	 *
	 * @param lower the lower
	 * @param upper the upper
	 * @return the double
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextDouble(double,
	 *      double)
	 */
	@Override
	public double nextDouble(double lower, double upper) {
		return delegate.nextDouble(lower, upper);
	}

	/**
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextDouble()
	 */
	public double nextDouble() {
		return delegate.nextDouble();
	}

	/**
	 * Next exponential value.
	 *
	 * @param mean the mean
	 * @return the double
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextExponentialValue(double)
	 */
	@Override
	public double nextExponentialValue(double mean) {
		return delegate.nextExponentialValue(mean);
	}

	/**
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextFloat()
	 */
	public float nextFloat() {
		return delegate.nextFloat();
	}

	/**
	 * Next from collection.
	 *
	 * @param c the c
	 * @return the object
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextFromCollection(java.util.Collection)
	 */
	@Override
	public Object nextFromCollection(Collection<?> c) {
		return delegate.nextFromCollection(c);
	}

	/**
	 * The listID is the value of a @list annotation of a create operation. If no
	 * create operation with that @list annotation value is found, the operation is
	 * delegated to the underlying {@link EmgRandomGenerator}.
	 *
	 * @param listID the list ID
	 * @return the object
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextFromList(java.lang.String)
	 */
	@Override
	public Object nextFromList(String listID) throws EolRuntimeException {
		Collection<Object> existing = getNamedList(listID);
		if (existing == null) {
			return delegate.nextFromList(listID);
		}
		else {
			return delegate.nextFromCollection(existing);
		}
	}

	/**
	 * The listID is the value of a @list annotation of a create operation. If no
	 * create operation with that @list annotation value is found, the operation is
	 * delegated to the underlying {@link EmgRandomGenerator}.
	 *
	 * @param listID the list ID
	 * @return the object
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextSampleFromList(java.lang.String)
	 */
	@Override
	public Object nextFromListAsSample(String listID) throws EolRuntimeException {
		List<Object> existing = (List<Object>) getNamedList(listID);
		if (existing == null) {
			return delegate.nextFromListAsSample(listID);
		}
		else {
			Map<String, List<Integer>> sampleList = getListSamples();
			int size = existing.size();
			List<Integer> index = delegate.getIndex(listID, size, sampleList);
			Object result = null;
			try {
				result = existing.get(index.remove(0));
			}
			catch (IndexOutOfBoundsException e) {
				// More matches than existing, what to do here? Does returning
				// null makes EPL disregard the match?
				// EolRuntimeException.propagate(e);
			}
			return result;
		}
	}

	/**
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextGaussian()
	 */
	public double nextGaussian() {
		return delegate.nextGaussian();
	}

	/**
	 * Next http URI.
	 *
	 * @param addPort     the add port
	 * @param addPath     the add path
	 * @param addQuery    the add query
	 * @param addFragment the add fragment
	 * @return the string
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextHttpURI(boolean,
	 *      boolean, boolean, boolean)
	 */
	@Override
	public String nextHttpURI(boolean addPort, boolean addPath, boolean addQuery, boolean addFragment)
		throws EolRuntimeException {
		return delegate.nextHttpURI(addPort, addPath, addQuery, addFragment);
	}

	/**
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextInt()
	 */
	public int nextInt() {
		return delegate.nextInt();
	}

	/**
	 * @param n
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextInt(int)
	 */
	public int nextInt(int n) {
		return delegate.nextInt(n);
	}

	/**
	 * Next integer.
	 *
	 * @param upper the upper
	 * @return the int
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextInteger(int)
	 */
	@Override
	public int nextInteger(int upper) throws EolRuntimeException {
		return delegate.nextInteger(upper);
	}

	/**
	 * Next integer.
	 *
	 * @param lower the lower
	 * @param upper the upper
	 * @return the int
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextInteger(int, int)
	 */
	@Override
	public int nextInteger(int lower, int upper) throws EolRuntimeException {
		return delegate.nextInteger(lower, upper);
	}

	/**
	 * @return
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextLong()
	 */
	public long nextLong() {
		return delegate.nextLong();
	}

	/**
	 * Next long.
	 *
	 * @param upper the upper
	 * @return the long
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextLong(long)
	 */
	@Override
	public long nextLong(long upper) throws EolRuntimeException {
		return delegate.nextLong(upper);
	}

	/**
	 * Next long.
	 *
	 * @param lower the lower
	 * @param upper the upper
	 * @return the long
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextLong(long, long)
	 */
	@Override
	public long nextLong(long lower, long upper) throws EolRuntimeException {
		return delegate.nextLong(lower, upper);
	}

	/**
	 * Next sample.
	 *
	 * @param c the c
	 * @param k the k
	 * @return the list
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextSample(java.util.Collection,
	 *      int)
	 */
	@Override
	public List<Object> nextSample(Collection<?> c, int k) throws EolRuntimeException {
		return delegate.nextSample(c, k);
	}

	/**
	 * The listID is the value of a @list annotation of a create operation. If no
	 * create operation with that @list annotation value is found, the operation is
	 * delegated to the underlying {@link EmgRandomGenerator}.
	 *
	 * @param listID the list ID
	 * @param k      the k
	 * @return the list
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextSample(java.lang.String,
	 *      int)
	 */
	@Override
	public List<Object> nextSample(String listID, int k) throws EolRuntimeException {
		List<Object> existing = (List<Object>) getNamedList(listID);
		if (existing == null) {
			return delegate.nextSample(listID, k);
		}
		else {
			return nextSample(existing, k);
		}
	}

	/**
	 * Next string.
	 *
	 * @param charSet the char set
	 * @param length  the length
	 * @return the string
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextString(java.lang.String,
	 *      int)
	 */
	@Override
	public String nextString(String charSet, int length) {
		return delegate.nextString(charSet, length);
	}

	/**
	 * Next URI.
	 *
	 * @return the string
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextURI()
	 */
	@Override
	public String nextURI() throws EolRuntimeException {
		return delegate.nextURI();
	}

	/**
	 * Next URI.
	 *
	 * @param addPort     the add port
	 * @param addPath     the add path
	 * @param addQuery    the add query
	 * @param addFragment the add fragment
	 * @return the string
	 * @throws EolRuntimeException the eol runtime exception
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextURI(boolean,
	 *      boolean, boolean, boolean)
	 */
	@Override
	public String nextURI(boolean addPort, boolean addPath, boolean addQuery, boolean addFragment)
		throws EolRuntimeException {
		return delegate.nextURI(addPort, addPath, addQuery, addFragment);
	}

	/**
	 * Next value.
	 * 
	 * @return the double
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#nextValue()
	 */
	@Override
	public double nextValue() {
		return delegate.nextValue();
	}

	/**
	 * Sets the hasids salt.
	 *
	 * @param hasids_salt the hasids_salt to set
	 */
	public void setHasids_salt(String hasids_salt) {
		this.hasids_salt = hasids_salt;
	}

	/**
	 * @param seed
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#setSeed(int)
	 */
	public void setSeed(int seed) {
		delegate.setSeed(seed);
	}

	/**
	 * @param seed
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#setSeed(int[])
	 */
	public void setSeed(int[] seed) {
		delegate.setSeed(seed);
	}

	/**
	 * @param seed
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#setSeed(long)
	 */
	public void setSeed(long seed) {
		delegate.setSeed(seed);
	}

	/**
	 * Use binomial distribution.
	 *
	 * @param numberOfTrials       the number of trials
	 * @param probabilityOfSuccess the probability of success
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#useBinomialDistribution(int,
	 *      double)
	 */
	public void useBinomialDistribution(int numberOfTrials, double probabilityOfSuccess) {
		delegate.useBinomialDistribution(numberOfTrials, probabilityOfSuccess);
	}

	/**
	 * Use exponential distribution.
	 *
	 * @param mean the mean
	 * @see org.eclipse.epsilon.emg.random.EmgRandomGenerator#useExponentialDistribution(double)
	 */
	public void useExponentialDistribution(double mean) {
		delegate.useExponentialDistribution(mean);
	}

	/**
	 * Gets the list samples.
	 *
	 * @return the listSamples
	 */
	private Map<String, List<Integer>> getListSamples() {
		if (listSamples == null) {
			listSamples = new HashMap<>();
		}
		return listSamples;
	}
}