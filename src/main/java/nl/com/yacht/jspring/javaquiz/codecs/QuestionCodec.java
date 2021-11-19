package nl.com.yacht.jspring.javaquiz.codecs;


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

import nl.com.yacht.jspring.javaquiz.models.Question;

public class QuestionCodec implements CollectibleCodec<Question> {

	private Codec<Document> codec;

	public QuestionCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Question question, EncoderContext encoder) {
		
		ObjectId id = question.getId();
		String question1 = question.getQuestion();
		String answer1 = question.getAnswer1();
		String answer2 = question.getAnswer2();
		String answer3 = question.getAnswer3();
		String answer4 = question.getAnswer4();
		String answer5 = question.getAnswer5();
		String quizType = question.getQuizType();
		String answerCorrect = question.getAnswerCorrect();
		
		Document document = new Document();
		document.put("_id", id);
		document.put("question", question1);
		document.put("answer1", answer1);
		document.put("answer2", answer2);
		document.put("answer3", answer3);
		document.put("answer4", answer4);
		document.put("answer5", answer5);
		document.put("quizType", quizType);
		document.put("answerCorrect", answerCorrect);

		codec.encode(writer, document, encoder);
	}

	@Override
	public Class<Question> getEncoderClass() {
		return Question.class;
	}

	@Override
	public Question decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		Question question = new Question();

		question.setId(document.getObjectId("_id"));
		question.setQuestion(document.getString("question"));
		question.setAnswer1(document.getString("answer1"));
		question.setAnswer2(document.getString("answer2"));
		question.setAnswer3(document.getString("answer3"));
		question.setAnswer4(document.getString("answer4"));
		question.setAnswer5(document.getString("answer5"));
		question.setAnswerCorrect(document.getString("answerCorrect"));
		question.setQuizType(document.getString("quizType"));
		
		return question;
	}

	@Override
	public boolean documentHasId(Question question) {
		return question.getId() == null;
	}

	@Override
	public Question generateIdIfAbsentFromDocument(Question question) {
		return documentHasId(question) ? question.criaId() : question;
	}

	@Override
	public BsonValue getDocumentId(Question question) {
		if (!documentHasId(question)) {
			throw new IllegalStateException("This document havent id");
		}
		return new BsonString(question.getId().toHexString());
	}
}
