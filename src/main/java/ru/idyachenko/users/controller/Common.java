package ru.idyachenko.users.controller;

import java.util.UUID;

import org.springframework.http.HttpHeaders;

import ru.idyachenko.users.entity.SubscriptionId;
import ru.idyachenko.users.entity.UserSkillId;

public class Common {
    public static HttpHeaders getHeaders(UUID id, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", path + id);
        headers.set("X-UserId", String.valueOf(id));
        return headers;
    }

    public static HttpHeaders getHeaders(SubscriptionId id, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", path + id);
        headers.set("X-UserId", String.valueOf(id));
        return headers;
    }

    public static HttpHeaders getHeaders(UserSkillId id, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", path + id);
        headers.set("X-UserId", String.valueOf(id));
        return headers;
    }
}
