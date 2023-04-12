package io.catalyte.training.sportsproducts.domains.promotions;

import java.math.BigDecimal;

/**
 * This class represents a promotional code, which is used to offer discounts
 * to customers when purchasing products.
 */
public class PromotionalCode {

    private String title;
    private String description;
    private PromotionalCodeType type;
    private BigDecimal rate;

    /**
     * Constructs a new PromotionalCode object with the specified title, description,
     * type, and rate.
     *
     * @param title the title of the promotional code
     * @param description the description of the promotional code
     * @param type the type of the promotional code (either percent or flat)
     * @param rate the rate of the promotional code (a percentage or flat dollar amount)
     */
    public PromotionalCode(String title, String description, PromotionalCodeType type, BigDecimal rate) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.rate = rate;
    }

    public PromotionalCode() {

    }

    /**
     * Returns the title of the promotional code.
     *
     * @return the title of the promotional code
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the promotional code.
     *
     * @param title the new title of the promotional code
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the promotional code.
     *
     * @return the description of the promotional code
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the promotional code.
     *
     * @param description the new description of the promotional code
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the type of the promotional code.
     *
     * @return the type of the promotional code
     */
    public PromotionalCodeType getType() {
        return type;
    }

    /**
     * Sets the type of the promotional code.
     *
     * @param type the new type of the promotional code
     */
    public void setType(PromotionalCodeType type) {
        this.type = type;
    }

    /**
     * Returns the rate of the promotional code.
     *
     * @return the rate of the promotional code
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the rate of the promotional code.
     *
     * @param rate the new rate of the promotional code
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}