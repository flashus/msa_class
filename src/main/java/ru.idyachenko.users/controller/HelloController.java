package ru.idyachenko.users.controller;
//package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {

        return "Hello world from users service!";

    }

}
