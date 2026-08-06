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

import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.random.RandomGenerator;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * The Random Attribute Generator interface defines the different available
 * methods to generate random data. This can be boolean values, numbers and
 * strings, or more specialized data as names, cities, emails, phone numbers,
 * etc.
 * 
 * @author Hoacio Hoyos
 *
 */
public interface IEmgRandomGenerator<K extends CharacterSet> extends RandomGenerator {

	/**
	 * Returns an array of n integers that addvar to m.
	 * 
	 * @param n
	 * @param m
	 * @return
	 * @throws EolRuntimeException
	 */
	public List<Integer> nextAddTo(int n, int m) throws EolRuntimeException;

	/**
	 * Generates a random value from the Binomial Distribution.
	 * 
	 * @param Distribution
	 * @return
	 */
	public double nextBinomialValue(int numberOfTrials, double probabilityOfSuccess) throws EolRuntimeException;

	/**
	 * Generates a random string of the given length using the specified character
	 * set formatted in <a href="https://en.wikipedia.org/wiki/CamelCase">
	 * CameCase</a> format. Characters are picked from the LETTER set using a
	 * uniform distribution.
	 *
	 * @param length        the length
	 * @param minWordLenght the minimum word length
	 * @return the string
	 * @throws EolRuntimeException
	 */
	public String nextCamelCaseString(int length, int minWordLength) throws EolRuntimeException;

	/**
	 * Next capitalised string.
	 *
	 * @param charSet the char set
	 * @param length  the length
	 * @return the string
	 */
	public String nextCapitalisedString(String charSet, int length);

	/**
	 * Returns a pseudorandom, uniformly distributed double value between 0
	 * (inclusive) and the specified value (exclusive), drawn from this random
	 * attribute generator's sequence.
	 * 
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	public double nextDouble(double upper) throws EolRuntimeException;
	
	/**
	 * Alias for nextDouble. Introduced to maintain API compatibility until 3.0.
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	@Deprecated
	default public double nextDobule(double upper) throws EolRuntimeException {
		return nextDouble(upper);
	}
	
	/**
	 * Returns a pseudorandom, uniformly distributed double value between lower and
	 * upper (endpoints included), drawn from this random attribute generator's
	 * sequence.
	 * 
	 * @param lower
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	public double nextDouble(double lower, double upper) throws EolRuntimeException;
	
	/**
	 * Alias for nextDouble. Introduced to maintain API compatibility until 3.0.
	 * @param lower
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	@Deprecated
	default public double nextDobule(double lower, double upper) throws EolRuntimeException {
		return nextDouble(lower, upper);
	}
	
	
	/**
	 * Generates a random value from the Exponential Distribution.
	 * 
	 * @param Distribution
	 * @return
	 */
	public double nextExponentialValue(double mean) throws EolRuntimeException;
	// TODO add more methods for other distributions

	/**
	 * Returns a single object selected randomly from the Collection c using a
	 * uniform distribution.
	 * 
	 * @param list the list
	 * @return the t
	 */
	public Object nextFromCollection(Collection<?> c);

	/**
	 * Returns a single objects selected randomly from the list using a uniform
	 * distribution. Particular implementations need to define what a valid listID
	 * is and how the data associated with it will be retrieved.
	 *
	 * @param listID the listID
	 * @return the t
	 */
	public Object nextFromList(String listID) throws EolRuntimeException;

	/**
	 * The list is treated as a sample without replacement, i.e. each call will
	 * return a unique member of the list. Throws an exception if list is empty or
	 * if the method is invoked more times than the number of items in the list.
	 * Particular implementations need to define what a valid listID is and how the
	 * data associated with it will be retrieved.
	 *
	 * @param listID
	 * @return
	 * @throws EolRuntimeException
	 */
	public Object nextFromListAsSample(String listID) throws EolRuntimeException;

	/**
	 * Generates a random URI that complies to:
	 * http:[//host[:port]][/]path[?query][#fragment]
	 *
	 * The scheme is The host is generated from a random string and uses a top-level
	 * domain. The optional parameters will add additional information to the URI.
	 * The number of paths and queries are random between 1 and 4.
	 * 
	 * @param addPort
	 * @param addPath
	 * @param addQuery
	 * @param addFragment
	 * @return
	 */
	public String nextHttpURI(boolean addPort, boolean addPath, boolean addQuery, boolean addFragment)
		throws EolRuntimeException;

