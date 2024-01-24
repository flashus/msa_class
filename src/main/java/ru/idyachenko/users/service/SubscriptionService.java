package ru.idyachenko.users.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.SubscriptionId;
import ru.idyachenko.users.repository.SubscriptionRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> getUserSubscriptions(UUID userFollowingId) {
        return subscriptionRepository.findByUserFollowingId(userFollowingId);
    }

    public ResponseEntity<String> createSubscription(@NonNull Subscription subscription) {
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        String desc = String.format("Subscription added to the database with id (userFollowing=%s, userFollowed=%s)",
                savedSubscription.getUserFollowing(), savedSubscription.getUserFollowed());
        HttpHeaders headers = Common.getHeaders(savedSubscription.getId(), "/subscriptions/");
        return new ResponseEntity<>(desc, headers, HttpStatus.CREATED);
    }

    public Subscription getSubscription(UUID userFollowingId, UUID userFollowedId) {
        SubscriptionId subscriptionId = new SubscriptionId(userFollowingId, userFollowedId);
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Subscription getSubscription(@NonNull SubscriptionId subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> updateSubscription(Subscription subscription, SubscriptionId subscriptionId) {
        UUID userFollowingId = subscription.getUserFollowing().getId();
        UUID userFollowedId = subscription.getUserFollowed().getId();
        return updateSubscription(subscription, userFollowingId, userFollowedId);
    }

    public ResponseEntity<String> updateSubscription(Subscription subscription, UUID userFollowingId,
            UUID userFollowedId) {
        SubscriptionId subscriptionId = new SubscriptionId(userFollowingId, userFollowedId);
        if (!subscriptionRepository.existsById(subscriptionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!subscription.getUserFollowing().getId().equals(userFollowingId)
                || !subscription.getUserFollowed().getId().equals(userFollowedId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User following and user followed must match the IDs.");
        }

        if ((subscription.getUserFollowing().getId() == null)
                || (subscription.getUserFollowed().getId() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User following and user followed must not be null.");
        }

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        String desc = String.format("Subscription with id (userFollowing=%s, userFollowed=%s) successfully updated",
                savedSubscription.getUserFollowing(), savedSubscription.getUserFollowed());
        HttpHeaders headers = Common.getHeaders(savedSubscription.getId(), "/subscriptions/");
        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteSubscription(SubscriptionId subscriptionId) {
        UUID userFollowingId = subscriptionId.getUserFollowing();
        UUID userFollowedId = subscriptionId.getUserFollowed();
        return deleteSubscription(userFollowingId, userFollowedId);
    }

    public ResponseEntity<String> deleteSubscription(UUID userFollowingId, UUID userFollowedId) {
        SubscriptionId subscriptionId = new SubscriptionId(userFollowingId, userFollowedId);
        if (!subscriptionRepository.existsById(subscriptionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        subscriptionRepository.deleteById(subscriptionId);

        String desc = String.format("Subscription with id = %s successfully deleted", subscriptionId);
        HttpHeaders headers = Common.getHeaders(subscriptionId, "/subscriptions/");
        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }
}
