package ru.idyachenko.users.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.entity.UserSkillId;

public interface UserSkillRepository extends CrudRepository<UserSkill, UserSkillId> {
    @NonNull
    List<UserSkill> findAll();

    List<UserSkill> findByUserId(UUID userId);
}
