package sk.mizik.quentin.commands;

import sk.mizik.quentin.dao.QADao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;

/**
 * Manageable list of Q&As
 *
 * @author Martin Krupa
 * @author Martin Hlavňa
 * @see Command
 * @since 1.1.5
 */
@ApplicationScoped
public class QACommand implements Command {

	@Inject
	private QADao dao;

	@Override
	public String getName() {
		return "qa";
	}

	@Override
	public String getDescription() {
		return "qa                 Manageable list of Q&As";
	}

	@Override
	public String getManual() {
		return "\nManageable list of Q&As\n" +
				"Example1:   q:qa add Question? Answer\n" +
				"Example2:   q:qa search searchString\n" +
				"Example3:   q:qa delete number";
	}

	@Override
	public Result execute(String sender, String[] params) {
		if (params.length < 1) {
			return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Not enough parameters!");
		}
		switch (params[0]) {
			case "add":
				String[] parameters = String.join(" ", Arrays.copyOfRange(params, 1, params.length)).split("\\? ");
				if (parameters.length < 2) {
					return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Not enough parameters!");
				}
				try {
					dao.add(parameters[0], parameters[1]);
				} catch (RuntimeException e) { //TODO
					return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Internal error!");
				}

				return new Result(Status.OK, sender, "Added successfully");
			case "search":
				String searchString = String.join(" ", Arrays.copyOfRange(params, 1, params.length)).trim();
				if (params.length != 2) {
					return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Not enough parameters!");
				}
				String answer = dao.search(searchString).stream()
						.map(qaa -> "\nQ-" + qaa.getId() + ": " + qaa.getQuestion() + "\n" + "A: " + qaa.getAnswer() + "\n")
						.reduce("", String::concat);

				return new Result(Status.OK, sender, answer.isEmpty() ? "Nothing found" : answer);
			case "delete":
				if (params.length != 2) {
					return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Not enough parameters!");
				}
				Integer id;
				try {
					id = Integer.parseInt(params[1]);
				} catch (NumberFormatException e) {
					return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Second parameter must be number");
				}
				try {
					dao.delete(id);
				} catch (RuntimeException ex) {
					return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Internal error!");
				}
				return new Result(Status.OK, sender, "Removed successfully");
			default:
				return new Result(Status.COMMAND_INTERNAL_ERROR, sender, "Unrecognized Q&A operation: " + params[0]);
		}
	}

}