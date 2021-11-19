package nl.com.yacht.jspring.javaquiz.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import nl.com.yacht.jspring.javaquiz.models.Admin;
import nl.com.yacht.jspring.javaquiz.repository.AdminRepository;

@Component
public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = repository.findByUsername(username);

		if (admin == null) {
			throw new UsernameNotFoundException("User not found");
		}

		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

		return new User(admin.getUsername(), admin.getPassword(), authorities);
	}
}