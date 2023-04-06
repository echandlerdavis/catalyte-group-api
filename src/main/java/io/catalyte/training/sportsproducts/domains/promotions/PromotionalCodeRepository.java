package io.catalyte.training.sportsproducts.domains.promotions;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 *  Repository interface for performing CRUD operations on PromotionalCode entity.
 */

public interface PromotionalCodeRepository extends JpaRepository<PromotionalCode, Long> {

    /**
     *  Saves the promotional code in the database
     *
     * @param promotionalCode the promotional code to be saved
     * @return the saved promotional code
     */
    PromotionalCode save(PromotionalCode promotionalCode);

    /**
     * Finds a promotional code by its id
     *
     * @param id the id of the promotional code to find
     * @return the promotional code with the given id, or null if not found
     */
    Optional<PromotionalCode> findById(Long id);
}
