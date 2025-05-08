package com.safarov.techapp.controller;

import com.safarov.techapp.dto.request.UserRequestDTO;
import com.safarov.techapp.entity.TechUser;
import com.safarov.techapp.repositories.UserRepository;
import com.safarov.techapp.service.UserRegisterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserRegisterService userRegisterService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userRegisterService.saveUser(userRequestDTO), HttpStatus.CREATED);
    }
}
