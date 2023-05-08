package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;

public interface ReviewService {
  List<Review> getAllReviewsByProductId(Long productId);
}
