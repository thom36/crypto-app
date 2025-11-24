package fr.messina.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.messina.auth_service.model.UserCredential;

public interface UserCredentialRepository  extends JpaRepository<UserCredential, Integer>{

    Optional<UserCredential> findByName(String userName);
}
