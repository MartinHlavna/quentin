package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

/**
 * Check if text matches regex pattern
 *
 * @author Martin Krupa
 * @see Command
 * @since 1.1.5
 */
@ApplicationScoped
public class RegexCheckerCommand implements Command {

    @Override
    public String getName() {
        return "regex";
    }

    @Override
    public String getDescription() {
        return "regex                Check if text matches regex pattern";
    }

    @Override
    public String getManual() {
        return "\nCheck whether provided text matches regex pattern\n" +
                "Example:   q:regex a*b aaaaab";
    }

    @Override
    public Result execute(String sender, String[] params) {
        if (params.length < 2) {
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Insufficient parameters.");
        }
        try {
            final String pattern = params[0].trim();
            final String textToMatch = String.join(" ", Arrays.copyOfRange(params, 1, params.length));
            return new Result(Status.OK, sender, Pattern.matches(pattern, textToMatch) ? "Match" : "No match");
        } catch (PatternSyntaxException e) {
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Invalid pattern syntax");
        }
    }
}