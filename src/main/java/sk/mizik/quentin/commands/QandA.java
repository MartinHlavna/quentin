package sk.mizik.quentin.commands;


/**
 * Question and answer tuple
 *
 * @author Martin Krupa
 * @author Martin Hlavňa
 */
public class QandA {
	private Integer id;
	private String question;
	private String answer;

	/**
	 * Full constructor
	 * @param id Id record
	 * @param question Question
	 * @param answer Answer
	 */
	public QandA(Integer id, String question, String answer) {
		this.id = id;
		this.question = question;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
