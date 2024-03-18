package ru.idyachenko.users;

// import java.sql.Date;
// import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import ru.idyachenko.users.entity.City;
import ru.idyachenko.users.entity.Gender;
import ru.idyachenko.users.entity.Skill;
import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.repository.CityRepository;
import ru.idyachenko.users.repository.SkillRepository;
import ru.idyachenko.users.repository.SubscriptionRepository;
import ru.idyachenko.users.repository.UserRepository;
import ru.idyachenko.users.repository.UserSkillRepository;
// import ru.idyachenko.users.service.SkillService;
import ru.idyachenko.users.service.SubscriptionService;
import ru.idyachenko.users.service.UserSkillService;

@SpringBootApplication
public class UsersApplication {

    @Autowired
    BuildProperties buildProperties;

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

            User vasya =
                    new User("Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
            vasya.setGender(Gender.MALE);
            User petya =
                    new User("Petya", "Ivanoff", "Ivanovich", "http://", "petya", "petya@mail.com");
            petya.setGender(Gender.MALE);
            User anna =
                    new User("Anna", "Smirnova", "Petrovna", "http://", "anya", "anya@mail.com");
            anna.setGender(Gender.FEMALE);

            City yuzhno = new City("Yuzhno-Sakhalinsk");
            City msk = new City("Moscow");

            cityRepository.save(yuzhno);
            cityRepository.save(msk);

            vasya.setCity(yuzhno);
            petya.setCity(msk);
            anna.setCity(msk);

            userRepository.save(vasya);
            userRepository.save(petya);
            userRepository.save(anna);

            Skill cpp = new Skill("C++", "C++ lang");
            Skill java = new Skill("Java", "Java lang");
            skillRepository.save(cpp);
            skillRepository.save(java);

            UserSkill vasyaCpp = new UserSkill(vasya, cpp);
            UserSkill vasyaJava = new UserSkill(vasya, java);
            UserSkill petyaJava = new UserSkill(petya, java);
            UserSkill annaCpp = new UserSkill(anna, cpp);

            userSkillRepository.save(vasyaCpp);
            userSkillRepository.save(vasyaJava);
            userSkillRepository.save(petyaJava);
            userSkillRepository.save(annaCpp);

            Subscription vasyaPetya = new Subscription(vasya, petya);
            subscriptionRepository.save(vasyaPetya);
            // repository.deleteById(vasya.getId());

            List<User> petrovList = userRepository.findByLname("Petrov");

            UserSkillService userSkillService = new UserSkillService(userSkillRepository);
            SubscriptionService subscriptionService =
                    new SubscriptionService(subscriptionRepository);
            // for (User user: repository.findAll()) {
            for (User user : petrovList) {
                System.out.println("User: " + user);
                System.out.println("User skills: " + userSkillService.getUserSkills(vasya.getId()));
                System.out.println("User subscriptions: "
                        + subscriptionService.getUserSubscriptions(vasya.getId()));
            }
            System.out.println(
                    "====================================================================================");
            System.out.println(
                    "====================================================================================");
            System.out.println("Application properties:");

            System.out.println("Name: " + buildProperties.getName());
            System.out.println("Version: " + buildProperties.getVersion());
            System.out.println("Build Time: " + buildProperties.getTime());
            System.out.println("Artifact: " + buildProperties.getArtifact());
            System.out.println("Group: " + buildProperties.getGroup());
            System.out.println(
                    "====================================================================================");

        };
    }
}
