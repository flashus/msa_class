package ru.idyachenko.users.entity;

import java.io.Serializable;
import java.util.UUID;

public class UserSkillId implements Serializable {

    private UUID skill;

    private UUID user;

    // Constructors, equals, and hashCode methods

    public UUID getSkill() {
        return skill;
    }

    public UUID getUser() {
        return user;
    }

    public void setSkill(UUID skill) {
        this.skill = skill;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public UserSkillId(UUID skill, UUID user) {
        this.skill = skill;
        this.user = user;
    }

    public UserSkillId() {
    }

    @Override
    public String toString() {
        return "UserSkillId{" +
                "user=" + user +
                ", skill=" + skill +
                '}';
    }
}
