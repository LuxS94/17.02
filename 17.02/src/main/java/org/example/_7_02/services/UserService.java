package org.example._7_02.services;

import org.example._7_02.dto.RoleDTO;
import org.example._7_02.dto.UserDTO;
import org.example._7_02.entities.User;
import org.example._7_02.exceptions.AlreadyExsists;
import org.example._7_02.exceptions.NotFoundException;
import org.example._7_02.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo ur;
    private final PasswordEncoder bcrypt;

    @Autowired
    public UserService(UserRepo ur, PasswordEncoder passwordEncoder) {
        this.ur = ur;
        this.bcrypt = passwordEncoder;
    }

    public Page<User> findAll(int page, int size, String orderBy, String sortCriteria) {
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.ur.findAll(pageable);
    }

    public User findById(String id) {
        return this.ur.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public User findByEmail(String email) {
        return this.ur.findByEmail(email).orElseThrow(() -> new NotFoundException("La mail non è registrata!"));
    }

    public User save(UserDTO payload) {
        User nUser = new User(payload.username(), payload.email(), bcrypt.encode(payload.password()));
        this.ur.findByEmail(payload.email()).ifPresent(u -> {
            throw new AlreadyExsists("La mail è già registrata");
        });
        return this.ur.save(nUser);
    }

    public User findByIdAndUpadte(String id, UserDTO payload) {
        User f = this.findById(id);
        f.setEmail(payload.email());
        f.setUsername(payload.username());
        f.setPassword(payload.password());
        this.ur.findByEmail(payload.email()).ifPresent(u -> {
            throw new AlreadyExsists("La mail è già registrata");
        });
        this.ur.findByUsername(payload.username()).ifPresent(u -> {
            throw new AlreadyExsists("L'username esiste già !");
        });
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
