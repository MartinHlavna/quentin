package sk.mizik.quentin.dao;

import sk.mizik.quentin.commands.QandA;

import java.util.List;

/**
 * Interface for Dao used by QA system
 *
 * @author Martin Krupa
 * @author Martin Hlavňa
 */
public interface QADao {
	/**
	 * Add new QA pair
	 *
	 *
	 * @param question Question part of QA record
	 * @param answer Answer part of WA record
	 */
	void add(String question, String answer);

	/**
	 * Delete QA record by id
	 * @param id Id of record
	 */
	void delete(Integer id);

	/**
	 * Search all QA records for search string.
	 *
	 * Searches both in question and answer
	 * @param searchString String to search
	 * @return Matching records
	 */
	List<QandA> search(String searchString);
}
