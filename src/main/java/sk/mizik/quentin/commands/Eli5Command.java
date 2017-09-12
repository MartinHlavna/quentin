package sk.mizik.quentin.commands;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Martin Krupa
 * @see Command
 * @since 1.1.5
 */
@ApplicationScoped
public class Eli5Command implements Command {

    private Properties props = new Properties();

    @Override
    public String getName() {
        return "eli5";
    }

    @Override
    public String getDescription() {
        return "eli5                Internet slang acronyms explained";
    }

    @Override
    public String getManual() {
        return "\nExplains internet slang acronyms\n" +
                "Example:   q:eli5 eli5";
    }

    @PostConstruct
    public void init() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("eli5.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result execute(String sender, String[] params) {
        if (params.length < 1) {
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Insufficient parameters.");
        }
        final String acronym = params[0].toLowerCase().trim();
        return new Result(Status.OK, sender, props.containsKey(acronym) ? props.getProperty(acronym) : "Unknown acronym");
    }
}