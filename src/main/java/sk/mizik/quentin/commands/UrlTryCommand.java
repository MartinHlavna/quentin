package sk.mizik.quentin.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class UrlTryCommand implements Command {

    private Logger LOG = LoggerFactory.getLogger(UrlTryCommand.class);

    @Override
    public String getName() {
        return "urltry";
    }

    @Override
    public String getDescription() {
        return "urltry              execute URL and get the response code";
    }

    @Override
    public String getManual() {
        return "\nExecute http request using provided http method as first parameter and url as second param. " +
                "Request is implemented using java.net.HttpUrlConnection API.\n" +
                "Example:   q:urltry GET https://4q.eu";
    }

    @Override
    public Result execute(String sender, String[] params) {
        try {
            URL obj = new URL(String.join(" ", Arrays.copyOfRange(params, 1, params.length)));
            HttpURLConnection c = (HttpURLConnection) obj.openConnection();
            c.setRequestMethod(params[0]);
            String value = String.valueOf(c.getResponseCode()) + " " + c.getResponseMessage();
            return new Result(Status.OK, sender, value);
        } catch (ProtocolException e) {
            LOG.error(e.getMessage(), e);
            String value = "I tried hard, but an ProtocolException occurred. Message: " + e.getMessage();
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, value);
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
            String value = "I tried hard, but an MalformedURLException occurred. Message: " + e.getMessage();
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, value);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            String value = "I tried hard, but an IOException occurred. Message: " + e.getMessage();
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, value);
        }
    }
}