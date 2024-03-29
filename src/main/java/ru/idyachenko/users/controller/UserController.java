package ru.idyachenko.users.controller;

import java.util.List;
import java.util.UUID;
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
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    // String createUser(@RequestBody @NonNull User user) {
    ResponseEntity<String> createUser(@RequestBody @NonNull User user) {
        return userService.createUser(user);
    }

    @GetMapping(path = "/{id}")
    User getUser(@PathVariable @NonNull UUID id) {
        return userService.getUser(id);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable @NonNull UUID id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteUser(@PathVariable @NonNull UUID id) {
        return userService.deleteUser(id);
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }
}
