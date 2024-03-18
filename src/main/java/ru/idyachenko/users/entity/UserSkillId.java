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

    public UserSkillId(UUID user, UUID skill) {
        this.skill = skill;
        this.user = user;
    }

    public UserSkillId() {}

    @Override
    public String toString() {
        return "UserSkillId{" + "user=" + user + ", skill=" + skill + '}';
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
        UserSkillId other = (UserSkillId) obj;
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
