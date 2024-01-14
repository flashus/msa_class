package ru.idyachenko.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@IdClass(SubscriptionId.class)
public class Subscription {

    @Id
    @ManyToOne
    private User userFollowing;

    @Id
    @ManyToOne
    private User userFollowed;

    private Timestamp created_at;

    // Constructors, getters, and setters

    public User getUserFollowing() {
        return userFollowing;
    }

    public User getUserFollowed() {
        return userFollowed;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setUserFollowing(User userFollowing) {
        this.userFollowing = userFollowing;
    }

    public void setUserFollowed(User userFollowed) {
        this.userFollowed = userFollowed;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Subscription(User userFollowing, User userFollowed) {
        this.userFollowing = userFollowing;
        this.userFollowed = userFollowed;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
}
