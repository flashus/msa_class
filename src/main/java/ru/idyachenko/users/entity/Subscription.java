package ru.idyachenko.users.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscriptions")
@IdClass(SubscriptionId.class)
public class Subscription {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_following_id")
    private User userFollowing;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_followed_id")
    private User userFollowed;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    // Constructors, getters, and setters

    public SubscriptionId getId() {
        return new SubscriptionId(userFollowing.getId(), userFollowed.getId());
    }

    public User getUserFollowing() {
        return userFollowing;
    }

    public User getUserFollowed() {
        return userFollowed;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setUserFollowing(User userFollowing) {
        this.userFollowing = userFollowing;
    }

    public void setUserFollowed(User userFollowed) {
        this.userFollowed = userFollowed;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Subscription(User userFollowing, User userFollowed) {
        this.userFollowing = userFollowing;
        this.userFollowed = userFollowed;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Subscription() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // public Subscription(SubscriptionId subscriptionId) {
    // this.userFollowing = subscriptionId.getUserFollowing();
    // this.userFollowed = subscriptionId.getUserFollowed();
    // this.created_at = new Timestamp(System.currentTimeMillis());
    // }

    @Override
    public String toString() {
        return "Subscription{" + "userFollowing=" + userFollowing + ", userFollowed=" + userFollowed
                + ", created_at=" + createdAt + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userFollowing == null) ? 0 : userFollowing.hashCode());
        result = prime * result + ((userFollowed == null) ? 0 : userFollowed.hashCode());
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
        Subscription other = (Subscription) obj;
        if (userFollowing == null) {
            if (other.userFollowing != null) {
                return false;
            }
        } else if (!userFollowing.equals(other.userFollowing)) {
            return false;
        }
        if (userFollowed == null) {
            if (other.userFollowed != null) {
                return false;
            }
        } else if (!userFollowed.equals(other.userFollowed)) {
            return false;
        }
        return true;
    }
}
