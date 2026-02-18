package org.example._7_02.controllers;

import org.example._7_02.dto.LoginDTO;
import org.example._7_02.dto.LoginResDTO;
import org.example._7_02.dto.UserDTO;
import org.example._7_02.entities.User;
import org.example._7_02.exceptions.ValidationExceptions;
import org.example._7_02.services.AuthService;
import org.example._7_02.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthControllers {
    private final AuthService as;
    private final UserService us;

    public AuthControllers(AuthService as, UserService us) {
        this.as = as;
        this.us = us;
    }

    @PostMapping("/login")
    public LoginResDTO login(@RequestBody LoginDTO body) {

        return new LoginResDTO(this.as.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated UserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(errorsList);
        } else {
            return this.us.save(payload);
        }

    }
}

