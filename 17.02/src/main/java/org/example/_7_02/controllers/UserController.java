package org.example._7_02.controllers;

import org.example._7_02.dto.RoleDTO;
import org.example._7_02.dto.UserDTO;
import org.example._7_02.entities.User;
import org.example._7_02.exceptions.ValidationExceptions;
import org.example._7_02.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public Page<User> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size,
                              @RequestParam(defaultValue = "username") String orderBy,
                              @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.us.findAll(page, size, orderBy, sortCriteria);
    }// http://localhost:3001/users

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{id}")
    public User FindById(@PathVariable String id) {
        return this.us.findById(id);
    }// http://localhost:3001/users/{id}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated UserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(errorsList);
        } else {
            return this.us.save(payload);
        }
    }// http://localhost:3001/users

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public User findByIdAndUpdate(@PathVariable String id, @RequestBody @Validated UserDTO payload) {
        return this.us.findByIdAndUpadte(id, payload);
    }// http://localhost:3001/users/{id}

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        this.us.deleteById(id);
    }// http://localhost:3001/users/{id}

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PatchMapping("/{id}/role")
    public User setRoleById(@PathVariable String id, @RequestBody RoleDTO role) {
        return this.us.setRoleById(id, role);
    }// http://localhost:3001/users/{id}/role

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody UserDTO payload) {
        return this.us.findByIdAndUpadte(currentAuthenticatedUser.getId(), payload);
    }// http://localhost:3001/users/me

    @DeleteMapping("/me")
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.us.deleteById(currentAuthenticatedUser.getId());
    }// http://localhost:3001/users/me
}
