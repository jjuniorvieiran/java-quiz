
package nl.com.yacht.jspring.javaquiz.models;

import java.util.Date;

import javax.validation.constraints.Size;

import org.bson.types.ObjectId;

public class User {

	private ObjectId id;
	
    @Size(min=3, max=30, message="Nickname must have at least 3 characters")
	private String name;
	
	private String surName;
	
	@Size(min=8, max=50, message="E-mail must have at least 8 characters")
	private String email;
	private String phone;
	private String company;
	private String quizType;
	private String rightAnswer;
	private Date startDateTime;
	private Date endDateTime;
	private Integer userAnswerCorrect = 0;
	private Integer userAnswers = 0 ;
	private Long timeTest;
	private String timeDisplayGrid;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public User criaId() {
		setId(new ObjectId());
		return this;
	}

	public Integer getUserAnswerCorrect() {
		return userAnswerCorrect;
	}

	public void setUserAnswerCorrect(Integer userAnswerCorrect) {
		this.userAnswerCorrect = userAnswerCorrect;
	}

	public Integer getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(Integer userAnswers) {
		this.userAnswers = userAnswers;
	}

	public Long getTimeTest() {
		return timeTest;
	}

	public void setTimeTest(Long timeTest) {
		this.timeTest = timeTest;
	}

	public String getTimeDisplayGrid() {
		return timeDisplayGrid;
	}

	public void setTimeDisplayGrid(String timeDisplayGrid) {
		this.timeDisplayGrid = timeDisplayGrid;
	}
	
	public String getQuizType() {
		return quizType;
	}

	public void setQuizType(String quizType) {
		this.quizType = quizType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surName=" + surName + ", email=" + email + ", phone=" + phone
				+ ", company=" + company + ", quizType=" + quizType + ", rightAnswer=" + rightAnswer + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", userAnswerCorrect=" + userAnswerCorrect + ", userAnswers="
				+ userAnswers + ", timeTest=" + timeTest + ", timeDisplayGrid=" + timeDisplayGrid + ", quizType="
				+ quizType + "]";
	}
}
