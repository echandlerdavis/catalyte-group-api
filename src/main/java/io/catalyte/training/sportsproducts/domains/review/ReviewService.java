package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;

/**
 * This interface provides an abstraction layer for the Reviews Service
 */
public interface ReviewService {

  List<Review> getAllReviewsByProductId(Long productId);

  Review postReview(Long productId, ReviewDTO reviewDTO);

  List<Review> getAllActiveReviewsByProductId(Long productId);


}
