package ru.idyachenko.users.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
// @Table(name = "users")
// @SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
// @SQLRestriction("deleted=false")

@Table(name = "USERS")
@SQLDelete(sql = "UPDATE USERS SET DELETED = true WHERE id=?")
@SQLRestriction("DELETED=false")
public class User {

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
    // @Enumerated(EnumType.ORDINAL)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "bit")
    // @Column(name = "GENDER", columnDefinition = "bit")
    // @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Nullable
    private Date bdate;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "city_id")
    // @JoinColumn(name = "CITY_ID")
    private City city;

    // @Column(name = "AVATAR_URL")
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Nullable
    private String bio;
    private String nickname;
    private String email;
    @Nullable
    private String phone;
    // @Column(name = "CREATED_AT")
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Nullable
    private Boolean deleted = Boolean.FALSE;
    @Nullable
    // @Column(name = "DELETED_AT")
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public User() {
        this.id = UUID.randomUUID();
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public User(String fname, String lname, String mname, String avatarUrl, String nickname,
            String email) {
        this.id = UUID.randomUUID();
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public User(UUID id, String fname, String lname, String mname, String avatarUrl,
            String nickname, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fname='" + fname + '\'' + ", lname='" + lname + '\''
                + ", mname='" + mname + '\'' + ", gender=" + gender + ", bdate=" + bdate + ", city="
                + city + ", avatar_url='" + avatarUrl + '\'' + ", bio='" + bio + '\''
                + ", nickname='" + nickname + '\'' + ", email='" + email + '\'' + ", phone='"
                + phone + '\'' + ", created_at=" + createdAt + ", deleted=" + deleted
                + ", deleted_at=" + deletedAt + '}';
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
    // public Boolean getGender() {
    public Gender getGender() {
        return gender;
    }

    @Nullable
    public Date getBdate() {
        return bdate;
    }

    public City getCity() {
        return city;
    }

    public String getAvatarUrl() {
        return avatarUrl;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Nullable
    public Boolean getDeleted() {
        return deleted;
    }

    @Nullable
    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setMname(@Nullable String mname) {
        this.mname = mname;
    }

    // public void setGender(@Nullable Boolean gender) {
    public void setGender(@Nullable Gender gender) {
        this.gender = gender;
    }

    public void setBdate(@Nullable Date bdate) {
        this.bdate = bdate;
    }

    public void setCity(@Nullable City city) {
        this.city = city;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setBio(@Nullable String bio) {
        this.bio = bio;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeleted(@Nullable Boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeletedAt(@Nullable Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((fname == null) ? 0 : fname.hashCode());
        result = prime * result + ((lname == null) ? 0 : lname.hashCode());
        result = prime * result + ((mname == null) ? 0 : mname.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((bdate == null) ? 0 : bdate.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
        result = prime * result + ((bio == null) ? 0 : bio.hashCode());
        result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        result = prime * result + ((deletedAt == null) ? 0 : deletedAt.hashCode());
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (fname == null) {
            if (other.fname != null) {
                return false;
            }
        } else if (!fname.equals(other.fname)) {
            return false;
        }
        if (lname == null) {
            if (other.lname != null) {
                return false;
            }
        } else if (!lname.equals(other.lname)) {
            return false;
        }
        if (mname == null) {
            if (other.mname != null) {
                return false;
            }
        } else if (!mname.equals(other.mname)) {
            return false;
        }
        if (gender != other.gender) {
            return false;
        }
        if (bdate == null) {
            if (other.bdate != null) {
                return false;
            }
        } else if (!bdate.equals(other.bdate)) {
            return false;
        }
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        } else if (!city.equals(other.city)) {
            return false;
        }
        if (avatarUrl == null) {
            if (other.avatarUrl != null) {
                return false;
            }
        } else if (!avatarUrl.equals(other.avatarUrl)) {
            return false;
        }
        if (bio == null) {
            if (other.bio != null) {
                return false;
            }
        } else if (!bio.equals(other.bio)) {
            return false;
        }
        if (nickname == null) {
            if (other.nickname != null) {
                return false;
            }
        } else if (!nickname.equals(other.nickname)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (phone == null) {
            if (other.phone != null) {
                return false;
            }
        } else if (!phone.equals(other.phone)) {
            return false;
        }
        if (createdAt == null) {
            if (other.createdAt != null) {
                return false;
            }
        } else if (!createdAt.equals(other.createdAt)) {
            return false;
        }
        if (deleted == null) {
            if (other.deleted != null) {
                return false;
            }
        } else if (!deleted.equals(other.deleted)) {
            return false;
        }
        if (deletedAt == null) {
            if (other.deletedAt != null) {
                return false;
            }
        } else if (!deletedAt.equals(other.deletedAt)) {
            return false;
        }
        return true;
    }

}
