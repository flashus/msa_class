package ru.idyachenko.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.PersistenceException;
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.repository.UserRepository;

class UserServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private UserService userService = new UserService(userRepository);
    private User user;
    private User savedUser;
    private User user2;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        user = new User("Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
        savedUser =
                new User(id, "Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");
        user2 = new User("Innokent", "Smirnoff", "Kentovich", "http://", "vasya", "vasya@mail.com");

    }

    @AfterEach
    void tearDown() {}

    @Test
    void createUserSuccess() {
        // given
        when(userRepository.save(user)).thenReturn(savedUser);

        // when
        ResponseEntity<String> response = userService.createUser(user);
        HttpHeaders headers = response.getHeaders();

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(String.format("/users/%s", id), headers.getFirst("Location"));
        assertEquals(id.toString(), headers.getFirst("X-UserId"));

        String expectedResult =
                String.format("Пользователь %s добавлен в базу с id = %s", user.getLname(), id);

        assertEquals(expectedResult, response.getBody());

    }

    @Test
    void createUserError() {
        // given
        when(userRepository.save(user)).thenThrow(PersistenceException.class);

        // when
        Executable executable = () -> userService.createUser(user);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    @Test
    public void testGetUser_ValidId_ReturnsUser() {
        // Arrange
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUser(id);

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testGetUser_InvalidId_ThrowsResponseStatusException() {
        // Arrange
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userService.getUser(id));
    }

    @Test
    public void testUpdateUser_SuccessfulUpdate() {
        User user =
                new User(id, "Vasya", "Petrov", "Ivanovich", "http://", "vasya", "vasya@mail.com");

        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(savedUser);

        // when
        ResponseEntity<String> response = userService.updateUser(user, id);
        HttpHeaders headers = response.getHeaders();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(String.format("/users/%s", id), headers.getFirst("Location"));
        assertEquals(id.toString(), headers.getFirst("X-UserId"));

        assertEquals("Пользователь Petrov успешно сохранен", response.getBody());
    }

    @Test
    public void testUpdateUser_UserNotFound() {

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            userService.updateUser(user, id);
        });
    }

    @Test
    public void testUpdateUser_InvalidUserId() {
        UUID id2 = UUID.randomUUID();
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userRepository.existsById(id2)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            userService.updateUser(savedUser, id2);
        });
    }

    @Test
    public void testUpdateUser_NullUserId() {

        when(userRepository.existsById(id)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            userService.updateUser(user, id);
        });
    }

    // delete
    @Test
    void testDeleteUser_Exists() {

        when(userRepository.existsById(id)).thenReturn(true);

        // when
        ResponseEntity<String> response = userService.deleteUser(id);
        HttpHeaders headers = response.getHeaders();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(String.format("/users/%s", id), headers.getFirst("Location"));
        assertEquals(id.toString(), headers.getFirst("X-UserId"));

        verify(userRepository).deleteById(id);
        assertEquals("Пользователь с id = " + id + " успешно удален", response.getBody());
    }

    @Test
    void testDeleteUser_NotExists() {

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            userService.deleteUser(id);
        });

        verify(userRepository, never()).deleteById(id);
    }

    @Test
    public void testGetUsers() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);
        expectedUsers.add(user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }
}
