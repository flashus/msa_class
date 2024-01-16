package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import ru.idyachenko.users.entity.City;
// import ru.idyachenko.users.entity.User;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends CrudRepository<City, UUID> {
    List<City> findListByCityName(@NonNull String cityName);

    @NonNull
    List<City> findAll();
}
