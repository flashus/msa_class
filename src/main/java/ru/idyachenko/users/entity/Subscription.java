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

    private Timestamp createdAt;

    // Constructors, getters, and setters
}
