package ru.idyachenko.users.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.idyachenko.users.entity.Skill;
import ru.idyachenko.users.service.SkillService;

import java.util.List;
import java.util.UUID;

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
    String createSkill(@RequestBody @NonNull Skill skill) {
        return skillService.createSkill(skill);
    }

    @GetMapping(path = "/{id}")
    Skill getSkill(@PathVariable @NonNull UUID id) {
        return skillService.getSkill(id);
    }

    @PutMapping(path = "/{id}")
    String updateSkill(@RequestBody Skill skill, @PathVariable @NonNull UUID id) {
        return skillService.updateSkill(skill, id);
    }

    @DeleteMapping(path = "/{id}")
    String deleteSkill(@PathVariable @NonNull UUID id) {
        return skillService.deleteSkill(id);
    }
}
