package ru.idyachenko.users.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.idyachenko.users.entity.City;
import ru.idyachenko.users.service.CityService;

@RestController
@RequestMapping(value = "/cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    List<City> getCities() {
        return cityService.getCities();
    }

    @PostMapping
    ResponseEntity<Object> createCity(@RequestBody @NonNull City city) {
        // return cityService.createCity(city);
        UUID savedCityId = cityService.createCity(city);
        // String desc = String.format("City %s added to the database with id = %s",
        // city.getCityName(),
        // savedCityId);

        HttpHeaders headers = Common.getHeaders(savedCityId, "/cities/");

        return new ResponseEntity<>(savedCityId, headers, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    City getCity(@PathVariable @NonNull UUID id) {
        return cityService.getCity(id);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<String> updateCity(@RequestBody City city, @PathVariable @NonNull UUID id) {
        // return cityService.updateCity(city, id);
        UUID cityId = cityService.updateCity(city, id);
        String desc = String.format("City %s successfully updated", city.getCityName());

        HttpHeaders headers = Common.getHeaders(cityId, "/cities/");

        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteCity(@PathVariable @NonNull UUID id) {
        // return cityService.deleteCity(id);
        UUID cityId = cityService.deleteCity(id);

        String desc = String.format("City with id = %s successfully deleted", cityId);

        HttpHeaders headers = Common.getHeaders(cityId, "/cities/");

        return new ResponseEntity<>(desc, headers, HttpStatus.OK);
    }
}
