package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;

import ru.idyachenko.users.entity.Skill;
import java.util.UUID;

public interface SkillRepository extends CrudRepository<Skill, UUID> {
}
