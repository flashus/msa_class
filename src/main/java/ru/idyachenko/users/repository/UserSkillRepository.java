package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;

import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.entity.UserSkillId;

public interface UserSkillRepository extends CrudRepository<UserSkill, UserSkillId> {
}
