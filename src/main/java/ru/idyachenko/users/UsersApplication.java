package ru.idyachenko.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.idyachenko.users.entity.*;
import ru.idyachenko.users.repository.*;
// import ru.idyachenko.users.service.SkillService;
import ru.idyachenko.users.service.SubscriptionService;
import ru.idyachenko.users.service.UserSkillService;

// import java.sql.Date;
// import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Bean
	CommandLineRunner demoJpa(UserRepository userRepository, CityRepository cityRepository,
			SkillRepository skillRepository, SubscriptionRepository subscriptionRepository,
			UserSkillRepository userSkillRepository) {
		return (args) -> {
			// public User(String fname, String lname, String mname, String avatar_url,
			// String nickname, String email) {

			User vasya = new User("Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
			User petya = new User("Petya", "Ivanoff", "Ivanovich", "http://", "petya", "petya@mail.com");

			City yuzhno = new City("Yuzhno-Sakhalinsk");
			City msk = new City("Moscow");

			cityRepository.save(yuzhno);
			cityRepository.save(msk);

			vasya.setCity(yuzhno);
			petya.setCity(msk);

			userRepository.save(vasya);
			userRepository.save(petya);

			Skill cpp = new Skill("C++", "C++ lang");
			Skill java = new Skill("Java", "Java lang");
			skillRepository.save(cpp);
			skillRepository.save(java);

			UserSkill vasya_cpp = new UserSkill(vasya, cpp);
			UserSkill vasya_java = new UserSkill(vasya, java);
			UserSkill petya_java = new UserSkill(petya, java);
			userSkillRepository.save(vasya_cpp);
			userSkillRepository.save(vasya_java);
			userSkillRepository.save(petya_java);

			Subscription vasya_petya = new Subscription(vasya, petya);
			subscriptionRepository.save(vasya_petya);
			// repository.deleteById(vasya.getId());

			List<User> petrovList = userRepository.findByLname("Petrov");

			UserSkillService userSkillService = new UserSkillService(userSkillRepository);
			SubscriptionService subscriptionService = new SubscriptionService(subscriptionRepository);
			// for (User user: repository.findAll()) {
			for (User user : petrovList) {
				System.out.println("User: " + user);
				System.out.println("User skills: " + userSkillService.getUserSkills(vasya.getId()));
				System.out.println("User subscriptions: " + subscriptionService.getUserSubscriptions(vasya.getId()));
			}
			;

		};
	}
}
