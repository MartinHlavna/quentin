package sk.mizik.quentin.commands;

/**
 * @author Marian Mizik
 * @since 1.0.0
 */
public interface Command {
    String getName();
    String getDescription();
    String getManual();
    Result execute(String sender, String[] params);
}
