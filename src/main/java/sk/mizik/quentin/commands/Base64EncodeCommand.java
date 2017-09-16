package sk.mizik.quentin.commands;

import com.google.common.io.BaseEncoding;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;

/**
 * Encode string to base64 format
 *
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class Base64EncodeCommand implements Command {

	@Override
	public String getName() {
		return "base64encode";
	}

	@Override
	public String getDescription() {
		return "base64encode        encode provided string using base64";
	}

	@Override
	public String getManual() {
		return "\nEncode plain text string using Google Guava implementation.\n" +
				"Example:   q:base64encode login:password";
	}

	@Override
	public Result execute(String sender, String[] params) {
		String value = BaseEncoding.base64().encode(String.join(" ", params).getBytes(StandardCharsets.UTF_8));
		return new Result(Status.OK, sender, value);
	}
}