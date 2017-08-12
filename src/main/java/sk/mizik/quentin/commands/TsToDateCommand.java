package sk.mizik.quentin.commands;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
import com.google.common.base.Strings;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.ZoneId;

@ApplicationScoped
public class TsToDateCommand implements Command {

    @Override
    public String getName() {
        return "ts2date";
    }

    @Override
    public String getDescription() {
        return "ts2date             convert timestamp to readable date & time";
    }

    @Override
    public String getManual() {
        return "\nConvert unix timestamp to date and time in well known text format using Java 8 date time api. " +
                "It is possible to use both second and millisecond unix timestamp representation. " +
                "Implementation is using Europe/Bratislava timezone\n" +
                "Example:   q:ts2date 1502190058";
    }

    @Override
    public Result execute(String sender, String[] params) {
        long timestamp = Long.parseLong(Strings.padEnd(params[0], 13, '0'));
        String value = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime().toString();
        return new Result(Status.OK, sender, value);
    }
}