package com.scouts.backlibrodeoro.controller;

import com.scouts.backlibrodeoro.auth.JWTGenerator;
import com.scouts.backlibrodeoro.dto.AuthDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping
    public AuthDTO login(@RequestBody AuthDTO authDTO) {
        String token = JWTGenerator.getJWTToken(authDTO.getUsuario());
        authDTO.setToken(token);
        return authDTO;
    }

}
