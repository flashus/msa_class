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

    public UserSkillId getId() {
        return new UserSkillId(user.getId(), skill.getId());
    }

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

    public UserSkill(User user, Skill skill) {
        this.skill = skill;
        this.user = user;
    }

    public UserSkill() {}

    @Override
    public String toString() {
        return "UserSkill{" + "user=" + user + ", skill=" + skill + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((skill == null) ? 0 : skill.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserSkill other = (UserSkill) obj;
        if (skill == null) {
            if (other.skill != null) {
                return false;
            }
        } else if (!skill.equals(other.skill)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

}
