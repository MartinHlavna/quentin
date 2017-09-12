package sk.mizik.quentin.service;

import com.google.common.io.Files;
import sk.mizik.quentin.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class FileService {

	public static File open(String filename) {
		String serverHome = System.getProperty("jboss.server.base.dir");
		if (serverHome == null) {
			serverHome = System.getProperty("jetty.home");
		}

		return new File(serverHome + File.separator + filename);
	}

	public static List<String> readAsLines(File file) throws IOException {
		return Files.readLines(file, Charset.forName(Constants.UTF8));
	}

	public static String readAsString(File file) throws IOException {
		return Files.toString(file, Charset.forName(Constants.UTF8));
	}

	public static void writeLines(File file, List<String> lines) throws IOException {
		java.nio.file.Files.write(file.toPath(), lines, Charset.forName(Constants.UTF8));
	}

}
