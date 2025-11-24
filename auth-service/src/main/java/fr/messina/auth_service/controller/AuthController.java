package fr.messina.auth_service.controller;

// Removed the unused import to resolve the collision
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.messina.auth_service.dto.AuthRequest;
import fr.messina.auth_service.model.UserCredential;
import fr.messina.auth_service.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return authService.saveUser(user);
    }

    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequest request){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authenticate.isAuthenticated()){
            return authService.generateToken(request.getUsername());
        }else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is valid";
    }
}
