package fr.messina.crypto_service.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Crypto {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String symbol;

    private String name;
    
    private float price;

    public Crypto(String symbol, String name, float price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}