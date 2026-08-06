/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg.random;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

// TODO: Auto-generated Javadoc
/**
 * The simplest implementation of the RandomAttributeGenerator interface. All
 * attributes are generated as strings. It is the responsibility of the calling
 * class/method to do the appropriate conversion. This classes uses the Apache
 * Commons Math RandomDataGenerator.
 *
 */
public class EmgRandomGenerator implements IEmgRandomGenerator<IEmgRandomGenerator.DefaultCharacterSet> {
	/** The uri scheme. */
	private final String[] URI_SCHEME = { "http", "ssh", "ftp" };

	/** The uri domain. */
	private final String[] URI_DOMAIN = { ".com", ".org", ".net", ".int", ".edu", ".gov", ".mil" };

	/** The generator. */
	private final RandomDataGenerator generator = new RandomDataGenerator();

	/** The context. */
	private final IEolContext context;

	/** Be default we use a normal distribution. */
	private Distribution globalDistribution = Distribution.Uniform;

	/** The lower bound of the distribution is 0. */
	private double firstArg = 0;

	/** The upper bound of the distribution is 1 so probability tests will work */
	private double secondArg = 1;

	/** The list samples. */
	private Map<String, List<Integer>> listSamples;

	/**
	 * Instantiates a new emg random generator.
	 *
	 * @param context the context
	 */
	public EmgRandomGenerator(IEolContext context) {
		super();
		this.context = context;
	}

	/**
	 * Instantiates a new emg random generator.
	 *
	 * @param context the context
	 * @param seed    the seed
	 */
	public EmgRandomGenerator(IEolContext context, long seed) {
		this(context);
		this.generator.reSeed(seed);
	}

	/**
	 * @return the generator
	 */
	public RandomDataGenerator getGenerator() {
		return generator;
	}

	public List<Integer> getIndex(String listID, int size, Map<String, List<Integer>> sampleList)
		throws EolRuntimeException {
		List<Integer> index;
		if (sampleList.containsKey(listID)) {
			index = sampleList.get(listID);
		}
		else {
			index = new ArrayList<>();
			sampleList.put(listID, index);
			int[] indexArray = null;
			try {
				indexArray = generator.nextPermutation(size, size);
			}
			catch (NotStrictlyPositiveException | NumberIsTooLargeException e) {
				EolRuntimeException.propagate(e);
			}
			for (int element : indexArray) {
				index.add(element);
			}
		}
		return index;
	}

