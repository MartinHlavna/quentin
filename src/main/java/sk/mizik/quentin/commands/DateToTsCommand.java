package sk.mizik.quentin.commands;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Convert date and time in well known text format to unix timestamp using Java 8 date time api.
 *
 * @author Marian Mizik
 * @see Command
 * @since 1.0.0
 */
@ApplicationScoped
public class DateToTsCommand implements Command {

	private DateTimeFormatter[] formatters = {
			DateTimeFormatter.BASIC_ISO_DATE,
			DateTimeFormatter.ISO_DATE,
			DateTimeFormatter.ISO_DATE_TIME,
			DateTimeFormatter.ISO_TIME,
			DateTimeFormatter.ISO_INSTANT,
			DateTimeFormatter.ISO_LOCAL_DATE,
			DateTimeFormatter.ISO_LOCAL_DATE_TIME,
			DateTimeFormatter.ISO_LOCAL_TIME,
			DateTimeFormatter.ISO_OFFSET_DATE,
			DateTimeFormatter.ISO_OFFSET_DATE_TIME,
			DateTimeFormatter.ISO_OFFSET_TIME,
			DateTimeFormatter.ISO_ORDINAL_DATE,
			DateTimeFormatter.ISO_ZONED_DATE_TIME,
			DateTimeFormatter.RFC_1123_DATE_TIME,
			DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"),
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
	};

	@Override
	public String getName() {
		return "date2ts";
	}

	@Override
	public String getDescription() {
		return "date2ts             convert readable date & time to timestamp";
	}

	@Override
	public String getManual() {
		return "\nConvert date and time in well known text format to unix timestamp using Java 8 date time api. " +
				"Implementation is using Europe/Bratislava timezone.\n" +
				"Example:   q:date2ts 2017-08-08T13:00:58";
	}

	@Override
	public Result execute(String sender, String[] params) {
		LocalDateTime dateTime = null;
		for (DateTimeFormatter formatter : formatters) {
			try {
				dateTime = LocalDateTime.parse(String.join(" ", params), formatter);
				break;
			} catch (DateTimeParseException e) {
				//DO NOTHING
			}
		}
		if (dateTime != null) {
			long value = Instant.from(ZonedDateTime.of(dateTime, ZoneId.systemDefault())).getEpochSecond();
			return new Result(Status.OK, sender, String.valueOf(value));
		} else {
			String value = "I have tried " + formatters.length + " different formatters, but all of them failed. " +
					"Maybe you can try something more common. What do you say?";
			return new Result(Status.COMMAND_INTERNAL_ERROR, sender, value);
		}
	}
}