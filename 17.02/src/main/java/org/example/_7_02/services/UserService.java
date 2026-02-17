package org.example._7_02.services;

import org.example._7_02.dto.RoleDTO;
import org.example._7_02.dto.UserDTO;
import org.example._7_02.entities.User;
import org.example._7_02.exceptions.NotFoundException;
import org.example._7_02.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo ur;

    @Autowired
    public UserService(UserRepo ur) {
        this.ur = ur;
    }

    public Page<User> findAll(int page, int size, String orderBy, String sortCriteria) {
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.ur.findAll(pageable);
    }

    public User findById(String id) {
        return this.ur.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public User save(UserDTO payload) {
        User nUser = new User(payload.username(), payload.email(), payload.password());
        return this.ur.save(nUser);
    }

    public User findByIdAndUpadte(String id, UserDTO payload) {
        User f = this.findById(id);
        f.setEmail(payload.email());
        f.setUsername(payload.username());
        f.setPassword(payload.password());
        return this.ur.save(f);
    }

    public void deleteById(String id) {
        User f = this.findById(id);
        this.ur.delete(f);

    }

    public User setRoleById(String id, RoleDTO role) {
        User f = this.findById(id);
        f.setRole(role.role());
        return this.ur.save(f);
    }
}
