package ru.idyachenko.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_skills")
@IdClass(UserSkillId.class)
public class UserSkill {

    @Id
    @ManyToOne
    private Skill skill;

    @Id
    @ManyToOne
    private User user;

    // Constructors, getters, and setters
}
