package io.catalyte.training.sportsproducts.domains.promotions;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PromotionalCodeDTO {

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Type is required")
    private PromotionalCodeType type;

    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.01", message = "Rate must be greater than or equal to 0.01")
    private BigDecimal rate;

    // constructor, getters and setters


    // getters and setters
}