package io.catalyte.training.sportsproducts.domains.promotions;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class PromotionalCode {

    /**
     * Unique identifier for the promotional code.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (unique = true)
    private String code;

    /**
     *  Title of the promotional code.
     */
    @NotBlank(message = "Title is required")
    private String title;

    /**
     *  Description of the promotional code.
     */
    @NotBlank(message = "Description is required")
    private String description;

    /**
     *  Type of promotional code (flat or percent).
     */
    @NotBlank(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private PromotionalCodeType type;

    /**
     * Rate of the promotional code (either a flat dollar amount or a percentage off).
     */
    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.01", message = "Rate must be greater than or equal to 0.01")
    private BigDecimal rate;

    // constructor, getters and setters
}
