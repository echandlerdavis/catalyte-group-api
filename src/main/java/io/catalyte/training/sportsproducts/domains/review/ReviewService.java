package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;

public interface ReviewService {
  List<Review> getAllReviews();
  Review postReview(ReviewDTO reviewDTO);
}