	/**
	 * Returns a pseudorandom, uniformly distributed int value between 0 (inclusive)
	 * and the specified value (exclusive), drawn from this random attribute
	 * generator's sequence.
	 * 
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	public int nextInteger(int upper) throws EolRuntimeException;

	/**
	 * Returns a pseudorandom, uniformly distributed int value between lower and
	 * upper (endpoints included), drawn from this random attribute generator's
	 * sequence.
	 * 
	 * @param lower
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	public int nextInteger(int lower, int upper) throws EolRuntimeException;

	/**
	 * Returns a pseudorandom, uniformly distributed long value between 0
	 * (inclusive) and the specified value (exclusive), drawn from this random
	 * attribute generator's sequence.
	 * 
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	public long nextLong(long upper) throws EolRuntimeException;

	/**
	 * Returns a pseudorandom, uniformly distributed long value between lower and
	 * upper (endpoints included), drawn from this random attribute generator's
	 * sequence.
	 * 
	 * @param lower
	 * @param upper
	 * @return
	 * @throws EolRuntimeException
	 */
	public long nextLong(long lower, long upper) throws EolRuntimeException;

	/**
	 * Returns an array of k objects selected randomly from the Collection c using a
	 * uniform distribution. Particular implementations need to define what a valid
	 * listID is and how the data associated with it will be retrieved.
	 *
	 * Sampling from c is without replacement; but if c contains identical objects,
	 * the sample may include repeats. If all elements of c are distinct, the
	 * resulting object collection represents a Simple Random Sample of size k from
	 * the elements of c.
	 */
	public List<Object> nextSample(Collection<?> c, int k) throws EolRuntimeException;

	/**
	 * Returns an array of k objects selected randomly from the list using a uniform
	 * distribution. Particular implementations need to define what a valid listID
	 * is and how the data associated with it will be retrieved.
	 *
	 * Sampling from the list is without replacement; but if the list contains
	 * identical objects, the sample may include repeats. If all elements of the
	 * list are distinct, the resulting object collection represents a Simple Random
	 * Sample of size k from the elements of c.
	 */
	public List<Object> nextSample(String listID, int k) throws EolRuntimeException;

	/**
	 * Generates a random string of the given length using the specified character
	 * set. Characters are picked from the set using a uniform distribution.
	 * 
	 * @param charSet
	 * @param length
	 * @return
	 */
	public String nextString(String charSet, int length);

	/**
	 * Generates a random URI. The port, path, query and fragment are added
	 * randomly.
	 * 
	 * @return
	 */
	public String nextURI() throws EolRuntimeException;

	/**
	 * Generates a random URI that complies to:
	 * scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]
	 *
	 * The scheme is randomly selected from: http, ssh and ftp. For ssh and ftp, a
	 * user and pasword are randomly generated. The host is generated from a random
	 * string and uses a top-level domain. The optional parameters will add
	 * additional information to the URI. The number of paths and queries are random
	 * between 1 and 4.
	 * 
	 * @param addPort
	 * @param addPath
	 * @param addQuery
	 * @param addFragment
	 * @return
	 */
	public String nextURI(boolean addPort, boolean addPath, boolean addQuery, boolean addFragment)
		throws EolRuntimeException;

	/**
	 * Returns the next pseudorandom, value from this random attribute generator's
	 * sequence. The value is picked from the defined distribution.
	 * 
	 * @param Distribution
	 * @return
	 */
	public double nextValue() throws EolRuntimeException;

	public enum DefaultCharacterSet implements CharacterSet {
		ID("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"), NUMERIC("1234567890"),
		LETTER("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), LETTER_UPPER("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		LETTER_LOWER("abcdefghijklmnopqrstuvwxyz"), UPPER_NUM("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),
		LOWER_NUM("abcdefghijklmnopqrstuvwxyz1234567890"),
		ID_SYMBOL("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*()_+-=[]\\{}|;':\"<>?,./"),
		HEX_LOWER("abcdef1234567890"), HEX_UPPER("ABCDEF1234567890");

		private char[] characters;
		private int lenght;

		private DefaultCharacterSet(String characters) {
			this.characters = characters.toCharArray();
			this.lenght = characters.length();
		}

		public char[] getCharacters() {
			return characters;
		}

		protected int getLenght() {
			return lenght;
		}

	}

	public enum Distribution {
		Beta, Binomial, Cauchy, ChiSquare, Exponential, F, Gamma, Gaussian, HyperGeometric, Uniform, Pascal, Poisson, T,
		Weibull, Zipf
	}

}
