package ru.idyachenko.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.PersistenceException;
import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.SubscriptionId;
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.repository.SubscriptionRepository;

public class SubscriptionServiceTest {

    private SubscriptionRepository subscriptionRepository = mock(SubscriptionRepository.class);
    private SubscriptionService subscriptionService =
            new SubscriptionService(subscriptionRepository);

    private Subscription subscription;
    private Subscription subscription2;
    private Subscription savedSubscription;
    private UUID userId1;
    private UUID userId2;
    private User user1;
    private User user2;
    // private SubscriptionId subscriptionId;
    // private SubscriptionId subscriptionId2;

    @BeforeEach
    void setUp() {
        userId1 = UUID.randomUUID();
        userId2 = UUID.randomUUID();

        user1 = new User(userId1, "Vasya", "Petrov", "Ivanovich", "http://", "vasya",
                "vasya@mail.com");
        // savedUser = new User(id, "Vasya", "Petrov", "Ivanovich", "http://", "vasya",
        // "vasya@mail.com");
        user2 = new User(userId2, "Innokent", "Smirnoff", "Kentovich", "http://", "vasya",
                "vasya@mail.com");

        // subscriptionId = new SubscriptionId(userId1, userId2);
        // subscriptionId2 = new SubscriptionId(userId2, userId1);

        subscription = new Subscription(user1, user2);
        savedSubscription = new Subscription(user1, user2);
        subscription2 = new Subscription(user2, user1);

    }

    @Test
    public void testGetSubscriptions_ReturnsListOfSubscriptions() {
        // Given
        List<Subscription> expectedSubscriptions = Arrays.asList(subscription, subscription2);
        when(subscriptionRepository.findAll()).thenReturn(expectedSubscriptions);

        // When
        List<Subscription> actualSubscriptions = subscriptionService.getSubscriptions();

        // Then
        assertEquals(expectedSubscriptions, actualSubscriptions);
    }

    @Test
    public void testGetSubscriptions_ReturnsEmptyList_WhenNoSubscriptionsFound() {
        // Arrange
        when(subscriptionRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Subscription> actualSubscriptions = subscriptionService.getSubscriptions();

        // Assert
        assertEquals(0, actualSubscriptions.size());
    }

    // create
    @Test
    void createSubscription_shouldReturnCreatedResponse() {
        // given
        when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);

        // when
        ResponseEntity<String> response = subscriptionService.createSubscription(subscription);
        HttpHeaders headers = response.getHeaders();
        final String expectedResult = String.format(
                "Subscription added to the database with id (userFollowing=%s, userFollowed=%s)",
                savedSubscription.getUserFollowing(), savedSubscription.getUserFollowed());

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(String.format("/subscriptions/%s", savedSubscription.getId()),
                headers.getFirst("Location"));
        assertEquals(savedSubscription.getId().toString(), headers.getFirst("X-UserId"));
        assertEquals(expectedResult, response.getBody());

        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void createSubscription_shouldThrowError() {
        // given
        when(subscriptionRepository.save(subscription)).thenThrow(PersistenceException.class);

        // when
        Executable executable = () -> subscriptionService.createSubscription(subscription);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    // get
    @Test
    void getSubscription_WhenSubscriptionExists_ReturnSubscription() {
        // given
        when(subscriptionRepository.findById(subscription.getId()))
                .thenReturn(Optional.of(subscription));

        // when
        Subscription result = subscriptionService.getSubscription(subscription.getId());

        // then
        assertEquals(subscription, result);
    }

    @Test
    void getSubscription_WhenSubscriptionDoesNotExist_ThrowException() {
        // given
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class,
                () -> subscriptionService.getSubscription(subscription.getId()));
    }

    // update
    @Test
    public void testUpdateSubscription_SuccessfulUpdate() {
        // subscription = new Subscription(id, "Subscription 1");

        when(subscriptionRepository.existsById(subscription.getId())).thenReturn(true);
        when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);

        // when
        ResponseEntity<String> response =
                subscriptionService.updateSubscription(subscription, subscription.getId());
        HttpHeaders headers = response.getHeaders();
        final String desc = String.format(
                "Subscription with id (userFollowing=%s, userFollowed=%s) successfully updated",
                savedSubscription.getUserFollowing(), savedSubscription.getUserFollowed());

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(String.format("/subscriptions/%s", subscription.getId()),
                headers.getFirst("Location"));
        assertEquals(subscription.getId().toString(), headers.getFirst("X-UserId"));

        assertEquals(desc, response.getBody());
    }

    @Test
    public void testUpdateSubscription_SubscriptionNotFound() {

        when(subscriptionRepository.existsById(subscription.getId())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            subscriptionService.updateSubscription(subscription, subscription.getId());
        });
    }

    @Test
    public void testUpdateSubscription_InvalidSubscriptionId() {
        UUID id2 = UUID.randomUUID();
        SubscriptionId subsctiptionId3 =
                new SubscriptionId(id2, subscription.getId().getUserFollowed());
        when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);
        when(subscriptionRepository.existsById(subsctiptionId3)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            subscriptionService.updateSubscription(savedSubscription, subsctiptionId3);
        });
    }

    // @Test
    // public void testUpdateSubscription_NullSubscriptionId() {
    // SubscriptionId subscriptionId = subscription.getId();
    // when(subscriptionRepository.existsById(subscriptionId)).thenReturn(true);

    // assertThrows(ResponseStatusException.class, () -> {
    // subscriptionService.updateSubscription(subscription, subscriptionId);
    // });
    // }

    // delete
    @Test
    void testDeleteSubscription_Exists() {

        when(subscriptionRepository.existsById(subscription.getId())).thenReturn(true);

        // when
        ResponseEntity<String> response =
                subscriptionService.deleteSubscription(subscription.getId());
        HttpHeaders headers = response.getHeaders();
        final String desc = String.format("Subscription with id = %s successfully deleted",
                subscription.getId());

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("/subscriptions/%s", subscription.getId()),
                headers.getFirst("Location"));
        assertEquals(subscription.getId().toString(), headers.getFirst("X-UserId"));

        verify(subscriptionRepository).deleteById(subscription.getId());
        assertEquals(desc, response.getBody());
    }

    @Test
    void testDeleteSubscription_NotExists() {

        when(subscriptionRepository.existsById(subscription.getId())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            subscriptionService.deleteSubscription(subscription.getId());
        });

        verify(subscriptionRepository, never()).deleteById(subscription.getId());
    }
}
