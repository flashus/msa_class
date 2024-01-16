package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import ru.idyachenko.users.entity.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillRepository extends CrudRepository<Skill, UUID> {
    @NonNull
    List<Skill> findAll();
}
