package sk.mizik.quentin.service;

import com.google.common.io.Files;
import sk.mizik.quentin.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * File service handling all file operations
 * <p>
 * Service writes and reads files from server configuration base dir
 *
 * @author Martin Krupa
 * @author Martin Hlavňa
 */
public class FileService {

	/**
	 * Open file in basedir
	 *
	 * @param filename Name of the file
	 * @return File handler
	 */
	public static File open(String filename) {
		String serverHome = System.getProperty("jboss.server.base.dir");
		if (serverHome == null) {
			serverHome = System.getProperty("jetty.home");
		}

		return new File(serverHome + File.separator + filename);
	}

	/**
	 * Read lines from file
	 *
	 * @param file File handler of existing file
	 * @return List of file lines
	 * @throws IOException In case of IO error
	 */
	public static List<String> readAsLines(File file) throws IOException {
		return Files.readLines(file, Charset.forName(Constants.UTF8));
	}

	/**
	 * Read entire contents of file as single String
	 *
	 * @param file File handler of existing file
	 * @return File contents
	 * @throws IOException In case of IO error
	 */
	public static String readAsString(File file) throws IOException {
		return Files.toString(file, Charset.forName(Constants.UTF8));
	}

	/**
	 * Write lines to file.
	 * <p>
	 * If file exists, this command overwrites existing content.
	 *
	 * @param file  File handler of existing file
	 * @param lines Lines to write
	 * @throws IOException In case of IO error
	 */
	public static void writeLines(File file, List<String> lines) throws IOException {
		java.nio.file.Files.write(file.toPath(), lines, Charset.forName(Constants.UTF8));
	}

}
