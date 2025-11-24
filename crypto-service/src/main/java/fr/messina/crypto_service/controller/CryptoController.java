package fr.messina.crypto_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.messina.crypto_service.dto.PriceDTO;
import fr.messina.crypto_service.model.Crypto;
import fr.messina.crypto_service.service.CryptoService;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;


    @GetMapping("/all")
    public List<Crypto> getCryptos(){
        return cryptoService.getAllCryptos();
    }

    @GetMapping("/price")
    public PriceDTO getPriceBySymbol(@RequestParam String symbol){
        return cryptoService.getPricerBySymbol(symbol);
    }
    
}
