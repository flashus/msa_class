package ru.idyachenko.users.entity;

import java.io.Serializable;
import java.util.UUID;

public class SubscriptionId implements Serializable {

    private UUID userFollowing;

    private UUID userFollowed;

    // Constructors, equals, and hashCode methods

    public UUID getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(UUID userFollowing) {
        this.userFollowing = userFollowing;
    }

    public UUID getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(UUID userFollowed) {
        this.userFollowed = userFollowed;
    }

    public SubscriptionId(UUID userFollowing, UUID userFollowed) {
        this.userFollowing = userFollowing;
        this.userFollowed = userFollowed;
    }

    public SubscriptionId() {
    }

    @Override
    public String toString() {
        return "SubscriptionId{" +
                "userFollowing=" + userFollowing +
                ", userFollowed=" + userFollowed +
                '}';
    }
}
