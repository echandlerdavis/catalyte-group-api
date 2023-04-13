package io.catalyte.training.sportsproducts.domains.promotions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Implements the {@link PromotionalCodeService} interface and provides methods to create and retrieve promotional codes.
 */
@Service
public class PromotionalCodeServiceImpl extends PromotionalCodeService {

    private final PromotionalCodeRepository promotionalCodeRepository;

    @Autowired
    public PromotionalCodeServiceImpl(PromotionalCodeRepository promotionalCodeRepository) {
        super();
        this.promotionalCodeRepository = promotionalCodeRepository;
    }

    /**
     * Creates a new promotional code with the given DTO.
     *
     * @param dto The DTO containing the promotional code data.
     * @return The created promotional code entity.
     */
    public PromotionalCode createPromotionalCode(PromotionalCodeDTO dto) throws InvalidPromoCodeException {
        PromotionalCode promotionalCode = new PromotionalCode();
        promotionalCode.setTitle(dto.getTitle());
        promotionalCode.setDescription(dto.getDescription());
        promotionalCode.setType(dto.getType());
        promotionalCode.setRate(dto.getRate());

        // validate the promotional code before saving
        validatePromotionalCode(promotionalCode);

        return promotionalCodeRepository.save(promotionalCode);
    }

    /**
     * Validates that the given promotional code entity is valid.
     *
     * @param promotionalCode The promotional code entity to validate.
     * @throws InvalidPromoCodeException if the given promotional code is invalid.
     */
    private void validatePromotionalCode(PromotionalCode promotionalCode) throws InvalidPromoCodeException {
        if (promotionalCode.getType() == null || promotionalCode.getRate() == null) {
            throw new InvalidPromoCodeException();
        }

        if (promotionalCode.getType() == PromotionalCodeType.FLAT && promotionalCode.getRate().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPromoCodeException();
        }

        if (promotionalCode.getType() == PromotionalCodeType.PERCENT && (promotionalCode.getRate().compareTo(BigDecimal.ZERO) <= 0 || promotionalCode.getRate().compareTo(BigDecimal.valueOf(100)) > 0)) {
            throw new InvalidPromoCodeException();
        }
    }

    /**
     * Applies the given promotional code to the given price and returns the discounted price.
     *
     * @param title The title of the promotional code to apply.
     * @param price The original price before the discount.
     * @return The discounted price.
     */
    public double applyPromotionalCode(String title, double price) {
        PromotionalCode promotionalCode = (PromotionalCode) promotionalCodeRepository.findByTitle(title);
        if (promotionalCode == null) {
            return price;
        }

        if (promotionalCode.getType() == PromotionalCodeType.FLAT) {
            return price - promotionalCode.getRate().doubleValue();
        } else if (promotionalCode.getType() == PromotionalCodeType.PERCENT) {
            return price * (1 - promotionalCode.getRate().doubleValue());
        } else {
            return price;
        }
    }
}
class InvalidPromoCodeException extends Exception {
}

