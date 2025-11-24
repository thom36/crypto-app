package fr.messina.crypto_service.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.messina.crypto_service.model.Asset;
import fr.messina.crypto_service.model.Crypto;
import fr.messina.crypto_service.model.Portfolio;
import fr.messina.crypto_service.model.Transaction;
import fr.messina.crypto_service.model.TransactionType;
import fr.messina.crypto_service.repository.AssetRepository;
import fr.messina.crypto_service.repository.CryptoRepository;
import fr.messina.crypto_service.repository.PortfolioRepository;
import fr.messina.crypto_service.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired 
    private CryptoRepository cryptoRepository;

    @Autowired AssetRepository assetRepository;

    public List<Transaction> getTransactionByUserId(int userId){
        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(userId).orElse(null);
        if(portfolio == null){
            throw new RuntimeException("user doesn't exist");
        }

        List<Transaction> transactions = transactionRepository.findTransactionByPortfolioId(portfolio.getId()).orElse(List.of());

        return transactions;
    }

    public List<Transaction> getTransactionByCryptoSymbol(String symbol, int userId){
        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(userId).orElse(null);
        if(portfolio == null){
            throw new RuntimeException("user doesn't exist");
        }

        Crypto crypto = cryptoRepository.findBySymbol(symbol).orElse(null);
        if(crypto == null){
            throw new RuntimeException("crypto doesn't exist");
        }

        List<Transaction> transactions = transactionRepository.findTransactionByPortfolioIdAndCryptoId(portfolio.getId(), crypto.getId()).orElse(List.of());

        return transactions;
    }

    public String buyCrypto(String symbol, Float quantity, int userId){
        Crypto crypto = cryptoRepository.findBySymbol(symbol).orElse(null);
        if(crypto == null){
            throw new RuntimeException("crypto doesn't exist");
        }

        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(userId).orElse(null);
        if(portfolio == null){
            throw new RuntimeException("user doesn't exist");
        }

        if(crypto.getPrice() * quantity > portfolio.getBalance()){
            throw new RuntimeException("not enough money");
        }

        Asset asset = assetRepository.findByPortfolioIdAndCryptoId(portfolio.getId(), crypto.getId()).orElse(null);
        if(asset != null){
            asset.setQuantity(asset.getQuantity() + quantity);
            assetRepository.save(asset);
        } else{
            Asset newAsset = new Asset();
            newAsset.setPortfolio(portfolio);
            newAsset.setCrypto(crypto);
            newAsset.setQuantity(quantity);
            assetRepository.save(newAsset);
        }

        portfolio.setBalance(portfolio.getBalance() - crypto.getPrice() * quantity);
        portfolioRepository.save(portfolio);

        Transaction newTransaction = new Transaction();
        newTransaction.setPortfolio(portfolio);
        newTransaction.setCrypto(crypto);
        newTransaction.setType(TransactionType.BUY);
        newTransaction.setAmount(quantity);
        newTransaction.setUnitaryprice(crypto.getPrice());
        newTransaction.setDate(new Date());

        return "Crypto added";

    }

    public String sellCrypto(String symbol, Float quantity, int userId){
        Crypto crypto = cryptoRepository.findBySymbol(symbol).orElse(null);
        if(crypto == null){
            throw new RuntimeException("crypto doesn't exist");
        }

        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(userId).orElse(null);
        if(portfolio == null){
            throw new RuntimeException("user doesn't exist");
        }

        Asset asset = assetRepository.findByPortfolioIdAndCryptoId(portfolio.getId(), crypto.getId()).orElse(null);
        if(asset == null){
            throw new RuntimeException("You don't have this crypto");
        } else{
            if(asset.getQuantity() < quantity){
                throw new RuntimeException("You don't have enough crypto");
            }
        }

        asset.setQuantity(asset.getQuantity() - quantity);
        assetRepository.save(asset);

        portfolio.setBalance(portfolio.getBalance() + quantity * crypto.getPrice());
        portfolioRepository.save(portfolio);

        Transaction newTransaction = new Transaction();
        newTransaction.setPortfolio(portfolio);
        newTransaction.setCrypto(crypto);
        newTransaction.setType(TransactionType.SELL);
        newTransaction.setAmount(quantity);
        newTransaction.setUnitaryprice(crypto.getPrice());
        newTransaction.setDate(new Date());

        return "Crypto sold";
    }
    
}
