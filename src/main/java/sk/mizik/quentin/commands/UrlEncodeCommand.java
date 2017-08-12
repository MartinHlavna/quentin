package sk.mizik.quentin.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.mizik.quentin.Constants;

import javax.enterprise.context.ApplicationScoped;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class UrlEncodeCommand implements Command {

    private Logger LOG = LoggerFactory.getLogger(UrlEncodeCommand.class);

    @Override
    public String getName() {
        return "urlencode";
    }

    @Override
    public String getDescription() {
        return "urlencode           url encode provided string";
    }

    @Override
    public String getManual() {
        return "\nEncode plain text string using java.net.URLEncoder API.\n" +
                "Example:   q:urlencode https://4q.eu/";
    }

    @Override
    public Result execute(String sender, String[] params) {
        try {
            String value = URLEncoder.encode(String.join(" ", params), Constants.UTF8);
            return new Result(Status.OK, sender, value);
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            String value = "I tried hard, but look like UTF8 is not supported encoding by my underlying JVM";
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, value);
        }
    }
}