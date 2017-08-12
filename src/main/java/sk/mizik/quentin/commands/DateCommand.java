package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class DateCommand implements Command {

    @Override
    public String getName() {
        return "date";
    }

    @Override
    public String getDescription() {
        return "date                print current date";
    }

    @Override
    public String getManual() {
        return "\nPrint current date using Java8 date time api and Europe/Bratislava timezone\n" +
                "Example:   q:date";
    }

    @Override
    public Result execute(String sender, String[] params) {
        String value = LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return new Result(Status.OK, sender, value);
    }
}