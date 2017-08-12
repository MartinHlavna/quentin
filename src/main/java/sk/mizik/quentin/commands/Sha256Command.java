package sk.mizik.quentin.commands;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
import com.google.common.hash.Hashing;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class Sha256Command implements Command {

    @Override
    public String getName() {
        return "sha256";
    }

    @Override
    public String getDescription() {
        return "sha256              hash provided string using SHA256 algorithm";
    }

    @Override
    public String getManual() {
        return "\nHash provided plain text string using google guava SHA256 algorithm implementation\n" +
                "Example:   q:sha256 secretpassword";
    }

    @Override
    public Result execute(String sender, String[] params) {
        String value = Hashing.sha256().hashString(params[0], StandardCharsets.UTF_8).toString();
        return new Result(Status.OK, sender, value);
    }
}