package ru.idyachenko.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "skills")
public class Skill {

    // @Id
    // @GeneratedValue(generator = "uuid2")
    // @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column(name = "skill_desc", nullable = false)
    private String skillDesc;

    // Constructors, getters, and setters

    public UUID getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillDesc() {
        return skillDesc;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setSkillDesc(String skillDesc) {
        this.skillDesc = skillDesc;
    }

    public Skill(String skillName, String skillDesc) {
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public Skill(String skillName) {
        // this.id = UUID.randomUUID();
        this.skillName = skillName;
    }

    public Skill(UUID id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }

    public Skill() {
        // this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                ", skillDesc='" + skillDesc + '\'' +
                '}';
    }
}
