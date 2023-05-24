package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByProductId(Long productId);

  List<Review> findByUserEmail(String userEmail);

  @Query("SELECT r FROM reviews WHERE r.product_id = 1 AND r.is_active = 1")
  List<Review> findAllActiveReviewsByProductId(Long productId);

}
