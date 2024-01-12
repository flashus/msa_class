package ru.idyachenko.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.repository.UserRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}
	@Bean
	CommandLineRunner demoJpa(UserRepository repository) {
		return (args) -> {
//			    public User(String fname, String lname, String mname, String avatar_url, String nickname, String email) {

			User vasya = new User("Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
			User petya = new User("Petya", "Ivanoff", "Ivanovich", "http://", "petya", "petya@mail.com");

			repository.save(vasya);
			repository.save(petya);

//			repository.deleteById(vasya.getId());

			List<User> petrovList = repository.findByLname("Petrov");

//			for (User user: repository.findAll()) {
			for (User user: petrovList) {
				System.out.println(user);
			};
		};
	}
}
