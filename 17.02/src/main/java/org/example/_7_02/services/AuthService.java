package org.example._7_02.services;

import org.example._7_02.dto.LoginDTO;
import org.example._7_02.entities.User;
import org.example._7_02.exceptions.UnauthorizedException;
import org.example._7_02.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService us;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    @Autowired
    public AuthService(UserService us, JWTTools jwtTools, PasswordEncoder bcrypt) {

        this.us = us;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        User f = this.us.findByEmail(body.email());
        if (bcrypt.matches(body.password(), f.getPassword())) {
            String Token = jwtTools.generateToken(f);
            return Token;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }


    }

}

