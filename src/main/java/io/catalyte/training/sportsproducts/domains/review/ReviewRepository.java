package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByProductId(Long productId);

  List<Review> findByUserEmail(String userEmail);

  @Query("SELECT r FROM Review r WHERE r.product.id = ?1 AND r.isActive = 1")
  List<Review> findAllActiveReviewsByProductId(Long productId);

}
