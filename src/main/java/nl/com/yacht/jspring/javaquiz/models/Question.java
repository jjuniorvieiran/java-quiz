package nl.com.yacht.jspring.javaquiz.models;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

public class Question {

	ObjectId id;

	@NotNull(message = "Question cannot be empty")
	String question;

	@NotNull(message = "Answer 1 cannot be empty")
	String answer1;

	@NotNull(message = "Answer 2 cannot be empty")
	String answer2;

	@NotNull(message = "Answer 3 cannot be empty")
	String answer3;

	@NotNull(message = "Answer 4 cannot be empty")
	String answer4;

	String answer5;
	
	@NotNull(message = "Please select the correct answer")
	String answerCorrect;
	
	@NotNull(message = "Quiz type cannot be empty")
	String quizType;

	boolean checkAnswer1;
	boolean checkAnswer2;
	boolean checkAnswer3;
	boolean checkAnswer4;
	boolean checkAnswer5;

	public boolean isCheckAnswer1() {
		return checkAnswer1;
	}

	public void setCheckAnswer1(boolean checkAnswer1) {
		this.checkAnswer1 = checkAnswer1;
	}

	public boolean isCheckAnswer2() {
		return checkAnswer2;
	}

	public void setCheckAnswer2(boolean checkAnswer2) {
		this.checkAnswer2 = checkAnswer2;
	}

	public boolean isCheckAnswer3() {
		return checkAnswer3;
	}

	public void setCheckAnswer3(boolean checkAnswer3) {
		this.checkAnswer3 = checkAnswer3;
	}

	public boolean isCheckAnswer4() {
		return checkAnswer4;
	}

	public void setCheckAnswer4(boolean checkAnswer4) {
		this.checkAnswer4 = checkAnswer4;
	}

	public boolean isCheckAnswer5() {
		return checkAnswer5;
	}

	public void setCheckAnswer5(boolean checkAnswer5) {
		this.checkAnswer5 = checkAnswer5;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	public String getAnswerCorrect() {
		return answerCorrect;
	}

	public void setAnswerCorrect(String answerCorrect) {
		this.answerCorrect = answerCorrect;
	}

	public String getQuizType() {
		return quizType;
	}

	public void setQuizType(String quizType) {
		this.quizType = quizType;
	}

	public Question criaId() {
		setId(new ObjectId());
		return this;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + ", answer1=" + answer1 + ", answer2=" + answer2
				+ ", answer3=" + answer3 + ", answer4=" + answer4 + ", answer5=" + answer5 + ", answerCorrect="
				+ answerCorrect + ", quizType=" + quizType + ", checkAnswer1=" + checkAnswer1 + ", checkAnswer2="
				+ checkAnswer2 + ", checkAnswer3=" + checkAnswer3 + ", checkAnswer4=" + checkAnswer4 + ", checkAnswer5="
				+ checkAnswer5 + "]";
	}

}
