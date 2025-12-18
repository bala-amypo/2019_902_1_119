
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class PortfolioHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserPortfolio portfolio;

    @ManyToOne
    private Stock stock;

    private Double quantity;
    private BigDecimal marketValue;
    private Timestamp lastUpdated;

    @PrePersist @PreUpdate
    public void updateTime() {
        lastUpdated = new Timestamp(System.currentTimeMillis());
    }

    // getters & setters
}