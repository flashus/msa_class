package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;
import ru.idyachenko.users.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findByLname(String lname);
    List<User> findByEmail(String email);
}
