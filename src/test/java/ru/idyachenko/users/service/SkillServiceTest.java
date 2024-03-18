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
import ru.idyachenko.users.repository.SkillRepository;

public class SkillServiceTest {
    // @InjectMocks
    // private SkillService skillService;

    // @Mock
    // private SkillRepository skillRepository;

    private SkillRepository skillRepository = mock(SkillRepository.class);
    private SkillService skillService = new SkillService(skillRepository);

    private Skill skill;
    private Skill skill2;
    private Skill savedSkill;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();

        skill = new Skill("Skill 1");
        skill2 = new Skill("Skill 2");
        savedSkill = new Skill(id, "Skill 1");

    }

    // get list
    @Test
    public void testGetSkills_ReturnsListOfSkills() {
        // Arrange
        List<Skill> expectedSkills = new ArrayList<>();
        expectedSkills.add(skill);
        expectedSkills.add(skill2);
        when(skillRepository.findAll()).thenReturn(expectedSkills);

        // Act
        List<Skill> actualSkills = skillService.getSkills();

        // Assert
        assertEquals(expectedSkills, actualSkills);
    }

    @Test
    public void testGetSkills_ReturnsEmptyList_WhenNoSkillsFound() {
        // Arrange
        when(skillRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Skill> actualSkills = skillService.getSkills();

        // Assert
        assertEquals(0, actualSkills.size());
    }

    // create
    @Test
    void createSkill_shouldReturnCreatedResponse() {
        // given
        when(skillRepository.save(skill)).thenReturn(savedSkill);

        // when
        ResponseEntity<String> response = skillService.createSkill(skill);
        HttpHeaders headers = response.getHeaders();
        final String expectedResult = String.format("Skill %s added to the database with id = %s",
                savedSkill.getSkillName(), savedSkill.getId());

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(String.format("/skills/%s", id), headers.getFirst("Location"));
        assertEquals(id.toString(), headers.getFirst("X-UserId"));
        assertEquals(expectedResult, response.getBody());

        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    void createSkill_shouldThrowError() {
        // given
        when(skillRepository.save(skill)).thenThrow(PersistenceException.class);

        // when
        Executable executable = () -> skillService.createSkill(skill);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    // get
    @Test
    void getSkill_WhenSkillExists_ReturnSkill() {
        // given
        when(skillRepository.findById(id)).thenReturn(Optional.of(skill));

        // when
        Skill result = skillService.getSkill(id);

        // then
        assertEquals(skill, result);
    }

    @Test
    void getSkill_WhenSkillDoesNotExist_ThrowException() {
        // given
        when(skillRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> skillService.getSkill(id));
    }

    // update
    @Test
    public void testUpdateSkill_SuccessfulUpdate() {
        skill = new Skill(id, "Skill 1");

        when(skillRepository.existsById(id)).thenReturn(true);
        when(skillRepository.save(skill)).thenReturn(savedSkill);

        // when
        ResponseEntity<String> response = skillService.updateSkill(skill, id);
        HttpHeaders headers = response.getHeaders();
        final String desc =
                String.format("Skill %s successfully updated", savedSkill.getSkillName());

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(String.format("/skills/%s", id), headers.getFirst("Location"));
        assertEquals(id.toString(), headers.getFirst("X-UserId"));

        assertEquals(desc, response.getBody());
    }

    @Test
    public void testUpdateSkill_SkillNotFound() {

        when(skillRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            skillService.updateSkill(skill, id);
        });
    }

    @Test
    public void testUpdateSkill_InvalidSkillId() {
        UUID id2 = UUID.randomUUID();
        when(skillRepository.save(skill)).thenReturn(savedSkill);
        when(skillRepository.existsById(id2)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            skillService.updateSkill(savedSkill, id2);
        });
    }

    @Test
    public void testUpdateSkill_NullSkillId() {

        when(skillRepository.existsById(id)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            skillService.updateSkill(skill, id);
        });
    }

    // delete
    @Test
    void testDeleteSkill_Exists() {

        when(skillRepository.existsById(id)).thenReturn(true);

        // when
        ResponseEntity<String> response = skillService.deleteSkill(id);
        HttpHeaders headers = response.getHeaders();
        final String desc = String.format("Skill with id = %s successfully deleted", id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("/skills/%s", id), headers.getFirst("Location"));
        assertEquals(id.toString(), headers.getFirst("X-UserId"));

        verify(skillRepository).deleteById(id);
        assertEquals(desc, response.getBody());
    }

    @Test
    void testDeleteSkill_NotExists() {

        when(skillRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            skillService.deleteSkill(id);
        });

        verify(skillRepository, never()).deleteById(id);
    }
}
