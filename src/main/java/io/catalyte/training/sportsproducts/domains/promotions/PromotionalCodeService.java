package io.catalyte.training.sportsproducts.domains.promotions;


/**
 * This interface provides an abstraction layer for the Promotional Code Service
 */
public interface PromotionalCodeService {

    PromotionalCode createPromotionalCode(PromotionalCodeDto promotionalCodeDto);
}
