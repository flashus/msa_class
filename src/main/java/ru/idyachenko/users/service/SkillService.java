package ru.idyachenko.users.service;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.Skill;
import ru.idyachenko.users.repository.SkillRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    public String createSkill(@NonNull Skill skill) {
        Skill savedSkill = skillRepository.save(skill);
        return String.format("Skill %s added to the database with id = %s", savedSkill.getSkillName(),
                savedSkill.getId());
    }

    public Skill getSkill(@NonNull UUID id) {
        return skillRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateSkill(Skill skill, @NonNull UUID id) {
        if (!skillRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!skill.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Skill savedSkill = skillRepository.save(skill);
        return String.format("Skill %s successfully updated", savedSkill.getSkillName());
    }

    public String deleteSkill(@NonNull UUID id) {
        if (!skillRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        skillRepository.deleteById(id);
        return String.format("Skill with id = %s successfully deleted", id);
    }
}
