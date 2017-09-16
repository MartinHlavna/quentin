package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

/**
 * generate unique random UUID
 *
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class UuidCommand implements Command {

	@Override
	public String getName() {
		return "uuid";
	}

	@Override
	public String getDescription() {
		return "uuid                generate unique random UUID";
	}

	@Override
	public String getManual() {
		return "\nGenerate pseudo random UUID using java.util.UUID api \n" +
				"Example:   q:uuid";
	}

	@Override
	public Result execute(String sender, String[] params) {
		String value = UUID.randomUUID().toString();
		return new Result(Status.OK, sender, value);
	}
}