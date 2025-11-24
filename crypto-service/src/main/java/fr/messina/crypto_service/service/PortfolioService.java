package fr.messina.crypto_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.messina.crypto_service.model.Asset;
import fr.messina.crypto_service.model.Crypto;
import fr.messina.crypto_service.model.Portfolio;
import fr.messina.crypto_service.repository.AssetRepository;
import fr.messina.crypto_service.repository.PortfolioRepository;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private AssetRepository assetRepository;

    public Portfolio createPortfolio(int userId){
        if (portfolioRepository.findPortfolioByUserId(userId).isPresent()) {
            return portfolioRepository.findPortfolioByUserId(userId).get();
        }

        Portfolio p = new Portfolio();
        p.setUserId(userId);
        p.setBalance(1000);

        return portfolioRepository.save(p);
    }

    public Float getPortfolioValueByUserId(int userId) {
        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(userId).orElse(null);
        if(portfolio == null){
            throw new RuntimeException("user doesn't exist");
        }

        List<Asset> assets = assetRepository.findByPortfolioId(portfolio.getId()).orElse(List.of());

        Float portfolioValue = portfolio.getBalance();
        for(Asset asset : assets){
            Crypto crypto = asset.getCrypto();
            portfolioValue += crypto.getPrice() * asset.getQuantity();
        }

        return portfolioValue;
    }

    public Map<String, Float> getPortfolioCompoByUserId(int userId){
        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(userId).orElse(null);
        if(portfolio == null){
            throw new RuntimeException("user doesn't exist");
        }

        List<Asset> assets = assetRepository.findByPortfolioId(portfolio.getId()).orElse(List.of());

        Map<String, Float> res = new HashMap<>();
        
        for(Asset asset : assets){
            Crypto crypto = asset.getCrypto();
            res.put(crypto.getSymbol(), asset.getQuantity());
        }
        return res;
    }
}