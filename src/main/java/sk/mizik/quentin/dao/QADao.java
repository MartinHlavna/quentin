package sk.mizik.quentin.dao;

import sk.mizik.quentin.commands.QandA;

import java.util.List;

public interface QADao {
	void add(String question, String answer);

	void delete(Integer id);

	List<QandA> search(String searchString);
}
