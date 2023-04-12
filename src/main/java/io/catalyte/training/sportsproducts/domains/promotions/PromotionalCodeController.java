package io.catalyte.training.sportsproducts.domains.promotions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The {@code PromotionalCodeController} class is a REST controller that handles HTTP requests
 * related to promotional codes.
 */
@RestController
public class PromotionalCodeController {

    private final PromotionalCodeService promotionalCodeService;

    /**
     * Creates a new {@code PromotionalCodeController} instance with the specified
     * {@code PromotionalCodeService} instance.
     *
     * @param promotionalCodeService the {@code PromotionalCodeService} instance to use
     */
    public PromotionalCodeController(PromotionalCodeService promotionalCodeService) {
        this.promotionalCodeService = promotionalCodeService;
    }

    /**
     * Handles HTTP POST requests to create a new promotional code with the specified data.
     * The request body must contain a valid JSON representation of the promotional code DTO.
     * If the promotional code is created successfully, a 201 CREATED response with the
     * persisted object is returned. Otherwise, an error response with a 400 BAD REQUEST status code
     * is returned.
     *
     * @param dto the DTO containing the promotional code data to create
     * @return a {@code ResponseEntity} containing the created promotional code and the HTTP status code
     * @throws PromotionalCodeService.DuplicatePromoCodeException if the promotional code already exists
     */
    @PostMapping("/promotional-codes")
    public ResponseEntity<PromotionalCode> createPromotionalCode(@Valid @RequestBody PromotionalCodeDTO dto) throws PromotionalCodeService.DuplicatePromoCodeException {
        PromotionalCode promotionalCode = promotionalCodeService.createPromotionalCode(dto);
        return new ResponseEntity<>(promotionalCode, HttpStatus.CREATED);
    }
}
