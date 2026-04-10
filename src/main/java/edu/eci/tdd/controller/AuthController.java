package edu.eci.tdd.controller;

import edu.eci.tdd.model.AuthRequest;
import edu.eci.tdd.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String createToken(@RequestBody AuthRequest authRequest) throws Exception {
        // 1. Validar usuario y contraseña con el manager de Spring
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // 2. Si es válido, cargar los detalles y generar el token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        
        // Obtenemos el rol para meterlo en el token
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        
        return jwtUtil.generateToken(userDetails.getUsername(), role);
    }
}
