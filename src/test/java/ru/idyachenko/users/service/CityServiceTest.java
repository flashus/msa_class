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
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.PersistenceException;
import ru.idyachenko.users.entity.City;
import ru.idyachenko.users.repository.CityRepository;

// @SpringBootTest
public class CityServiceTest {

    // @InjectMocks
    // private CityService cityService;

    // @Mock
    // private CityRepository cityRepository;

    private CityRepository cityRepository = mock(CityRepository.class);
    private CityService cityService = new CityService(cityRepository);

    private City city;
    private City city2;
    private City savedCity;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();

        city = new City("City 1");
        city2 = new City("City 2");
        savedCity = new City(id, "City 1");

    }

    // get list
    @Test
    public void testGetCities_ReturnsListOfCities() {
        // Arrange
        List<City> expectedCities = new ArrayList<>();
        expectedCities.add(city);
        expectedCities.add(city2);
        when(cityRepository.findAll()).thenReturn(expectedCities);

        // Act
        List<City> actualCities = cityService.getCities();

        // Assert
        assertEquals(expectedCities, actualCities);
    }

    @Test
    public void testGetCities_ReturnsEmptyList_WhenNoCitiesFound() {
        // Arrange
        when(cityRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<City> actualCities = cityService.getCities();

        // Assert
        assertEquals(0, actualCities.size());
    }

    // create
    @Test
    void createCity_shouldReturnCreatedResponse() {
        // given
        when(cityRepository.save(city)).thenReturn(savedCity);

        // when
        UUID response = cityService.createCity(city);
        // ResponseEntity<String> response = cityService.createCity(city);
        // HttpHeaders headers = response.getHeaders();
        // String expectedResult = String.format("City %s added to the database with id
        // = %s", savedCity.getCityName(),
        // savedCity.getId());

        // then
        // assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // assertEquals(String.format("/cities/%s", id), headers.getFirst("Location"));
        // assertEquals(id.toString(), headers.getFirst("X-UserId"));
        // assertEquals(expectedResult, response.getBody());
        assertEquals(savedCity.getId(), response);

        verify(cityRepository, times(1)).save(city);
    }

    @Test
    void createCity_shouldThrowError() {
        // given
        when(cityRepository.save(city)).thenThrow(PersistenceException.class);

        // when
        Executable executable = () -> cityService.createCity(city);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    // get
    @Test
    void getCity_WhenCityExists_ReturnCity() {
        // given
        when(cityRepository.findById(id)).thenReturn(Optional.of(city));

        // when
        City result = cityService.getCity(id);

        // then
        assertEquals(city, result);
    }

    @Test
    void getCity_WhenCityDoesNotExist_ThrowException() {
        // given
        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> cityService.getCity(id));
    }

    // update
    @Test
    public void testUpdateCity_SuccessfulUpdate() {
        city = new City(id, "City 1");

        when(cityRepository.existsById(id)).thenReturn(true);
        when(cityRepository.save(city)).thenReturn(savedCity);

        // when
        UUID response = cityService.updateCity(city, id);
        // ResponseEntity<String> response = cityService.updateCity(city, id);
        // HttpHeaders headers = response.getHeaders();
        // String desc = String.format("City %s successfully updated",
        // savedCity.getCityName());

        // then
        // assertEquals(HttpStatus.OK, response.getStatusCode());

        // assertEquals(String.format("/cities/%s", id), headers.getFirst("Location"));
        // assertEquals(id.toString(), headers.getFirst("X-UserId"));

        // assertEquals(desc, response.getBody());
        assertEquals(id, response);
    }

    @Test
    public void testUpdateCity_CityNotFound() {

        when(cityRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            cityService.updateCity(city, id);
        });
    }

    // @Test
    // public void testUpdateCity_InvalidCityId() {

    // when(cityRepository.existsById(id)).thenReturn(true);

    // assertThrows(ResponseStatusException.class, () -> {
    // cityService.updateCity(city, id);
    // });
    // }

    @Test
    public void testUpdateCity_InvalidCityId() {
        UUID id2 = UUID.randomUUID();
        when(cityRepository.save(city)).thenReturn(savedCity);
        when(cityRepository.existsById(id2)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            cityService.updateCity(savedCity, id2);
        });
    }

    @Test
    public void testUpdateCity_NullCityId() {

        when(cityRepository.existsById(id)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            cityService.updateCity(city, id);
        });
    }

    // delete
    @Test
    void testDeleteCity_Exists() {

        when(cityRepository.existsById(id)).thenReturn(true);

        // when
        UUID response = cityService.deleteCity(id);
        // ResponseEntity<String> response = cityService.deleteCity(id);
        // HttpHeaders headers = response.getHeaders();
        // String desc = String.format("City with id = %s successfully deleted", id);

        // then
        // assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertEquals(String.format("/cities/%s", id), headers.getFirst("Location"));
        // assertEquals(id.toString(), headers.getFirst("X-UserId"));

        verify(cityRepository).deleteById(id);
        // assertEquals(desc, response.getBody());
        assertEquals(id, response);
    }

    @Test
    void testDeleteCity_NotExists() {

        when(cityRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            cityService.deleteCity(id);
        });

        verify(cityRepository, never()).deleteById(id);
    }
}
