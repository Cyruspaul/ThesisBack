package com.cyrus.controller;

import com.cyrus.config.APIResponse;
import com.cyrus.models.DTO.UserModelDTO;
import com.cyrus.models.DTO.UserRequestDTO;
import com.cyrus.service.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private final AuthServices authServices;

    @RequestMapping("/login")
    public APIResponse<?> login(@RequestBody UserRequestDTO userRequestDTO) {
        return authServices.login(userRequestDTO);
    }

    @RequestMapping("/register")
    public APIResponse<?> register(@RequestBody UserModelDTO userModelDTO) {
        return authServices.registerNewUSer(userModelDTO);
    }


    @RequestMapping("/verifyToken")
    public APIResponse<?> verifyToken(@RequestParam("token") String token) {
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authServices.verifyToken(token);
    }
}
