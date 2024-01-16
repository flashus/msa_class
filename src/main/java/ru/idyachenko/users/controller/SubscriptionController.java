package ru.idyachenko.users.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.SubscriptionId;
import ru.idyachenko.users.service.SubscriptionService;

import java.util.List;
import java.util.UUID;

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
        return subscriptionService.getUserSubscriptions(userId);
    }

    @PostMapping
    String createSubscription(@RequestBody @NonNull Subscription subscription) {
        return subscriptionService.createSubscription(subscription);
    }

    @GetMapping(path = "/{userFollowingId}/{userFollowedId}")
    Subscription getSubscription(
            @PathVariable @NonNull UUID userFollowingId,
            @PathVariable @NonNull UUID userFollowedId) {
        SubscriptionId subscriptionId = new SubscriptionId(userFollowingId, userFollowedId);
        return subscriptionService.getSubscription(subscriptionId);
    }

    @PutMapping(path = "/{userFollowingId}/{userFollowedId}")
    String updateSubscription(
            @RequestBody Subscription subscription,
            @PathVariable @NonNull UUID userFollowingId,
            @PathVariable @NonNull UUID userFollowedId) {
        return subscriptionService.updateSubscription(subscription, userFollowingId, userFollowedId);
    }

    @DeleteMapping(path = "/{userFollowingId}/{userFollowedId}")
    String deleteSubscription(
            @PathVariable @NonNull UUID userFollowingId,
            @PathVariable @NonNull UUID userFollowedId) {
        return subscriptionService.deleteSubscription(userFollowingId, userFollowedId);
    }
}
