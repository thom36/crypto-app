package fr.messina.crypto_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "crypto_id", referencedColumnName = "id", nullable = false)
    private Crypto crypto;

    @PositiveOrZero
    private float quantity;
}