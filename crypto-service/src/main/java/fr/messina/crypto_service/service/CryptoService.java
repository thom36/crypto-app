package fr.messina.crypto_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.messina.crypto_service.dto.PriceDTO;
import fr.messina.crypto_service.model.Crypto;
import fr.messina.crypto_service.repository.CryptoRepository;

@Service
public class CryptoService {
    @Autowired
    private CryptoRepository cryptoRepository;

    public List<Crypto> getAllCryptos() {
        return cryptoRepository.findAll();
    }

    public PriceDTO getPricerBySymbol(String symbol) {
        Crypto crypto = cryptoRepository.findBySymbol(symbol).orElse(null);
        if(crypto == null){
            throw new RuntimeException("symbol not found");
        }
        PriceDTO res = new PriceDTO(crypto.getSymbol(), crypto.getPrice());
        return res;
    }
}