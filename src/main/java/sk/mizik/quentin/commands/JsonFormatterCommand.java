package sk.mizik.quentin.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Martin Krupa
 * @see Command
 * @since 1.1.5
 */
@ApplicationScoped
public class JsonFormatterCommand implements Command {

    private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public String getName() {
        return "formatjson";
    }

    @Override
    public String getDescription() {
        return "formatjson                Formats json";
    }

    @Override
    public String getManual() {
        return "\nPretty-prints json input\n" +
                "Example:   q:formatjson {\"id\":1, \"name\": \"Hodor\", \"workplace\": \"door\"}";
    }

    @Override
    public Result execute(String sender, String[] params) {
        if (params.length < 1) {
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Insufficient parameters.");
        }
        final String jsonString = String.join(" ", params).trim();
        try {
            final Object parsed = mapper.readValue(jsonString, Object.class);
            final String formatted = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parsed);
            return new Result(Status.OK, sender, "\n" + formatted);
        } catch (IOException e) {
            return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Problem occurred while parsing provided JSON");
        }
    }
}