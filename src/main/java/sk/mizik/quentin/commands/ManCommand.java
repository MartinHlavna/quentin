package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Print usage manual for specified command
 *
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class ManCommand implements Command {

    @Inject
    @Any
    Instance<Command> commands;

    @Override
    public String getName() {
        return "man";
    }

    @Override
    public String getDescription() {
        return "man                 print usage manual for specified command";
    }

    @Override
    public String getManual() {
        return "\nShow detailed usage manual with examples. " +
                "Command name can be used both with q: prefix or without it. \n" +
                "Example:   q:man md5";
    }

    @Override
    public Result execute(String sender, String[] params) {
        for (Command c : commands) {
            if (params[0].endsWith(c.getName())) {
                return new Result(Status.OK, sender, c.getManual());
            }
        }
        return new Result(Status.OK, sender, "Sorry mate. I have no memory of such command whatsoever.");
    }
}