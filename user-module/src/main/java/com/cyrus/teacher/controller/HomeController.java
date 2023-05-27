package com.cyrus.teacher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class HomeController {


    @RequestMapping("/all")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("OK TEACHER MADE PERFECT ");
    }
}
