package io.catalyte.training.sportsproducts.domains.promotions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service class provides functionality related to promotional codes.
 */
@Service
public class PromotionalCodeService {

    private PromotionalCodeRepository promotionalCodeRepository = null;

    /**
     * Constructs a PromotionalCodeService object with the given PromotionalCodeRepository.
     */
    @Autowired
    public PromotionalCodeService(PromotionalCodeRepository promotionalCodeRepository) {
        this.promotionalCodeRepository = promotionalCodeRepository;
    }

    public PromotionalCodeService() {

    }

    /**
     * Creates a new promotional code using the information in the given PromotionalCodeDTO.
     *
     * @param promotionalCodeDTO The PromotionalCodeDTO containing the information for the new promotional code.
     * @return The created PromotionalCode.
     */
    public PromotionalCode createPromotionalCode(PromotionalCodeDTO promotionalCodeDTO) throws PromotionalCodeServiceImpl. InvalidPromoCodeException {
        if (promotionalCodeDTO.getType() == null || promotionalCodeDTO.getRate() == null) {
            return null;
        }

        PromotionalCode promotionalCode = new PromotionalCode();
        return promotionalCodeRepository.save(promotionalCode);
    }

    public List<PromotionalCode> getAllPromotionalCodes() {
        return promotionalCodeRepository.findAll();
    }

    public class InvalidPromoCodeException extends Exception {
    }
}