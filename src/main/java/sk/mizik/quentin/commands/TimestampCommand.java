package sk.mizik.quentin.commands;


import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;

/**
 * print current unix timestamp
 *
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */

@ApplicationScoped
public class TimestampCommand implements Command {

	@Override
	public String getName() {
		return "timestamp";
	}

	@Override
	public String getDescription() {
		return "timestamp           print current unix timestamp";
	}

	@Override
	public String getManual() {
		return "\nPrint current timestamp using Java8 date time api and UTF timezone\n" +
				"Example:   q:timestamp";
	}

	@Override
	public Result execute(String sender, String[] params) {
		String value = String.valueOf(Instant.now().getEpochSecond());
		return new Result(Status.OK, sender, value);
	}
}