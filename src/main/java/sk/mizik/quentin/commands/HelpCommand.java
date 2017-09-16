package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Print list of all commands
 *
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class HelpCommand implements Command {

    @Inject
    @Any
    Instance<Command> commands;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "help                print list of all commands";
    }

    @Override
    public String getManual() {
        return "Show all commands in a list form with brief description of the command meaning\n" +
                "Example:   q:help";

    }

    @Override
    public Result execute(String sender, String[] params) {
        StringBuilder sb = new StringBuilder();
        for (Command c : commands) {
            sb.append("\nq:").append(c.getDescription());
        }
        return new Result(Status.OK, sender, sb.toString());
    }
}