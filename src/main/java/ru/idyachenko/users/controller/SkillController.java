package ru.idyachenko.users.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.idyachenko.users.entity.Skill;
import ru.idyachenko.users.service.SkillService;

@RestController
@RequestMapping(value = "/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    List<Skill> getSkills() {
        return skillService.getSkills();
    }

    @PostMapping
    ResponseEntity<String> createSkill(@RequestBody @NonNull Skill skill) {
        return skillService.createSkill(skill);
    }

    @GetMapping(path = "/{id}")
    Skill getSkill(@PathVariable @NonNull UUID id) {
        return skillService.getSkill(id);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<String> updateSkill(@RequestBody Skill skill, @PathVariable @NonNull UUID id) {
        return skillService.updateSkill(skill, id);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteSkill(@PathVariable @NonNull UUID id) {
        return skillService.deleteSkill(id);
    }
}
