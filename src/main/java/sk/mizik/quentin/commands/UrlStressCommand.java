package sk.mizik.quentin.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.1.0
 */
@ApplicationScoped
public class UrlStressCommand implements Command {

    private Logger LOG = LoggerFactory.getLogger(UrlStressCommand.class);

    @Inject
    private Event<Result> resultDistributor;

    @Override
    public String getName() {
        return "urlstress";
    }

    @Override
    public String getManual() {
        return "\nExecute http request in loop using provided http method as first parameter, " +
                "url as second and number of iterations as third argument. " +
                "Request is implemented using java.net.HttpUrlConnection API. " +
                "Command is asynchronous in its nature. Quentin will immediately acknowledge the request " +
                "and later he will inform about results in separate private message.\n" +
                "Example:   q:urlstress 100 GET https://4q.eu";
    }

    @Override
    public String getDescription() {
        return "urlstress           execute http request in loop using given count, method and url";
    }

    @Override
    public Result execute(String sender, String[] params) {
        new Thread(() -> {
            int success = 0;
            int error = 0;
            for (int i = 0; i < Integer.parseInt(params[0]); i++) {
                try {
                    URL obj = new URL(String.join(" ", Arrays.copyOfRange(params, 2, params.length)));
                    HttpURLConnection c = (HttpURLConnection) obj.openConnection();
                    c.setRequestMethod(params[1].toUpperCase());
                    if (c.getResponseCode() < 400) {
                        success++;
                    } else {
                        error++;
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                    error++;
                }
            }
            String value = "Stress test done. Requests: " + params[2] + " Success: " + success + " Error: " + error;
            resultDistributor.fire(new Result(Status.OK, sender, value));
        }).start();
        return new Result(Status.OK, sender, "I am on it! Starting stress test for your URL.");
    }
}