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
import ru.idyachenko.users.entity.Skill;
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.entity.UserSkill;
import ru.idyachenko.users.repository.UserSkillRepository;

public class UserSkillServiceTest {

    private UserSkillRepository userSkillRepository = mock(UserSkillRepository.class);
    private UserSkillService userSkillService = new UserSkillService(userSkillRepository);

    private UserSkill userSkill;
    private UserSkill userSkill2;
    private UserSkill savedUserSkill;
    private UUID userId1;
    private UUID userId2;
    private User user1;
    private User user2;
    private Skill skill1;
    private Skill skill2;
    // private UserSkillId userSkillId;
    // private UserSkillId userSkillId2;

    @BeforeEach
    void setUp() {
        userId1 = UUID.randomUUID();
        userId2 = UUID.randomUUID();

        user1 = new User(userId1, "Vasya", "Petrov", "Ivanovich", "http://", "vasya",
                "vasya@mail.com");
        user2 = new User(userId2, "Innokent", "Smirnoff", "Kentovich", "http://", "vasya",
                "vasya@mail.com");

        // userSkillId = new UserSkillId(userId1, userId2);
        // userSkillId2 = new UserSkillId(userId2, userId1);
        skill1 = new Skill(UUID.randomUUID(), "Java");
        skill2 = new Skill(UUID.randomUUID(), "Python");

        userSkill = new UserSkill(user1, skill1);
        savedUserSkill = new UserSkill(user1, skill1);
        userSkill2 = new UserSkill(user2, skill2);

    }

    @Test
    public void testGetUserSkills_ReturnsListOfUserSkills() {
        // Given
        List<UserSkill> expectedUserSkills = Arrays.asList(userSkill, userSkill2);
        when(userSkillRepository.findByUserId(userId1)).thenReturn(expectedUserSkills);

        // When
        List<UserSkill> actualUserSkills = userSkillService.getUserSkills(userId1);

        // Then
        assertEquals(expectedUserSkills, actualUserSkills);
    }

    @Test
    public void testGetUserSkills_ReturnsEmptyList_WhenNoUserSkillsFound() {
        // Arrange
        when(userSkillRepository.findByUserId(userId1)).thenReturn(new ArrayList<>());

        // Act
        List<UserSkill> actualUserSkills = userSkillService.getUserSkills(userId1);

        // Assert
        assertEquals(0, actualUserSkills.size());
    }

    // create
    @Test
    void createUserSkill_shouldReturnCreatedResponse() {
        // given
        when(userSkillRepository.save(userSkill)).thenReturn(savedUserSkill);

        // when
        ResponseEntity<String> response = userSkillService.createUserSkill(userSkill);
        HttpHeaders headers = response.getHeaders();
        final String expectedResult = String.format("User/Skill %s/%s added to the database",
                savedUserSkill.getUser(), savedUserSkill.getSkill());

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(String.format("/user-skills/%s", savedUserSkill.getId()),
                headers.getFirst("Location"));
        assertEquals(savedUserSkill.getId().toString(), headers.getFirst("X-UserId"));
        assertEquals(expectedResult, response.getBody());

        verify(userSkillRepository, times(1)).save(userSkill);
    }

    @Test
    void createUserSkill_shouldThrowError() {
        // given
        when(userSkillRepository.save(userSkill)).thenThrow(PersistenceException.class);

        // when
        Executable executable = () -> userSkillService.createUserSkill(userSkill);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    // get
    @Test
    void getUserSkill_WhenUserSkillExists_ReturnUserSkill() {
        // given
        when(userSkillRepository.findById(userSkill.getId())).thenReturn(Optional.of(userSkill));

        // when
        UserSkill result = userSkillService.getUserSkill(userSkill.getId());

        // then
        assertEquals(userSkill, result);
    }

    @Test
    void getUserSkill_WhenUserSkillDoesNotExist_ThrowException() {
        // given
        when(userSkillRepository.findById(userSkill.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class,
                () -> userSkillService.getUserSkill(userSkill.getId()));
    }

    // // update
    // @Test
    // public void testUpdateUserSkill_SuccessfulUpdate() {
    // // userSkill = new UserSkill(id, "UserSkill 1");

    // when(userSkillRepository.existsById(userSkill.getId())).thenReturn(true);
    // when(userSkillRepository.save(userSkill)).thenReturn(savedUserSkill);

    // // when
    // ResponseEntity<String> response = userSkillService.updateUserSkill(userSkill,
    // userSkill.getId());
    // HttpHeaders headers = response.getHeaders();
    // String desc = String.format("UserSkill with id (userFollowing=%s,
    // userFollowed=%s) successfully updated",
    // savedUserSkill.getUserFollowing(), savedUserSkill.getUserFollowed());

    // // then
    // assertEquals(HttpStatus.OK, response.getStatusCode());

    // assertEquals(String.format("/userSkills/%s", userSkill.getId()),
    // headers.getFirst("Location"));
    // assertEquals(userSkill.getId().toString(), headers.getFirst("X-UserId"));

    // assertEquals(desc, response.getBody());
    // }

    // @Test
    // public void testUpdateUserSkill_UserSkillNotFound() {

    // when(userSkillRepository.existsById(userSkill.getId())).thenReturn(false);

    // assertThrows(ResponseStatusException.class, () -> {
    // userSkillService.updateUserSkill(userSkill, userSkill.getId());
    // });
    // }

    // @Test
    // public void testUpdateUserSkill_InvalidUserSkillId() {
    // UUID id2 = UUID.randomUUID();
    // UserSkillId subsctiptionId3 = new UserSkillId(id2,
    // userSkill.getId().getUserFollowed());
    // when(userSkillRepository.save(userSkill)).thenReturn(savedUserSkill);
    // when(userSkillRepository.existsById(subsctiptionId3)).thenReturn(true);

    // assertThrows(ResponseStatusException.class, () -> {
    // userSkillService.updateUserSkill(savedUserSkill, subsctiptionId3);
    // });
    // }

    // @Test
    // public void testUpdateUserSkill_NullUserSkillId() {
    // UserSkillId userSkillId = userSkill.getId();
    // when(userSkillRepository.existsById(userSkillId)).thenReturn(true);

    // assertThrows(ResponseStatusException.class, () -> {
    // userSkillService.updateUserSkill(userSkill, userSkillId);
    // });
    // }

    // delete
    @Test
    void testDeleteUserSkill_Exists() {

        when(userSkillRepository.existsById(userSkill.getId())).thenReturn(true);
        when(userSkillRepository.findById(userSkill.getId())).thenReturn(Optional.of(userSkill));

        // when
        ResponseEntity<String> response = userSkillService.deleteUserSkill(userSkill.getId());
        HttpHeaders headers = response.getHeaders();
        final String desc = String.format("User/Skill %s/%s deleted", userSkill.getUser().getId(),
                userSkill.getSkill());

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("/user-skills/%s", userSkill.getId()),
                headers.getFirst("Location"));
        assertEquals(userSkill.getId().toString(), headers.getFirst("X-UserId"));

        verify(userSkillRepository).deleteById(userSkill.getId());
        assertEquals(desc, response.getBody());
    }

    @Test
    void testDeleteUserSkill_NotExists() {

        when(userSkillRepository.existsById(userSkill.getId())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            userSkillService.deleteUserSkill(userSkill.getId());
        });

        verify(userSkillRepository, never()).deleteById(userSkill.getId());
    }

}
