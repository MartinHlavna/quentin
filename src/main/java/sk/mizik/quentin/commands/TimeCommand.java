package sk.mizik.quentin.commands;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class TimeCommand implements Command {

    @Override
    public String getName() {
        return "time";
    }

    @Override
    public String getDescription() {
        return "time                print current time";
    }

    @Override
    public String getManual() {
        return "\nPrint current time using Java8 date time api and Europe/Bratislava timezone.\n" +
                "Example:   q:timestamp";
    }

    @Override
    public Result execute(String sender, String[] params) {
        String value = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return new Result(Status.OK, sender, value);
    }
}