package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;

public interface ReviewService {
  List<Review> getAllReviewsByProductId(Long productId);
  Review postReview(Long productId, ReviewDTO reviewDTO);
}
