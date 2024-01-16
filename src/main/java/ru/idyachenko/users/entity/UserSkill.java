package ru.idyachenko.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    public Skill getSkill() {
        return skill;
    }

    public User getUser() {
        return user;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSkill(Skill skill, User user) {
        this.skill = skill;
        this.user = user;
    }

    public UserSkill() {
    }

    @Override
    public String toString() {
        return "UserSkill{" +
                "user=" + user +
                ", skill=" + skill +
                '}';
    }
}
