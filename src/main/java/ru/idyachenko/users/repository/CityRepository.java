package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;

import ru.idyachenko.users.entity.City;
import java.util.UUID;

public interface CityRepository extends CrudRepository<City, UUID> {
}
