package fr.messina.crypto_service.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.messina.crypto_service.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    Optional <List<Transaction>> findTransactionByPortfolioId(int portfolioId);
    Optional <List<Transaction>> findTransactionByCryptoId(int cryptoId);
    Optional <List<Transaction>> findTransactionByPortfolioIdAndCryptoId(int portfolioId, int cryptoId);
}