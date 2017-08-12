package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.1.0
 */
@ApplicationScoped
public class ReminderCommand implements Command {

    @Inject
    private Event<Result> resultDistributor;

    @Override
    public String getName() {
        return "remindme";
    }

    @Override
    public String getDescription() {
        return "remindme            set reminder. first param is time in seconds. second is text to be reminded";
    }

    @Override
    public String getManual() {
        return "\nSchedule reminder using number of seconds as a first parameter " +
                "and optional custom message as a second argument. " +
                "Request is implemented using java.util.Timer API. " +
                "Command is asynchronous in its nature. Quentin will immediately acknowledge the request " +
                "and later he will trigger the reminder as a separate private message.\n" +
                "Example:   q:remindme 180 remove that tea bag";
    }

    @Override
    public Result execute(String sender, String[] params) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String message = String.join(" ", Arrays.copyOfRange(params, 1, params.length));
                String value = "You wanted me to remind you to: " + (params[1] == null ? "Whatever it was" : message);
                resultDistributor.fire(new Result(Status.OK, sender, value));
            }
        }, Integer.parseInt(params[0]) * 1000);
        return new Result(Status.OK, sender, "Of course. I won't forget");
    }
}