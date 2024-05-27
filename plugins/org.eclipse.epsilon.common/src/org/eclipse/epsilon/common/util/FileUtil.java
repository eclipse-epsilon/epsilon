/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtil {
	private FileUtil() {}
	
	/**
	 * Temporary directory location
	 */
	private static Path tmpdir;
	
	static {
		final String javaTemp = System.getProperty("java.io.tmpdir");
		try {
			Files.createDirectory(tmpdir = Paths.get(javaTemp, "epsilon"));
		}
		catch (FileAlreadyExistsException fax) {
			// Oh well
		}
		catch (Exception ex) {
			System.err.println("Couldn't create temp directory for Epsilon, using default");
			ex.printStackTrace();
			tmpdir = Paths.get(javaTemp);
		}
	}

	private static final boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");

	// Default file and directory permissions (lazily initialized)
	private static class PosixPermissions {
		static final FileAttribute<Set<PosixFilePermission>> filePermissions = PosixFilePermissions
				.asFileAttribute(EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE));
		static final FileAttribute<Set<PosixFilePermission>> dirPermissions = PosixFilePermissions
				.asFileAttribute(EnumSet.of(
					PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE
				));
	}

	
	public static Path getCurrentDirectory() {
		return Paths.get(".").toAbsolutePath().normalize();
	}
	
	public static void setFileContents(String str, File file) throws Exception {
		try (FileWriter writer = new FileWriter(file)) {
			writer.append(str);
			writer.flush();
		}
	}
	
	public static String replaceExtension(String filename, String newExtension) {
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > -1) {
			filename = filename.substring(0, dotIndex+1) + newExtension;
		}
		return filename;
	}
	
	public static String removeExtension(String filename) {
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > -1) {
			filename = filename.substring(0, dotIndex);
		}
		return filename;
	}
	
	public static String getFileName(String path) {
		return getFileName(path, true);
	}
	
	public static String getFileName(String path, boolean includeExtension) {
		String filename = path.substring(path.replace("\\", "/").lastIndexOf('/')+1);
		if (!includeExtension) {
			filename = removeExtension(filename);
		}
		return filename;
	}
	
	/**
	 * Copied from @linkplain{https://stackoverflow.com/a/3571239/5870336}
	 * @param filename
	 * @return
	 * @since 1.6
	 */
	public static String getExtension(String filename) {
		String extension = "";
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'))) {
		    extension = filename.substring(dotIndex+1);
		}
		return extension;
	}
	
	public static String getFileContents(File file) throws Exception {
		final StringBuffer buffer = new StringBuffer();
		final String lineSeparator = System.getProperty("line.separator");

		for (String line : getFileLineContents(file)) {
			buffer.append(line);
			buffer.append(lineSeparator);
		}
		
		return buffer.toString();
	}
	
	public static Collection<String> getFileLineContents(File file) throws Exception {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			final List<String> lines = new LinkedList<>();

			String line = bufferedReader.readLine();

			while (line != null) {
				lines.add(line);
				line = bufferedReader.readLine();
			}

			return lines;
		}
	}
	
	public static String getAbsolutePath(String basePath, String relativePath) {
		File file = new File(relativePath);
		if (!file.isAbsolute()) {
			file = new File(basePath, relativePath);
		}
		return file.getAbsolutePath();
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public static boolean isInJarFile() {
		return isInJarFile(FileUtil.class);
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @since 1.6
	 */
	public static boolean isInJarFile(Class<?> clazz) {
		return new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath()).isFile();
	}
	
	public static Path getStandalonePath(String dir, Class<?> relativeTo) throws IOException {
		URI uri;
		try {
			uri = relativeTo.getResource("").toURI();
		}
		catch (URISyntaxException ex) {
			throw new IOException(ex);
		}
	    
		return Paths.get(uri).resolve(Paths.get(dir));
	}
	
	/**
	 * Convenience method for copying all files from the workspace / JAR
	 * path (relative to the class) to temp folder. Used for tests.
	 * 
	 * @see #getFileStandalone(String, Class)
	 * @param dir
	 * @param relativeTo
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @since 1.6
	 */
	public static File getDirectoryStandalone(String dir, Class<?> relativeTo) throws IOException {
		Objects.requireNonNull(dir, "Directory can't be null!");
		Objects.requireNonNull(relativeTo, "relativeTo (Class) can't be null!");
		dir = dir.replace('\\', '/');
		if (dir.endsWith("/")) {
			dir = dir.substring(0, dir.length()-1);
		}
		final String normalDir = dir;
		
		Path resource = getStandalonePath(normalDir, relativeTo);
		
		Collection<String> fileNames = Files.walk(resource)
			//.filter(p -> p.toFile().isFile())
			.map(p -> {
				String pathStr = p.toString().replace('\\', '/');
				pathStr = pathStr.substring(pathStr.indexOf(normalDir));
				return pathStr;
			})
			.collect(Collectors.toList());
		
		for (String fileName : fileNames) {
			getFileStandalone(fileName, relativeTo);
		}
		
		return getFileStandalone(normalDir, relativeTo);
	}
	
	/**
	 * Gets a file stored as a resource in a jar. Since not all users of the file
	 * can read from inside jars, we get the file as a stream and create a temp file
	 * with its contents.
	 * 
	 * @param name
	 * @param relativeTo
	 * @return
	 * @throws IOException
	 * @since 1.6
	 */
	public static File getFileStandalone(String name, Class<?> relativeTo) throws IOException {
		if (StringUtil.isEmpty(name)) return tmpdir.toFile();
		try (InputStream inStream = Objects.requireNonNull(relativeTo, "Class can't be null!").getResourceAsStream(name)) {
			return inputStreamToFile(inStream, name);
		}
	}
	
	/**
	 * The getFile method of old, before we tried to fix it to work in JAR files.
	 * 
	 * @param name
	 * @param relativeTo
	 * @return
	 * @since 1.6
	 */
	public static File getFileURL(String name, Class<?> relativeTo) {
		try {
			final File clazz = new File(URLDecoder.decode(relativeTo.getResource(relativeTo.getSimpleName() + ".class").getFile(), "UTF-8"));
			return new File(clazz.getParentFile(), name);
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(name + " could not be located relative to " + relativeTo);
		}
	}
	
	public static File getDirectoryOf(Class<?> clazz) throws IOException {
		return getFileURL(clazz.getSimpleName() + ".class", clazz).getParentFile();
	}
	
	public static String getPath(String name, Class<?> relativeTo) throws IOException {
		return getFileURL(name, relativeTo).getAbsolutePath();
	}

	public static void checkFileExists(final File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException(
				"File " + file.getPath() + " does not exist");
		}
	}

	public static File copyToTemp(File srcFile) throws IOException {
		File tmpFile = File.createTempFile("filecompare", "tmp");
		if (srcFile.isDirectory()) {
			tmpFile.delete();
			tmpFile.mkdir();
		}
		copy(srcFile, tmpFile);
		return tmpFile;
	}

	public static void copy(File srcFile, File dstFile) throws IOException {
		if (srcFile.isDirectory()) {
			dstFile.mkdir();
			for (File entry : srcFile.listFiles()) {
				copy(entry, new File(dstFile, entry.getName()));
			}
		}
		else {
			// Based on the second answer in http://stackoverflow.com/questions/106770
			try (
				FileInputStream isSrc = new FileInputStream(srcFile);
				FileOutputStream osDst = new FileOutputStream(dstFile);
				FileChannel chSrc = isSrc.getChannel();
				FileChannel chDst = osDst.getChannel();
			) {
				final long srcBytes = srcFile.length();
				long transferred = 0;
				while (transferred < srcBytes) {
					transferred += chDst.transferFrom(chSrc, transferred, srcBytes);
					chDst.position(transferred);
				}
			}
		}
	}

	public static Set<String> listFilesAsSet(File fileExpected) {
		return new HashSet<>(Arrays.asList(fileExpected.list()));
	}

	/**
	 * We implement our own comparison algorithm here, so we don't need Eclipse
	 * Compare to compute differences, but rather only to show them in the UI.
	 */
	public static boolean sameContents(File fileExpected, File fileActual, Set<String> ignoreFilenames, boolean ignoreLineEndings) throws IOException {
		if (fileExpected.isDirectory() != fileActual.isDirectory()) {
			// One is a file, the other is a directory: not the same
			return false;
		}

		if (fileExpected.isDirectory()) {
			// Both are directories: they should contain the same filenames,
			// and each pair should have the same contents
			final Set<String> expectedFilenames = listFilesAsSet(fileExpected);
			final Set<String> actualFilenames = listFilesAsSet(fileActual);
			expectedFilenames.removeAll(ignoreFilenames);
			actualFilenames.removeAll(ignoreFilenames);

			if (!expectedFilenames.equals(actualFilenames)) {
				return false;
			}
			for (String filename : expectedFilenames) {
				final File expectedEntry = new File(fileExpected, filename);
				final File actualEntry = new File(fileActual, filename);
				if (!sameContents(expectedEntry, actualEntry, ignoreFilenames)) {
					return false;
				}
			}
			return true;
		}
		else if (ignoreLineEndings) {
			// Use stream iterators to read the files
			final Iterator<String> expectedIterator = Files.lines(fileExpected.toPath()).iterator();
			final Iterator<String> actualIterator = Files.lines(fileActual.toPath()).iterator();
			
			// Compare each line (ignoring line endings)
			while (expectedIterator.hasNext() && actualIterator.hasNext()) {
				String expected = expectedIterator.next();
				String actual = actualIterator.next();
				
				if (!expected.equals(actual)) {
					return false;
				}
			}
		    
			// Ensure that both files have no further content
			return !expectedIterator.hasNext() && !actualIterator.hasNext();
		}
		else {
			if (fileExpected.length() != fileActual.length()) {
				// Different length: no need to read the files
				return false;
			}

			try (FileInputStream isExpected = new FileInputStream(fileExpected)) {
				try (FileInputStream isActual = new FileInputStream(fileActual)) {
					return sameContents(isExpected, isActual);
				}
			}
		}
	}
	
	public static boolean sameContents(File fileExpected, File fileActual, Set<String> ignoreFilenames) throws IOException {
		return sameContents(fileExpected, fileActual, ignoreFilenames, false);
	}

	public static boolean sameContents(InputStream isExpected, InputStream isActual) throws IOException {
		int chExpected, chActual;
		do {
			chExpected = isExpected.read();
			chActual = isActual.read();
		}
		while (chExpected == chActual && chExpected > 0 && chActual > 0);
		return chExpected == chActual;
	}
	
	/**
	 * 
	 * @param dir
	 * @return
	 * @since 1.6
	 */
	public static Path stringToPath(String dir) {
		Path path;
		try {
			path = Paths.get(dir);
		}
		catch (InvalidPathException ipx) {
			path = Paths.get(URI.create(dir));
		}
		return path;
	}
	
	/**
	 * {@link #deleteDirectory(Path)}
	 * @param path
	 * @throws IOException
	 * @since 1.6
	 */
	public static void deleteDirectory(final String dir) throws IOException {
		deleteDirectory(stringToPath(dir));
	}
	
	/**
	 * WARNIING: Use with caution! Deletes all contents and sub-directories of the specified path.
	 * @param dir The absolute path to the directory.
	 * @throws IOException
	 * @since 1.6
	 */
	public static void deleteDirectory(final Path path) throws IOException {
		if (Files.exists(path)) {
			Files.walk(path)
		        .map(Path::toFile)
		        .sorted((o1, o2) -> -o1.compareTo(o2))
		        .forEach(File::delete);
		}
	}
	
	/**
	 * {@link #readDirectory(Path)}
	 * @param dir
	 * @return
	 * @throws IOException
	 * @since 1.6
	 */
	public static Map<Path, byte[]> readDirectory(final String dir) throws IOException {
		return readDirectory(stringToPath(dir));
	}
	
	/**
	 * Reads entire directory recursively, mapping the contents of each file
	 * as a string to its path.
	 * 
	 * @param dir The root directory.
	 * @return The contents of each file in the directory and its subdirectories.
	 * @throws IOException
	 * @since 1.6
	 */
	public static Map<Path, byte[]> readDirectory(final Path dir) throws IOException {		
		Map<Path, byte[]> contents = new HashMap<>();
		for (Path path : ((Iterable<Path>)Files.walk(dir)::iterator)) {
			if (Files.isRegularFile(path)) {
				contents.put(path, Files.readAllBytes(path));
			}
		}
		return contents;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @since 1.6
	 */
	public static File createTempFile(String name) {
		return createTempFile(name, "tmp");
	}

	/**
	 * 
	 * @param name
	 * @param extension
	 * @return
	 * @since 1.6
	 */
	public static File createTempFile(String name, String extension) {
		Path tmpFile;
		try {
			tmpFile = createTempFile(null, name, extension);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Could not create temp file ", e);
		}
		return tmpFile.toFile();
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @since 1.6
	 */
	public static File createTempDir(String name) {
		return createTempDir(name, false);
	}
	
	/**
	 * 
	 * @param name
	 * @param reuse
	 * @return
	 * @since 1.6
	 */
	public static File createTempDir(String name, boolean reuse) {
		Path tmpFile = null;
		if (reuse) {
			DirectoryStream.Filter<Path> filter = file -> Files.isDirectory(file) && file.getFileName().startsWith(Paths.get(name));
			Iterator<Path> it;
			try {
				it = Files.newDirectoryStream(tmpdir, filter).iterator();
				tmpFile = it.hasNext() ? it.next() : null;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (tmpFile == null) {
			try {
				tmpFile = createTempDirectory(null, name);
			}
			catch (IOException e) {
				throw new IllegalArgumentException("Could not create temp directory ", e);
			}
		}
		return tmpFile.toFile();
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param name
	 * @return
	 * @since 1.6
	 */
	private static File inputStreamToFile(InputStream inputStream, String name) throws IOException {
		//Objects.requireNonNull(inputStream, "InputStream is null!")
		name = name.replace('\\', '/').replace("../", "");
		String prefix = name;
		String suffix = "";
		Path dirStructure = null;
		
		// Name might be a path, get the file name, remove it
		if (name.contains("/")) {
			dirStructure = Paths.get(tmpdir.toString(), name.substring(0, name.lastIndexOf("/")), "/");
			dirStructure.toFile().mkdirs();
			name = name.substring(name.lastIndexOf("/") + 1);
		}

		String[] parts = name.split("\\.");
		boolean isFile = parts.length == 2;
		if (isFile) {
			prefix = parts[0];
			suffix = "." + parts[1];
		}
		else {
			prefix = prefix.substring(prefix.lastIndexOf('/')+1);
		}
		File file = null;
		Path tempPath;
		
		try {
			tempPath = isFile ?
				createTempFile(dirStructure, prefix, suffix) :
				createTempDirectory(dirStructure, prefix);
		}
		catch (IllegalArgumentException e) {
			throw e;
		}
		
		if ((file = tempPath.toFile()).exists() && file.isDirectory()) {
			return file;
		}
		
		if (inputStream != null) try (OutputStream outputStream = new FileOutputStream(file)) {
			byte[] bytes = new byte[1<<10];
			for (int read = 0; (read = inputStream.read(bytes)) != -1; outputStream.write(bytes, 0, read));
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.delete(tempPath);
                }
                catch (IOException ex) {
                    // Don't care - not even worth printing
                	// since we don't want to confuse anyone
                	// or pollute logs with useless stack trace
                }
	        }));
		}

		return file;
	}

	static Path createTempDirectory(Path dir, String prefix, FileAttribute<?>... attrs) throws IOException {
		return create(dir, prefix, null, true, attrs);
	}

	static Path createTempFile(Path dir, String prefix, String suffix, FileAttribute<?>... attrs) throws IOException {
		return create(dir, prefix, suffix, false, attrs);
	}

	/**
	 * Creates a file or directory in the given given directory (or in the
	 * temporary directory if dir is {@code null}).
	 * @since 1.6
	 */
	private static Path create(Path dir, String prefix, String suffix, boolean createDirectory, FileAttribute<?>[] attrs) throws IOException {
		if (prefix == null)
			prefix = "";
		if (suffix == null)
			suffix = createDirectory ? "" : ".tmp";
		if (dir == null)
			dir = tmpdir;

		// in POSIX environments use default file and directory permissions
		// if initial permissions not given by caller.
		if (isPosix && dir.getFileSystem() == FileSystems.getDefault()) {
			if (attrs.length == 0) {
				// no attributes so use default permissions
				attrs = new FileAttribute<?>[1];
				attrs[0] = (createDirectory) ? PosixPermissions.dirPermissions : PosixPermissions.filePermissions;
			}
			else {
				// check if posix permissions given; if not use default
				boolean hasPermissions = false;
				for (FileAttribute<?> attr : attrs) {
					if ("posix:permissions".equals(attr.name())) {
						hasPermissions = true;
						break;
					}
				}
				if (!hasPermissions) {
					FileAttribute<?>[] copy = new FileAttribute<?>[attrs.length + 1];
					System.arraycopy(attrs, 0, copy, 0, attrs.length);
					attrs = copy;
					attrs[attrs.length - 1] = (createDirectory) ? PosixPermissions.dirPermissions
							: PosixPermissions.filePermissions;
				}
			}
		}

		// loop generating random names until file or directory can be created
		SecurityManager sm = System.getSecurityManager();
		
		Path f;
		try {
			f = generatePath(prefix, suffix, dir);
		}
		catch (InvalidPathException e) {
			// don't reveal temporary directory location
			if (sm != null)
				throw new IllegalArgumentException("Invalid prefix or suffix");
			throw e;
		}
		try {
			return createDirectory ? Files.createDirectory(f, attrs) : Files.createFile(f, attrs);
		}
		catch (FileAlreadyExistsException fax) {
			// FIXME Add the file deletion so we don't need this.
			// FIXME or make each test use a different folder...
			return f;
		}
	}

	/**
	 * 
	 * @param prefix
	 * @param suffix
	 * @param dir
	 * @return
	 * @since 1.6
	 */
	private static Path generatePath(String prefix, String suffix, Path dir) {
		Path name = dir.getFileSystem().getPath(prefix + suffix);
		// the generated name should be a simple file name
		if (name.getParent() != null && !StringUtil.isEmpty(suffix)) {
			throw new IllegalArgumentException("Invalid prefix or suffix");
		}
		return dir.resolve(name);
	}
}
