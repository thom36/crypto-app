package fr.messina.crypto_service.ConfigDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.messina.crypto_service.model.Crypto;
import fr.messina.crypto_service.repository.CryptoRepository;

@Component
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cryptoRepository.count() == 0) {
            Crypto bitcoin = new Crypto("BTC", "bitcoin", 100);
            Crypto ethereum = new Crypto("ETH", "ethereum", 30);

            cryptoRepository.save(bitcoin);
            cryptoRepository.save(ethereum);
            
            System.out.println("Database Seeded!");
        } else {
            System.out.println("Database already contains data. Skipping seeding.");
        }
    }
}

