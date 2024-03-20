package ru.idyachenko.users.smoke;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import ru.idyachenko.users.entity.Gender;
import ru.idyachenko.users.entity.User;


@ActiveProfiles("integTest-smoke")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAndRetrieveUser() {
        // Create user
        String createUserUrl = "http://localhost:" + port + "/users";
        // String createUserRequestBody =
        // "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}";
        // String createdUserId =
        // restTemplate.postForObject(createUserUrl, createUserRequestBody, String.class);
        User vasya = new User("Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
        vasya.setGender(Gender.MALE);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(createUserUrl, vasya, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // String createdUserId = createResponse.getBody().getId().toString();
        String createdUserId = responseEntity.getHeaders().get("X-UserId").get(0);
        vasya.setId(UUID.fromString(createdUserId));

        // Retrieve user
        var getResponse =
                restTemplate.getForEntity(createUserUrl + "/" + createdUserId, User.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(vasya, getResponse.getBody());

    }

}
