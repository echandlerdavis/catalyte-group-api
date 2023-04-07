package io.catalyte.training.sportsproducts.domains.promotions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

    /**
     *  Exposes endpoints for the 'promotions' domain.
     */
    @RestController
    @RequestMapping("/promotional-codes")
    public class PromotionalCodeController {

        private final PromotionalCodeService promotionalCodeService;

        /**
         * Constructor for injecting the promotional code service dependency.
         *
         * @param promotionalCodeService Promotional code service.
         */
        public PromotionalCodeController(PromotionalCodeService promotionalCodeService) {
            this.promotionalCodeService = promotionalCodeService;
        }

        /**
         * Endpoint for creating a new promotional code.
         *
         * @param promotionalCodeDTO DTO object containing promotional code data.
         * @return The created promotional code.
         */
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public PromotionalCode createPromotionalCode(@RequestBody @Valid PromotionalCodeDTO promotionalCodeDTO) {
            return promotionalCodeService.createPromotionalCode(promotionalCodeDTO);
        }

        /**
         * Exception handler for validation errors in the request body.
         *
         * @param ex Exception containing the validation errors.
         * @return A list of error messages.
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
            List<String> errors = new ArrayList<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String errorMessage = error.getDefaultMessage();
                errors.add(errorMessage);
            });
            return errors;
        }

    }
