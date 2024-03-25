package ru.idyachenko.users.regression;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.idyachenko.users.entity.Gender;
import ru.idyachenko.users.entity.Subscription;
import ru.idyachenko.users.entity.SubscriptionId;
import ru.idyachenko.users.entity.User;


@ActiveProfiles("integTest-smoke")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegressionTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Logger LOGGER = LogManager.getLogger(RegressionTest.class);

    // @RegisterExtension
    // public final SystemOutCaptureExtension systemOutCaptureExtension =
    // new SystemOutCaptureExtension();

    @Test
    public void testTwoUsersSubscriptionCrud() {
        // Создайте двух пользователей.
        // Успешно получите их по ID.
        // Проведите поиск пользователей.
        // Подпишите одного пользователя на другого.
        // Проверьте изменившиеся данные.
        // Удалите подписку.
        // Проверьте изменившиеся данные.
        // Проверьте частичное изменение данных.
        // Проверьте изменившиеся данные.
        // Удалите пользователей.
        // Получите ошибку 404 по ID пользователей.

        // Create user
        String userUrl = "http://localhost:" + port + "/users";
        String subscriptionsUrl = "http://localhost:" + port + "/subscriptions";

        User vasya = new User("Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
        vasya.setGender(Gender.MALE);
        User katya =
                new User("Katya", "Ivanova", "Vasilyevna", "http://", "katya", "katya@mail.com");
        vasya.setGender(Gender.FEMALE);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(userUrl, vasya, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        String createdUserId = responseEntity.getHeaders().get("X-UserId").get(0);
        vasya.setId(UUID.fromString(createdUserId));

        // Retrieve user
        var getResponse = restTemplate.getForEntity(userUrl + "/" + createdUserId, User.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(vasya, getResponse.getBody());

        // kate

        ResponseEntity<String> responseEntityK =
                restTemplate.postForEntity(userUrl, katya, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        String createdUserIdK = responseEntityK.getHeaders().get("X-UserId").get(0);
        katya.setId(UUID.fromString(createdUserIdK));

        // Retrieve user
        var getResponseK = restTemplate.getForEntity(userUrl + "/" + createdUserIdK, User.class);
        assertEquals(HttpStatus.OK, getResponseK.getStatusCode());
        assertEquals(katya, getResponseK.getBody());

        // Search
        ParameterizedTypeReference<List<User>> responseType =
                new ParameterizedTypeReference<List<User>>() {};
        ResponseEntity<List<User>> getResponseAll =
                restTemplate.exchange(userUrl, HttpMethod.GET, null, responseType);
        assertEquals(HttpStatus.OK, getResponseAll.getStatusCode());

        List<User> userList = getResponseAll.getBody();
        assertNotNull(userList);
        assertTrue(userList.contains(katya));
        assertTrue(userList.contains(vasya));


        // Subscribe
        Subscription subscription = new Subscription(vasya, katya);
        SubscriptionId subscriptionId = subscription.getId();
        ResponseEntity<String> postResponse =
                restTemplate.postForEntity(subscriptionsUrl, subscriptionId, String.class);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Subscription creation POST request status: {}",
                    postResponse.getStatusCode());
            if (postResponse.hasBody()) {
                LOGGER.info("Subscription creation POST request payload: {}",
                        postResponse.getBody());
            }
        }
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        ResponseEntity<List<Subscription>> getSubscriptions =
                restTemplate.exchange(subscriptionsUrl + "/{id}", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Subscription>>() {}, vasya.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("GET subscriptions request URL: {}",
                    getSubscriptions.getHeaders().getLocation());
            LOGGER.info("GET subscriptions response status: {}", getSubscriptions.getStatusCode());
            if (getSubscriptions.hasBody()) {
                LOGGER.info("GET subscriptions response payload: {}", getSubscriptions.getBody());
            }
        }

        assertEquals(HttpStatus.OK, getSubscriptions.getStatusCode());
        List<Subscription> subscriptions = getSubscriptions.getBody();
        System.out.println(subscriptions);
        assertEquals(1, subscriptions.size());
        assertEquals(subscription, subscriptions.get(0));

        restTemplate.delete(subscriptionsUrl + "/{from}/{to}", vasya.getId(), katya.getId());
        getSubscriptions = restTemplate.exchange(subscriptionsUrl + "/{id}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Subscription>>() {}, vasya.getId());
        assertEquals(HttpStatus.OK, getSubscriptions.getStatusCode());
        subscriptions = getSubscriptions.getBody();
        assertTrue(subscriptions.isEmpty());

        // Change user
        vasya.setEmail("crocodile@mail.com");
        ResponseEntity<String> responseEntityPutUser =
                restTemplate.exchange(userUrl + "/" + vasya.getId(), HttpMethod.PUT,
                        new HttpEntity<>(vasya), String.class);
        assertEquals(HttpStatus.OK, responseEntityPutUser.getStatusCode());

        User vasyaModified =
                restTemplate.getForEntity(userUrl + "/" + vasya.getId(), User.class).getBody();
        assertNotNull(vasyaModified);
        assertEquals("crocodile@mail.com", vasyaModified.getEmail());

        // delete users

        ResponseEntity<String> responseEntityDeleteUser1 = restTemplate
                .exchange(userUrl + "/" + vasya.getId(), HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, responseEntityDeleteUser1.getStatusCode());
        ResponseEntity<String> responseEntityGetDeletedUser1 = restTemplate
                .exchange(userUrl + "/" + vasya.getId(), HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityGetDeletedUser1.getStatusCode());

        ResponseEntity<String> responseEntityDeleteUser2 = restTemplate
                .exchange(userUrl + "/" + katya.getId(), HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, responseEntityDeleteUser2.getStatusCode());
        ResponseEntity<String> responseEntityGetDeletedUser2 = restTemplate
                .exchange(userUrl + "/" + katya.getId(), HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityGetDeletedUser2.getStatusCode());
    }

}
