package sk.mizik.quentin.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.mizik.quentin.commands.QandA;
import sk.mizik.quentin.exceptions.QAException;
import sk.mizik.quentin.service.FileService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * File implementation of  {@link QADao}
 *
 * @author Martin Krupa
 * @author Martin Hlavňa
 */
@ApplicationScoped
public class QADaoFile implements QADao {
	private List<QandA> data;

	private static final String FILENAME = "qaa.txt";

	private final Logger LOG = LoggerFactory.getLogger(QADaoFile.class);

	/**
	 * Initialize database. Called automatically upon bean creation
	 */
	@PostConstruct
	public void init() {
		data = new LinkedList<>();

		File file = FileService.open(FILENAME);
		if (!file.exists()) {
			return;
		}

		try {
			Integer number = null;
			String question = null;

			List<String> lines = FileService.readAsLines(file);
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).isEmpty()) {
					continue;
				}
				int modulo = i % 3;
				switch (modulo) {
					case 0:
						number = Integer.parseInt(lines.get(i));
						break;
					case 1:
						question = lines.get(i);
						break;
					case 2:
						data.add(new QandA(number, question, lines.get(i)));
						break;
				}
			}

		} catch (RuntimeException | IOException e) {
			//NOTE: Catching runtime exception because any exception thrown from here would have break startup
			LOG.error(e.getMessage(), e);
		}
	}

	private void save() throws IOException {
		File file = FileService.open(FILENAME);

		List<String> lines = data.stream()
				.map(qaa -> qaa.getId() + System.lineSeparator() + qaa.getQuestion() + System.lineSeparator() + qaa.getAnswer())
				.collect(Collectors.toList());

		FileService.writeLines(file, lines);
	}

	@Override
	public void add(String question, String answer) {
		Optional<QandA> max = data.stream().max(Comparator.comparing(QandA::getId));
		Integer newId = 1;
		if (max.isPresent()) {
			newId = max.get().getId() + 1;
		}
		data.add(new QandA(newId, question + "?", answer));
		try {
			save();
		} catch (IOException e) {
			throw new QAException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		QandA found = null;
		for (QandA qaa : data) {
			if (qaa.getId().equals(id)) {
				found = qaa;
			}
		}
		data.remove(found);

		try {
			save();
		} catch (IOException e) {
			throw new QAException(e);
		}
	}

	@Override
	public List<QandA> search(String searchString) {
		return data.stream()
				.filter(qaa -> (qaa.getQuestion() + qaa.getAnswer()).toLowerCase().contains(searchString))
				.collect(Collectors.toList());
	}

}
