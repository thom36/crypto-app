package fr.messina.auth_service.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.messina.auth_service.model.UserCredential;
import fr.messina.auth_service.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final RestTemplate restTemplate;


    public String saveUser(UserCredential user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        restTemplate.postForObject(
            "http://crypto-service/crypto/portfolio/create",
            Map.of("userId", user.getId()),
            Void.class
    );
        return "User added to the system";
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
