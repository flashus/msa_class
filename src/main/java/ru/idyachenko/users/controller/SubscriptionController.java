package ru.idyachenko.users.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.SubscriptionId;
import ru.idyachenko.users.service.SubscriptionService;

@RestController
@RequestMapping(value = "/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    List<Subscription> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }

    @GetMapping(path = "/{userId}")
    List<Subscription> getUserSubscriptions(@PathVariable @NonNull UUID userId) {
        List<Subscription> userSubscriptions = subscriptionService.getUserSubscriptions(userId);
        return userSubscriptions;
    }

    // @PostMapping
    // ResponseEntity<String> createSubscription(@RequestBody @NonNull Subscription subscription) {
    // return subscriptionService.createSubscription(subscription);
    // }

    @PostMapping
    ResponseEntity<String> createSubscription(@RequestBody @NonNull SubscriptionId subscriptionId) {
        return subscriptionService.createSubscription(subscriptionId);
    }

    @GetMapping(path = "/{userFollowingId}/{userFollowedId}")
    Subscription getSubscription(@PathVariable @NonNull UUID userFollowingId,
            @PathVariable @NonNull UUID userFollowedId) {
        SubscriptionId subscriptionId = new SubscriptionId(userFollowingId, userFollowedId);
        return subscriptionService.getSubscription(subscriptionId);
    }

    // @PutMapping(path = "/{userFollowingId}/{userFollowedId}")
    // ResponseEntity<String> updateSubscription(@RequestBody Subscription subscription,
    // @PathVariable @NonNull UUID userFollowingId,
    // @PathVariable @NonNull UUID userFollowedId) {
    // return subscriptionService.updateSubscription(subscription, userFollowingId,
    // userFollowedId);
    // }

    @DeleteMapping(path = "/{userFollowingId}/{userFollowedId}")
    ResponseEntity<String> deleteSubscription(@PathVariable @NonNull UUID userFollowingId,
            @PathVariable @NonNull UUID userFollowedId) {
        return subscriptionService.deleteSubscription(userFollowingId, userFollowedId);
    }
}
