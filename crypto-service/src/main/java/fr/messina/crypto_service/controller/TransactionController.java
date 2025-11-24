package fr.messina.crypto_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.messina.crypto_service.model.Transaction;
import fr.messina.crypto_service.service.TransactionService;

@RestController
@RequestMapping("/crypto/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    public List<Transaction> getAllTransactions(@RequestHeader("X-User-Id") String id){
        int userId = Integer.parseInt(id);
        return transactionService.getTransactionByUserId(userId);
    }

    @GetMapping("/crypto")
    public List<Transaction> getTransactionsByCrypto(@RequestHeader("X-User-Id") String id, @RequestParam String symbol){
        int userId = Integer.parseInt(id);
        return transactionService.getTransactionByCryptoSymbol(symbol, userId);
    }

    @PostMapping("/buy")
    public String buyCrypto(@RequestHeader("X-User-Id") String id, @RequestParam String symbol, @RequestParam Float quantity){
        int userId = Integer.parseInt(id);
        return transactionService.buyCrypto(symbol, quantity, userId);
    }

    @PostMapping("/sell")
    public String sellCrypto(@RequestHeader("X-User-Id") String id, @RequestParam String symbol, @RequestParam Float quantity){
        int userId = Integer.parseInt(id);
        return transactionService.sellCrypto(symbol, quantity, userId);
    }
}
