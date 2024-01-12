package ru.idyachenko.users.entity;

import org.hibernate.annotations.GenericGenerator;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.UUID;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class User {
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private long id;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    // @GenericGenerator(name = "uuid2", type = UuidGenerator.class)
    private UUID id;

    private String fname;

    private String lname;
    @Nullable
    private String mname;
    @Nullable
    private Boolean gender;
    @Nullable
    private Date bdate;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private String avatar_url;
    @Nullable
    private String bio;
    private String nickname;
    private String email;
    @Nullable
    private String phone;
    private Timestamp created_at;
    @Nullable
    private Boolean deleted = Boolean.FALSE;
    @Nullable
    private Timestamp deleted_at;

    public User() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public User(String fname, String lname, String mname, String avatar_url, String nickname, String email) {
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.avatar_url = avatar_url;
        this.nickname = nickname;
        this.email = email;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mname='" + mname + '\'' +
                ", gender=" + gender +
                ", bdate=" + bdate +
                ", city=" + city +
                ", avatar_url='" + avatar_url + '\'' +
                ", bio='" + bio + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", created_at=" + created_at +
                ", deleted=" + deleted +
                ", deleted_at=" + deleted_at +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    @Nullable
    public String getMname() {
        return mname;
    }

    @Nullable
    public Boolean getGender() {
        return gender;
    }

    @Nullable
    public Date getBdate() {
        return bdate;
    }

    public City getCity() {
        return city;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Nullable
    public String getBio() {
        return bio;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    @Nullable
    public Boolean getDeleted() {
        return deleted;
    }

    @Nullable
    public Timestamp getDeleted_at() {
        return deleted_at;
    }
}
