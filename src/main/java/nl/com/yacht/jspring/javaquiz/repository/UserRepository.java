package nl.com.yacht.jspring.javaquiz.repository;

import  static nl.com.yacht.jspring.javaquiz.utils.QuizUtils.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
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
import com.mongodb.operation.OrderBy;

import nl.com.yacht.jspring.javaquiz.codecs.UserCodec;
import nl.com.yacht.jspring.javaquiz.models.User;

@Repository
public class UserRepository {
	
	private MongoClient client;
	MongoDatabase db;
	
	@Autowired
	private Environment env;

	private MongoDatabase createConnection() {
		
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		UserCodec userCodec = new UserCodec(codec);
		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),	CodecRegistries.fromCodecs(userCodec));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();
		
		client = new MongoClient(env.getProperty("mongo.host"), options);
		db = client.getDatabase(env.getProperty("mongo.database"));
		
		return db;
	}

	public void save(User user) {
		
		createConnection();
		
		MongoCollection<User> users = this.db.getCollection("users", User.class);
		if(user.getId() == null){
			user.setStartDateTime(new Date());
			users.insertOne(user);
		} else {
			user.setEndDateTime(new Date());
			handleTime(user);
			users.updateOne(Filters.eq("_id", user.getId()), new Document("$set", user));
		}
		client.close();
	}
	
	public List<User> getAll() {
		
		createConnection();
		MongoCollection<User> users = db.getCollection("users", User.class);
		MongoCursor<User> resultIt = users.find()
				.sort(new BasicDBObject("userAnswerCorrect", OrderBy.DESC.getIntRepresentation())
						.append("timeTest", OrderBy.ASC.getIntRepresentation()))
				.iterator();

		List<User> usersFound = populateUser(resultIt);
		
		client.close();

		return usersFound;		
	}
	
	public List<User> findUserByQuizType(String quizType) {
		
		createConnection();
		MongoCollection<User> users = db.getCollection("users", User.class);
		
		MongoCursor<User> resultIt = users.find(Filters.eq("quizType", quizType), User.class)
				.sort(new BasicDBObject("userAnswerCorrect", OrderBy.DESC.getIntRepresentation())
						.append("timeTest", OrderBy.ASC.getIntRepresentation()))
				.iterator();

		List<User> usersFound = populateUser(resultIt);
		
		client.close();

		return usersFound;		
	}
	
	public List<User> findBy(String email) {
		
		createConnection();
		MongoCollection<User> users = db.getCollection("users", User.class);
		MongoCursor<User> resultIt = users.find(Filters.eq("email", email), User.class).iterator();
		
		List<User> usersFound = populateUser(resultIt);

		client.close();
		
		return usersFound;
	}
	
	private List<User> populateUser(MongoCursor<User> resultIt) {
		
		List<User> usersFound = new ArrayList<>();
		while (resultIt.hasNext()) {
			User user = resultIt.next();
			usersFound.add(user);
		}
		
		client.close();

		return usersFound;
	}
}
