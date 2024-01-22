package ru.idyachenko.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.idyachenko.users.entity.City;
import ru.idyachenko.users.service.CityService;

import java.util.List;
import java.util.UUID;

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
    ResponseEntity<String> createCity(@RequestBody @NonNull City city) {
        return cityService.createCity(city);
    }

    @GetMapping(path = "/{id}")
    City getCity(@PathVariable @NonNull UUID id) {
        return cityService.getCity(id);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<String> updateCity(@RequestBody City city, @PathVariable @NonNull UUID id) {
        return cityService.updateCity(city, id);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteCity(@PathVariable @NonNull UUID id) {
        return cityService.deleteCity(id);
    }
}
