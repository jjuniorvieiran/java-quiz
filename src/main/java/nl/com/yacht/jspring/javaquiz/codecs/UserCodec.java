package nl.com.yacht.jspring.javaquiz.codecs;


import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import nl.com.yacht.jspring.javaquiz.models.User;

public class UserCodec implements CollectibleCodec<User> {

	private Codec<Document> codec;

	public UserCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, User user, EncoderContext encoder) {
		
		ObjectId id = user.getId();
		String name = user.getName();
		String surName = user.getSurName();
		String email = user.getEmail();
		String phone = user.getPhone();
		String company = user.getCompany();
		String quizType = user.getQuizType();
		String rightAnswer = user.getRightAnswer();
		Date startDateTime = user.getStartDateTime();
		Date endDateTime = user.getEndDateTime();
		Integer userAnswerCorrect = user.getUserAnswerCorrect();
		Integer userAnswers = user.getUserAnswers();
		Long timeTest = user.getTimeTest();
		String timeDisplayGrid = user.getTimeDisplayGrid();
		
		Document document = new Document();
		document.put("_id", id);
		document.put("name", name);
		document.put("surName", surName);
		document.put("email", email);
		document.put("phone", phone);
		document.put("company", company);
		document.put("quizType", quizType);
		document.put("rightAnswer", rightAnswer);
		document.put("startDateTime", startDateTime);
		document.put("endDateTime", endDateTime);
		document.put("userAnswerCorrect", userAnswerCorrect);
		document.put("userAnswers", userAnswers);
		document.put("timeTest", timeTest);
		document.put("timeDisplayGrid", timeDisplayGrid);

		codec.encode(writer, document, encoder);
	}

	@Override
	public Class<User> getEncoderClass() {
		return User.class;
	}

	@Override
	public User decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		User user = new User();

		user.setId(document.getObjectId("_id"));
		user.setName(document.getString("name"));
		user.setSurName(document.getString("surName"));
		user.setEmail(document.getString("email"));
		user.setPhone(document.getString("phone"));
		user.setCompany(document.getString("company"));
		user.setQuizType(document.getString("quizType"));
		user.setRightAnswer(document.getString("rightAnswer"));
		user.setStartDateTime(document.getDate("startDateTime"));
		user.setEndDateTime(document.getDate("endDateTime"));
		user.setUserAnswerCorrect(document.getInteger("userAnswerCorrect"));
		user.setUserAnswers(document.getInteger("userAnswers"));
		user.setTimeTest(document.getLong("timeTest"));
		user.setTimeDisplayGrid(document.getString("timeDisplayGrid"));
		
		return user;
	}

	@Override
	public boolean documentHasId(User user) {
		return user.getId() == null;
	}

	@Override
	public User generateIdIfAbsentFromDocument(User user) {
		return documentHasId(user) ? user.criaId() : user;
	}

	@Override
	public BsonValue getDocumentId(User user) {
		if (!documentHasId(user)) {
			throw new IllegalStateException("This document havent id");
		}
		return new BsonString(user.getId().toHexString());
	}
}
