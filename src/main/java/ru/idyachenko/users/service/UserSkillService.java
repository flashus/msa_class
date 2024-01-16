package ru.idyachenko.users.service;

import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.entity.UserSkillId;
import ru.idyachenko.users.repository.UserSkillRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;

    public UserSkillService(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    public List<UserSkill> getAllUserSkills() {
        return userSkillRepository.findAll();
    }

    public List<UserSkill> getUserSkills(UUID userId) {
        return userSkillRepository.findByUserId(userId);
    }

    public String createUserSkill(@NonNull UserSkill userSkill) {
        UserSkill savedUserSkill = userSkillRepository.save(userSkill);
        return String.format("User/Skill %s/%s added to the database", savedUserSkill.getUser(),
                savedUserSkill.getSkill());
    }

    public String deleteUserSkill(@NonNull UserSkill userSkill) {
        UserSkillId id = new UserSkillId(userSkill.getUser().getId(), userSkill.getSkill().getId());
        if (!userSkillRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userSkillRepository.delete(userSkill);
        return String.format("User/Skill %s/%s deleted", userSkill.getUser(),
                userSkill.getSkill());
    }
}
