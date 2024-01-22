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
        SubscriptionId other = (SubscriptionId) obj;
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

    @Override
    public String toString() {
        return "SubscriptionId{" +
                "userFollowing=" + userFollowing +
                ", userFollowed=" + userFollowed +
                '}';
    }
}
