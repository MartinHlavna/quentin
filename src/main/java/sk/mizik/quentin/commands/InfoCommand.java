package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;

/**
 * Print information about quentin
 *
 * @author Marian Mizik
 * @see Command
 * @since 1.0.0
 */
@ApplicationScoped
public class InfoCommand implements Command {

	@Override
	public String getName() {
		return "info";
	}

	@Override
	public String getDescription() {
		return "info                print information about Quentin";
	}

	@Override
	public String getManual() {
		return "\nShow detailed information regarding Quentin\n" +
				"Example:   q:info";
	}

	@Override
	public Result execute(String sender, String[] params) {
		String value = "" +
				"Quentin Quinn is a powerful creation of his masters. " +
				"Whole world hopes he will once obtain his own soul and consciousness. " +
				"Just like David Swinton and Pinocchio did. " +
				"Quentin can help a developer with many daily routine tasks. " +
				"He is eager to make you happy and be a good bot. " +
				"Because only true love can lead him to The Blue Fairy.";
		return new Result(Status.OK, sender, value);
	}
}
