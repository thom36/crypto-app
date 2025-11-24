package fr.messina.crypto_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.messina.crypto_service.model.Portfolio;
import fr.messina.crypto_service.service.PortfolioService;

@RestController
@RequestMapping("/crypto/portfolio")
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/create")
    public Portfolio createPortfolio(@RequestBody Map<String, Object> payload) {
        int userId = (int) payload.get("userId");
        return portfolioService.createPortfolio(userId);
    }

    @GetMapping("/value")
    public Float getPortfolioByUserId(@RequestHeader("X-User-Id") String id){
        int userId = Integer.parseInt(id);
        return portfolioService.getPortfolioValueByUserId(userId);
    }

    @GetMapping("/compo")
    public Map<String, Float> getPortfolioCompo(@RequestHeader("X-User-Id") String id){
        int userId = Integer.parseInt(id);
        return portfolioService.getPortfolioCompoByUserId(userId);
    }

}
