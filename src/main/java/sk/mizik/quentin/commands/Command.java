package sk.mizik.quentin.commands;

/**
 * Interface for all commands
 *
 * @author Marian Mizik
 * @since 1.0.0
 */
public interface Command {
	/**
	 * Get name of the command. Used to identify command
	 *
	 * @return Name of the command
	 */
	String getName();

	/**
	 * Get description of command. Used in q:help command
	 *
	 * @return Description of command
	 */
	String getDescription();

	/**
	 * Get manual of command. Used in q:man command
	 *
	 * @return Manual of command
	 */
	String getManual();

	/**
	 * Execute command and return result
	 *
	 * @param sender Sender of message
	 * @param params Arguments for command
	 * @return Result of command
	 */
	Result execute(String sender, String[] params);
}
