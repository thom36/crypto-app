package fr.messina.crypto_service.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.messina.crypto_service.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>{

    Optional <Portfolio> findPortfolioByUserId(int userId);
}
