package ru.idyachenko.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.service.UserSkillService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user-skills")
public class UserSkillController {
    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @GetMapping
    List<UserSkill> getAllUserSkills() {
        return userSkillService.getAllUserSkills();
    }

    @GetMapping(path = "/{userId}")
    List<UserSkill> getUserSkills(@PathVariable @NonNull UUID userId) {
        return userSkillService.getUserSkills(userId);
    }

    @PostMapping
    ResponseEntity<String> createUserSkill(@RequestBody @NonNull UserSkill userSkill) {
        return userSkillService.createUserSkill(userSkill);
    }

    @DeleteMapping
    ResponseEntity<String> deleteUserSkill(@RequestBody @NonNull UserSkill userSkill) {
        return userSkillService.deleteUserSkill(userSkill);
    }
}
