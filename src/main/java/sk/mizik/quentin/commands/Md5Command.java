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
public class Md5Command implements Command {

	@Override
	public String getName() {
		return "md5";
	}

	@Override
	public String getDescription() {
		return "md5                 hash provided string using MD5 algorithm";
	}

	@Override
	public String getManual() {
		return "\nHash provided plain text string using google guava MD5 algorithm implementation\n" +
				"Example:   q:md5 secretpassword";
	}

	@Override
	public Result execute(String sender, String[] params) {
		String value = Hashing.md5().hashString(params[0], StandardCharsets.UTF_8).toString();
		return new Result(Status.OK, sender, value);
	}
}