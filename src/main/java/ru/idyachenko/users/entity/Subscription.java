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

    public SubscriptionId getId() {
        return new SubscriptionId(userFollowing.getId(), userFollowed.getId());
    }

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

    public Subscription() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    // public Subscription(SubscriptionId subscriptionId) {
    // this.userFollowing = subscriptionId.getUserFollowing();
    // this.userFollowed = subscriptionId.getUserFollowed();
    // this.created_at = new Timestamp(System.currentTimeMillis());
    // }

    @Override
    public String toString() {
        return "Subscription{" +
                "userFollowing=" + userFollowing +
                ", userFollowed=" + userFollowed +
                ", created_at=" + created_at +
                '}';
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subscription other = (Subscription) obj;
        if (userFollowing == null) {
            if (other.userFollowing != null)
                return false;
        } else if (!userFollowing.equals(other.userFollowing))
            return false;
        if (userFollowed == null) {
            if (other.userFollowed != null)
                return false;
        } else if (!userFollowed.equals(other.userFollowed))
            return false;
        return true;
    }
}
