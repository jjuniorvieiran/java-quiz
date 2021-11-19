package nl.com.yacht.jspring.javaquiz.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import nl.com.yacht.jspring.javaquiz.codecs.QuestionCodec;
import nl.com.yacht.jspring.javaquiz.models.Question;

@Repository
public class QuizQuestionRepository {

	private MongoClient client;
	MongoDatabase db;

	@Autowired
	private Environment env;

	private MongoDatabase createConnection() {

		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		QuestionCodec questionCodec = new QuestionCodec(codec);
		CodecRegistry registry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(questionCodec));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registry).build();

		client = new MongoClient(env.getProperty("mongo.host"), options);
		db = client.getDatabase(env.getProperty("mongo.database"));
		return db;
	}

	public void save(Question question) {

		createConnection();
		MongoCollection<Question> questions = this.db.getCollection("questions", Question.class);

		if (question.getId() == null) {
			questions.insertOne(question);
		} else {
			questions.updateOne(Filters.eq("_id", question.getId()), new Document("$set", question));
		}

		client.close();
	}

	public List<Question> getAll() {

		createConnection();
		MongoCollection<Question> questions = db.getCollection("questions", Question.class);
		MongoCursor<Question> resultIt = questions.find().sort(new BasicDBObject("quizType", 1)).iterator();

		List<Question> questionsFound = new ArrayList<Question>();
		while (resultIt.hasNext()) {
			Question question = resultIt.next();
			questionsFound.add(question);
		}

		client.close();

		return questionsFound;
	}

	public List<Question> findBy(String quizType) {

		createConnection();

		MongoCollection<Question> questions = db.getCollection("questions", Question.class);
		MongoCursor<Question> resultIt = questions.find(Filters.eq("quizType", quizType)).iterator();

		List<Question> questionsFound = new ArrayList<Question>();
		while (resultIt.hasNext()) {
			Question question = resultIt.next();
			questionsFound.add(question);
		}

		client.close();

		return questionsFound;
	}

	public void delete(ObjectId id) {

		createConnection();

		MongoCollection<Question> questions = db.getCollection("questions", Question.class);
		questions.deleteOne(Filters.eq("_id", id));

		client.close();
	}

	public Question getQuestion(ObjectId id) {

		createConnection();

		MongoCollection<Question> questions = db.getCollection("questions", Question.class);
		Question question = questions.find(Filters.eq("_id", id)).first();

		client.close();

		return question;
	}

	public List<String> getQuizTypes() {

		createConnection();

		MongoCollection<Question> questions = db.getCollection("questions", Question.class);
		MongoCursor<Question> resultIt = questions.find().iterator();

		List<String> quizTypes = new ArrayList<String>();
		while (resultIt.hasNext()) {
			Question question = resultIt.next();
			String quizType = question.getQuizType();
			
			if(!quizTypes.contains(quizType)) {
				quizTypes.add(quizType);
			}
		}
		client.close();

		return quizTypes;
	}

}
