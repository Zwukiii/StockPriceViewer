package portfolio;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name ="stocks")
@Getter
@Setter

public class StockEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String ticket;

    @NotBlank
    @Column(nullable = false)
    private String companyName;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal currentPrice;

    @NotNull
    private LocalDateTime lastUpdated;

}
