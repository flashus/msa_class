package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.SubscriptionId;

import java.util.List;
import java.util.Optional;
// import java.util.UUID;
import java.util.UUID;

public interface SubscriptionRepository extends CrudRepository<Subscription, SubscriptionId> {
    @NonNull
    Optional<Subscription> findById(@NonNull SubscriptionId subscriptionId);

    List<Subscription> findByUserFollowingId(UUID userFollowingId);
    // Optional<Subscription> findByIdUserFollowingAndIdUserFollowed(UUID
    // userFollowing, UUID userFollowed);

    @NonNull
    List<Subscription> findAll();
}
