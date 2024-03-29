package ru.idyachenko.users.service;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.City;
import ru.idyachenko.users.repository.CityRepository;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }

    // public ResponseEntity<String> createCity(@NonNull City city) {
    public UUID createCity(@NonNull City city) {
        City savedCity = cityRepository.save(city);
        return savedCity.getId();
        // String desc = String.format("City %s added to the database with id = %s",
        // savedCity.getCityName(),
        // savedCity.getId());

        // HttpHeaders headers = Common.getHeaders(savedCity.getId(), "/cities/");

        // return new ResponseEntity<>(desc, headers, HttpStatus.CREATED);
    }

    public City getCity(@NonNull UUID id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // public ResponseEntity<String> updateCity(City city, @NonNull UUID id) {
    public UUID updateCity(City city, @NonNull UUID id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (city.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!city.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        City savedCity = cityRepository.save(city);

        // String desc = String.format("City %s successfully updated",
        // savedCity.getCityName());

        // HttpHeaders headers = Common.getHeaders(savedCity.getId(), "/cities/");

        // return new ResponseEntity<>(desc, headers, HttpStatus.OK);

        return savedCity.getId();

    }

    // public ResponseEntity<String> deleteCity(@NonNull UUID id) {
    public UUID deleteCity(@NonNull UUID id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        cityRepository.deleteById(id);

        // String desc = String.format("City with id = %s successfully deleted", id);

        // HttpHeaders headers = Common.getHeaders(id, "/cities/");

        // return new ResponseEntity<>(desc, headers, HttpStatus.OK);

        return id;
    }
}