	@Override
	public List<Integer> nextAddTo(int n, int m) throws EolRuntimeException {
		assert n > 1;
		int len = n - 1;
		int[] index = generator.nextPermutation(m, len);
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			values.add(index[i]);
		}
		values.add(0, 0);
		values.add(m);
		Collections.sort(values);
		List<Integer> result = new ArrayList<>();
		ListIterator<Integer> it = values.listIterator(1);
		while (it.hasNext()) {
			int low = it.previous();
			it.next();
			int high = it.next();
			result.add(high - low);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextBinomialValue(int,
	 * double)
	 */
	@Override
	public double nextBinomialValue(int numberOfTrials, double probabilityOfSuccess) {
		return generator.nextBinomial((int) firstArg, secondArg);
	}

	@Override
	public boolean nextBoolean() {
		return generator.getRandomGenerator().nextBoolean();
	}

	@Override
	public void nextBytes(byte[] bytes) {
		generator.getRandomGenerator().nextBytes(bytes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextCamelCaseString(int,
	 * int)
	 */
	@Override
	public String nextCamelCaseString(int length, int minWordLength) throws EolRuntimeException {

		StringBuilder sb = new StringBuilder();
		String base = nextString("LETTER_LOWER", length);
		int[] chunks = new int[length / minWordLength + 1];
		int remaining = length;
		int nextWord = 0;
		chunks[0] = 0;
		int i = 1;
		do {
			if (remaining > minWordLength) {
				nextWord += nextInteger(minWordLength, remaining);
				remaining -= nextWord;
			}
			else {
				nextWord = 1; // So chunks[i++] is 0
				remaining = 0;
			}
			try {
				chunks[i++] = nextWord - 1;
			}
			catch (ArrayIndexOutOfBoundsException ex) {
				break;
			}
		}
		while (remaining > 0);
		// chunks[i] = length;
		int index = 0;
		for (int j = 0; j < chunks.length - 1; j++) {
			if ((j > 0) && (chunks[j] == 0)) {
				break;
			}
			index = chunks[j];
			try {
				String capital = base.substring(index, index + 1).toUpperCase();
				int endIndex = chunks[j + 1];
				if (endIndex == 0) {
					endIndex = length;
				}
				sb.append(capital);
				sb.append(base.substring(index + 1, endIndex));
			}
			catch (StringIndexOutOfBoundsException ex) {
				System.out.println(ex);
			}

		}
		return sb.toString();
	}

	@Override
	public String nextCapitalisedString(String charSet, int length) {
		String lower = nextString(charSet, length);
		if (length > 1) {
			return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
		}
		else {
			return Character.toString(lower.charAt(0)).toUpperCase();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextDobule(double)
	 */
	@Override
	public double nextDouble(double upper) throws EolRuntimeException {

		return nextDouble(0, upper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextDobule(double,
	 * double)
	 */
	@Override
	public double nextDouble(double lower, double upper) {

		double diff = upper - lower;
		if (diff == 0) {
			return lower;
		}
		if (upper < lower) {
			return generator.getRandomGenerator().nextDouble() * diff + upper;
		}
		return generator.getRandomGenerator().nextDouble() * diff + lower;
	}

	@Override
	public double nextDouble() {
		return generator.getRandomGenerator().nextDouble();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextExponentialValue(
	 * double)
	 */
	@Override
	public double nextExponentialValue(double mean) {
		return generator.nextExponential(firstArg);
	}

	@Override
	public float nextFloat() {
		return generator.getRandomGenerator().nextFloat();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextFromCollection(java.
	 * util.Collection)
	 */
	@Override
	public Object nextFromCollection(Collection<?> c) {

		int upper = c.size() - 1;
		int index = 0;
		try {
			index = nextInteger(0, upper);
		}
		catch (NumberFormatException | EolRuntimeException e) {
			// Should never get here
			e.printStackTrace();
		}
		Object[] objects = c.toArray();
		return objects[index];
	}

	/**
	 * The listID must be the name of a parameter in the launch configuration. The
	 * value of the parameter can be either a CSV list of strings or the name of a
	 * file. The name of the file should be full path and each line in the file is
	 * considered a separate item.
	 *
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextFromList(java.lang.String)
	 */
	@Override
	public Object nextFromList(String listID) throws EolRuntimeException {
		// Get the list from the context
		String list = (String) context.getFrameStack().get(listID).getValue();
		List<String> valuesList = getValuesFromList(list);
		return nextFromCollection(valuesList);
	}

	/**
	 * The listID must be the name of a parameter in the launch configuration. The
	 * value of the parameter can be either a CSV list of strings or the name of a
	 * file. The name of the file should be full path and each line in the file is
	 * considered a separate item.
	 *
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextSampleFromList(java.lang.String)
	 */
	@Override
	public Object nextFromListAsSample(String listID) throws EolRuntimeException {
		// Get the list from the context
		String list = (String) context.getFrameStack().get(listID).getValue();
		List<String> valuesList = getValuesFromList(list);
		int size = valuesList.size();
		String[] values = new String[size];
		values = valuesList.toArray(values);
		Map<String, List<Integer>> sampleList = getListSamples();
		List<Integer> index = getIndex(listID, size, sampleList);
		String result = null;
		try {
			result = valuesList.get(index.remove(0));
		}
		catch (IndexOutOfBoundsException e) {
			// TODO An additional configuration parameter could be used to generate a new
			// index array
			System.out.println("No more elements to pick from the list, " + listID);
		}
		return result;
	}

	@Override
	public double nextGaussian() {
		return generator.getRandomGenerator().nextGaussian();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextHttpURI(boolean,
	 * boolean, boolean, boolean)
	 */
	@Override
	public String nextHttpURI(boolean addPort, boolean addPath, boolean addQuery, boolean addFragment)
		throws EolRuntimeException {

		StringBuilder sb = new StringBuilder();
		// scheme
		sb.append("http");
		sb.append("://");
		// Host
		sb.append("www.");
		sb.append(nextString("LETTER", nextInteger(6, 10)));
		sb.append(getRandomUriDomain());
		if (addPort) {
			sb.append(":");
			sb.append(nextInteger(9999));
		}
		sb.append("/");
		if (addPath) {
			for (int i = 0; i < nextInteger(1, 4); i++) {
				sb.append(nextString("LETTER_LOWER", nextInteger(3, 6)));
				sb.append("/");
			}
		}
		if (addQuery) {
			String separator = "?";
			for (int i = 0; i < nextInteger(1, 4); i++) {
				sb.append(separator);
				sb.append(nextString("LETTER_LOWER", nextInteger(3, 5)));
				sb.append("=");
				sb.append(nextString("NUMERIC", nextInteger(5, 8)));
				separator = "&";
			}
		}
		if (addFragment) {
			sb.append("#");
			sb.append(nextString("ID_SYMBOL", nextInteger(1, 15)));
		}
		return sb.toString();
	}

	@Override
	public int nextInt() {
		return generator.getRandomGenerator().nextInt();
	}

	@Override
	public int nextInt(int n) {
		return generator.getRandomGenerator().nextInt(n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextIngeter(int)
	 */
	@Override
	public int nextInteger(int upper) throws EolRuntimeException {
		return nextInteger(0, upper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextIngeter(int, int)
	 */
	@Override
	public int nextInteger(int lower, int upper) throws EolRuntimeException {
		int value = 0;
		try {
			value = generator.nextInt(lower, upper);
		}
		catch (NumberIsTooLargeException e) {
			EolRuntimeException.propagate(e);
		}
		return value;
	}

	@Override
	public long nextLong() {
		return generator.getRandomGenerator().nextLong();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextLong(long)
	 */
	@Override
	public long nextLong(long upper) throws EolRuntimeException {

		return nextLong(0, upper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextLong(long, long)
	 */
	@Override
	public long nextLong(long lower, long upper) throws EolRuntimeException {

		long value = 0;
		try {
			value = generator.nextLong(lower, upper);
		}
		catch (NumberIsTooLargeException e) {
			EolRuntimeException.propagate(e);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextSample(java.util.
	 * Collection, int)
	 */
	@Override
	public List<Object> nextSample(Collection<?> c, int k) throws EolRuntimeException {
		List<Object> sample = null;
		try {
			Object[] result = generator.nextSample(c, k);
			sample = new ArrayList<>(Arrays.asList(result));
		}
		catch (NotStrictlyPositiveException | NumberIsTooLargeException e) {
			EolRuntimeException.propagate(e);
		}
		return sample;
	}

	/**
	 * The listID must be the name of a parameter in the launch configuration. The
	 * value of the parameter can be either a CSV list of strings or the name of a
	 * file. The name of the file should be full path and each line in the file is
	 * considered a separate item.
	 *
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextSample(java.lang.String,
	 *      int)
	 */
	@Override
	public List<Object> nextSample(String listID, int k) throws EolRuntimeException {
		// Get the list from the context
		String list = (String) context.getFrameStack().get(listID).getValue();
		List<String> valuesList = getValuesFromList(list);
		List<Object> sample = null;
		try {
			Object[] result = generator.nextSample(valuesList, k);
			sample = new ArrayList<>(result.length);
			for (Object element : result)
				sample.add(element);
		}
		catch (NotStrictlyPositiveException | NumberIsTooLargeException e) {
			EolRuntimeException.propagate(e);
		}
		return sample;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextString(java.lang.
	 * String, int)
	 */
	@Override
	public String nextString(String charSet, int length) {
		DefaultCharacterSet cSet = null;
		for (DefaultCharacterSet cs : DefaultCharacterSet.values()) {
			if (cs.name().equals(charSet)) {
				cSet = cs;
				break;
			}
		}
		if (cSet == null) {
			cSet = DefaultCharacterSet.ID;
		}
		StringBuilder sb = new StringBuilder();
		char[] chars = cSet.getCharacters();
		for (int i = 0; i < length; i++) {
			sb.append(chars[generator.nextInt(0, chars.length - 1)]);
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextURI()
	 */
	@Override
	public String nextURI() throws EolRuntimeException {
		return nextURI(nextBoolean(), nextBoolean(), nextBoolean(), nextBoolean());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextURI(boolean,
	 * boolean, boolean, boolean)
	 */
	@Override
	public String nextURI(boolean addPort, boolean addPath, boolean addQuery, boolean addFragment)
		throws EolRuntimeException {
		StringBuilder sb = new StringBuilder();
		// scheme
		String uriScheme = getRandomUriScheme();
		sb.append(uriScheme);
		sb.append("://");
		// user:password
		if (!uriScheme.equals("http")) {
			if (nextBoolean()) {
				sb.append(nextString("LETTER_LOWER", nextInteger(6, 10)));
				if (nextBoolean()) {
					sb.append(":");
					sb.append(generator.nextSecureHexString(nextInteger(6, 10)));
				}
				sb.append("@");
			}
		}
		// Host
		sb.append("www.");
		sb.append(nextString("LETTER", nextInteger(6, 10)));
		sb.append(getRandomUriDomain());
		if (addPort) {
			sb.append(":");
			sb.append(nextInteger(9999));
		}
		sb.append("/");
		if (addPath) {
			for (int i = 0; i < nextInteger(1, 4); i++) {
				sb.append(nextString("LETTER_LOWER", nextInteger(3, 6)));
				sb.append("/");
			}
		}
		if (addQuery) {
			String separator = "?";
			for (int i = 0; i < nextInteger(1, 4); i++) {
				sb.append(separator);
				sb.append(nextString("LETTER_LOWER", nextInteger(3, 5)));
				sb.append("=");
				sb.append(nextString("NUMERIC", nextInteger(5, 8)));
				separator = "&";
			}
		}
		if (addFragment) {
			sb.append("#");
			sb.append(nextString("ID_SYMBOL", nextInteger(1, 15)));
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epsilon.emg.random.IEmgRandomGenerator#nextValue()
	 */
	@Override
	public double nextValue() {
		switch (globalDistribution) {
		case Binomial:
			return nextBinomialValue((int) firstArg, secondArg);
		case Exponential:
			return nextExponentialValue(firstArg);
		case Uniform:
			return generator.nextUniform(firstArg, secondArg);
		default:
			return 0;
		}
	}

	@Override
	public void setSeed(int seed) {
		generator.reSeed(seed);
	}

	@Override
	public void setSeed(int[] seed) {
		// the following number is the largest prime that fits in 32 bits (it is 2^32 -
		// 5)
		final long prime = 4294967291l;
		long combined = 0l;
		for (int s : seed) {
			combined = combined * prime + s;
		}
		setSeed(combined);
	}

	@Override
	public void setSeed(long seed) {
		generator.reSeed(seed);
	}

	/**
	 * Use binomial distribution.
	 * 
	 * @param numberOfTrials       the number of trials
	 * @param probabilityOfSuccess the probability of success
	 */
	public void useBinomialDistribution(int numberOfTrials, double probabilityOfSuccess) {
		this.globalDistribution = Distribution.Binomial;
		this.firstArg = numberOfTrials;
		this.secondArg = probabilityOfSuccess;
	}

	/**
	 * Use exponential distribution.
	 * 
	 * @param mean the mean
	 */
	public void useExponentialDistribution(double mean) {
		this.globalDistribution = Distribution.Exponential;
		this.firstArg = mean;
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

	/**
	 * Gets the random uri domain.
	 *
	 * @return the random uri domain
	 */
	private String getRandomUriDomain() {
		return (String) nextFromCollection(Arrays.asList(URI_DOMAIN));
	}

	/**
	 * Gets the random uri scheme.
	 *
	 * @return the random uri scheme
	 */
	private String getRandomUriScheme() {
		return (String) nextFromCollection(Arrays.asList(URI_SCHEME));
	}

	/**
	 * Gets the values from list.
	 *
	 * @param list the list
	 * @return the values from list
	 * @throws EolRuntimeException the eol runtime exception
	 */
	protected List<String> getValuesFromList(String list) throws EolRuntimeException {
		// TODO We assume URI/paths don't have commas
		String[] values = list.split(",");
		List<String> valuesList = null;
		if (values.length == 1) {
			// It should be a path
			File file = new File(list);
			if (file.isDirectory())
				throw new EolRuntimeException("Given list path is not a valid file.");
			if (file.exists()) {
				try (Scanner s = new Scanner(file)) {
					valuesList = new ArrayList<>();
					while (s.hasNext()) {
						valuesList.add(s.next());
					}
				}
				catch (FileNotFoundException e) {
					EolRuntimeException.propagate(e);
				}
			}
		}
		else {
			valuesList = Arrays.asList(values);
		}
		return valuesList;
	}
}