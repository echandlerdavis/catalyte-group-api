package io.catalyte.training.sportsproducts.domains.promotions;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class PromotionalCode {

    /**
     * Unique identifier for the promotional code.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  Title of the promotional code.
     */
    @Column(unique = true)
    private String title;

    /**
     *  Description of the promotional code.
     */
    private String description;

    /**
     *  Type of promotional code (flat or percent).
     */
    @Enumerated(EnumType.STRING)
    private PromotionalCodeType type;

    /**
     * Rate of the promotional code (either a flat dollar amount or a percentage off).
     */
    private BigDecimal rate;

    /**
     *  Default constructor for JPA.
     */
    public PromotionalCode() {}

    /**
     *  Constructor for creating a new promotional code.
     *
     * @param title  Title of the promotional code.
     * @param description  Description of the promotional code.
     * @param type  Type of the promotional code (flat or percent).
     * @param rate  Rate of the promotional code (either a flat dollar amount or a percentage off).
     */
    public PromotionalCode(String title, String description, PromotionalCodeType type, BigDecimal rate) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.rate = rate;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PromotionalCodeType getType() {
        return type;
    }

    public void setType(PromotionalCodeType type) {
        this.type = type;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
