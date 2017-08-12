package sk.mizik.quentin.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.mizik.quentin.Constants;

import javax.enterprise.context.ApplicationScoped;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class UrlDecodeCommand implements Command {

    private Logger LOG = LoggerFactory.getLogger(UrlDecodeCommand.class);

    @Override
    public String getName() {
        return "urldecode";
    }

    @Override
    public String getDescription() {
        return "urldecode           url decode of provided string";
    }

    @Override
    public String getManual() {
        return "\nURL decode encoded string using java.net.URLDecoder API.\n" +
                "Example:   q:urldecode https%3A%2F%2F4q.eu%2F";
    }

    @Override
    public Result execute(String sender, String[] params) {
        try {
            String value = URLDecoder.decode(params[0], Constants.UTF8);
            return new Result(Status.OK, sender, value);
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            String value = "I tried hard, but look like UTF8 is not supported encoding by my underlying JVM";
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, value);
        }
    }
}