package io.catalyte.training.sportsproducts.domains.promotions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PromotionalCodeDTO {

    /**
     * Title of the promotional code.
     */
    @NotBlank(message = "Title is required")
    private String title;

    /**
     * Description of the promotional code.
     */
    @NotBlank
    private String description;

    /**
     * Type of the promotional code (flat or percent).
     */
    @NotNull
    private PromotionalCodeType type;

    /**
     * Rate of the promotional code (either a flat dollar amount or a percentage off).
     */
    @NotNull
    private BigDecimal rate;

    // getters and setters
}