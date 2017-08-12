package sk.mizik.quentin.commands;

import com.google.common.io.BaseEncoding;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Marian Mizik
 * @see sk.mizik.quentin.commands.Command
 * @since 1.0.0
 */
@ApplicationScoped
public class Base64DecodeCommand implements Command {

    @Override
    public String getName() {
        return "base64decode";
    }

    @Override
    public String getDescription() {
        return "base64decode        decode provided string using base64";
    }

    @Override
    public String getManual() {
        return "\nDecode base64 encoded string using Google Guava implementation.\n" +
                "Example:   q:base64decode amFob2RhOm1hbGluYQ==";
    }

    @Override
    public Result execute(String sender, String[] params) {
        String value = new String(BaseEncoding.base64().decode(String.join(" ", params)));
        return new Result(Status.OK, sender, value);
    }
}