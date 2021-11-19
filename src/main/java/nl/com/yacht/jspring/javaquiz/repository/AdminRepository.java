package nl.com.yacht.jspring.javaquiz.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nl.com.yacht.jspring.javaquiz.models.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {

	Admin findByUsername(String username);
}
