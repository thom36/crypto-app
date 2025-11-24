package fr.messina.crypto_service.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crypto_id", referencedColumnName = "id")
    private Crypto crypto;

    @Positive
    private float amount;

    @Positive
    private float unitaryprice;

    @Enumerated(EnumType.STRING) // Stocke l'enum sous forme de chaîne (ex : "BUY", "SELL")
    private TransactionType type;

    private Date date;
}