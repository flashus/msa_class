package ru.idyachenko.users.service;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
// import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.entity.UserSkillId;
import ru.idyachenko.users.repository.UserSkillRepository;

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

    public UserSkill getUserSkill(UUID userId, UUID skillId) {
        UserSkillId userSkillId = new UserSkillId(userId, skillId);
        return userSkillRepository.findById(userSkillId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UserSkill getUserSkill(@NonNull UserSkillId userSkillId) {
        return userSkillRepository.findById(userSkillId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> createUserSkill(@NonNull UserSkill userSkill) {
        UserSkill savedUserSkill = userSkillRepository.save(userSkill);

        String desc = String.format("User/Skill %s/%s added to the database",
                savedUserSkill.getUser(), savedUserSkill.getSkill());
        HttpHeaders headers = Common.getHeaders(savedUserSkill.getId(), "/user-skills/");
        return new ResponseEntity<>(desc, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteUserSkill(@NonNull UserSkillId userSkillId) {
        UserSkill userSkill = getUserSkill(userSkillId);
        return deleteUserSkill(userSkill);
    }

    public ResponseEntity<String> deleteUserSkill(@NonNull UserSkill userSkill) {
        UserSkillId userSkillId =
                new UserSkillId(userSkill.getUser().getId(), userSkill.getSkill().getId());
        if (!userSkillRepository.existsById(userSkillId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userSkillRepository.deleteById(userSkillId);

        String desc = String.format("User/Skill %s/%s deleted", userSkill.getUser().getId(),
                userSkill.getSkill());
        HttpHeaders headers = Common.getHeaders(userSkillId, "/user-skills/");
        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }
}
