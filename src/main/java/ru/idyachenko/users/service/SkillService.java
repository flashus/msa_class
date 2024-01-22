package ru.idyachenko.users.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<String> createSkill(@NonNull Skill skill) {
        Skill savedSkill = skillRepository.save(skill);

        String desc = String.format("Skill %s added to the database with id = %s", savedSkill.getSkillName(),
                savedSkill.getId());
        HttpHeaders headers = Common.getHeaders(savedSkill.getId(), "/skills/");
        return new ResponseEntity<>(desc, headers, HttpStatus.CREATED);
    }

    public Skill getSkill(@NonNull UUID id) {
        return skillRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> updateSkill(Skill skill, @NonNull UUID id) {
        if (!skillRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (skill.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!skill.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Skill savedSkill = skillRepository.save(skill);

        String desc = String.format("Skill %s successfully updated", savedSkill.getSkillName());
        HttpHeaders headers = Common.getHeaders(savedSkill.getId(), "/skills/");
        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteSkill(@NonNull UUID id) {
        if (!skillRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        skillRepository.deleteById(id);

        String desc = String.format("Skill with id = %s successfully deleted", id);
        HttpHeaders headers = Common.getHeaders(id, "/skills/");
        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }
}
