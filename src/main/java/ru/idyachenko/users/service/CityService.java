package ru.idyachenko.users.service;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.City;
import ru.idyachenko.users.repository.CityRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public String createCity(@NonNull City city) {
        City savedCity = cityRepository.save(city);
        return String.format("City %s added to the database with id = %s", savedCity.getCityName(), savedCity.getId());
    }

    public City getCity(@NonNull UUID id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateCity(City city, @NonNull UUID id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!city.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        City savedCity = cityRepository.save(city);
        return String.format("City %s successfully updated", savedCity.getCityName());
    }

    public String deleteCity(@NonNull UUID id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        cityRepository.deleteById(id);
        return String.format("City with id = %s successfully deleted", id);
    }
}
