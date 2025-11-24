package fr.messina.crypto_service.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.messina.crypto_service.model.Crypto;

public interface CryptoRepository extends JpaRepository<Crypto, Integer>{

    Optional <Crypto> findBySymbol(String symbol);
}