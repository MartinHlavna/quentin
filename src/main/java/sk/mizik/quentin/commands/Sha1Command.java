package sk.mizik.quentin.commands;


import com.google.common.hash.Hashing;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;

/**
 * hash provided string using MD5 algorithm
 *
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class Sha1Command implements Command {

	@Override
	public String getName() {
		return "sha1";
	}

	@Override
	public String getDescription() {
		return "sha1                hash provided string using MD5 algorithm";
	}

	@Override
	public String getManual() {
		return "\nHash provided plain text string using google guava SHA1 algorithm implementation\n" +
				"Example:   q:sha1 secretpassword";
	}

	@Override
	public Result execute(String sender, String[] params) {
		String value = Hashing.sha1().hashString(params[0], StandardCharsets.UTF_8).toString();
		return new Result(Status.OK, sender, value);
	}
}