package fr.messina.crypto_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.messina.crypto_service.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Integer>{
    Optional <List<Asset>> findByPortfolioId(int portfolioId);
    Optional<Asset> findByPortfolioIdAndCryptoId(int portfolioId, int cryptoId);
}
