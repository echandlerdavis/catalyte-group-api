package io.catalyte.training.sportsproducts.domains.promotions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service class provides functionality related to promotional codes.
 */
@Service
public class PromotionalCodeService {

    private final PromotionalCodeRepository promotionalCodeRepository;

    /**
     * Constructs a PromotionalCodeService object with the given PromotionalCodeRepository.
     */
    @Autowired
    public PromotionalCodeService() {
        this.promotionalCodeRepository = promotionalCodeRepository;
    }

    /**
     * Creates a new promotional code using the information in the given PromotionalCodeDTO.
     *
     * @param promotionalCodeDTO The PromotionalCodeDTO containing the information for the new promotional code.
     * @return The created PromotionalCode.
     */
    public PromotionalCode createPromotionalCode(PromotionalCodeDTO promotionalCodeDTO) throws PromotionalCodeServiceImpl.InvalidPromoCodeException {
        if (promotionalCodeDTO.getType() == null || promotionalCodeDTO.getRate() == null) {
            return null;
        }

        PromotionalCode promotionalCode = new PromotionalCode(promotionalCodeDTO.getTitle(),
                promotionalCodeDTO.getDescription(),
                promotionalCodeDTO.getType(),
                promotionalCodeDTO.getRate());
        return promotionalCodeRepository.save(promotionalCode);
    }

}