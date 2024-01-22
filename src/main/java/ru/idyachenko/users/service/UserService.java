package ru.idyachenko.users.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.idyachenko.users.entity.User;
import ru.idyachenko.users.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> createUser(@NonNull User user) {
        User savedUser = userRepository.save(user);

        String desc = String.format("Пользователь %s добавлен в базу с id = %s", savedUser.getLname(),
                savedUser.getId());
        HttpHeaders headers = Common.getHeaders(savedUser.getId(), "/users/");
        return new ResponseEntity<>(desc, headers, HttpStatus.CREATED);
    }

    public User getUser(@NonNull UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> updateUser(User user, @NonNull UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!user.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User savedUser = userRepository.save(user);
        String desc = String.format("Пользователь %s успешно сохранен", savedUser.getLname());

        HttpHeaders headers = Common.getHeaders(savedUser.getId(), "/users/");

        return new ResponseEntity<>(desc, headers, HttpStatus.OK);

    }

    public ResponseEntity<String> deleteUser(@NonNull UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);

        HttpHeaders headers = Common.getHeaders(id, "/users/");

        String desc = String.format("Пользователь с id = %s успешно удален", id);

        return new ResponseEntity<>(desc, headers, HttpStatus.OK);

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
