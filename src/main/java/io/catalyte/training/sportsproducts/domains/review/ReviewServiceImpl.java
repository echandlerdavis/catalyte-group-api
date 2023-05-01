package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService{

  private final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

  ReviewRepository reviewRepository;

  @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository){
    this.reviewRepository = reviewRepository;
  }

  public List<Review> getAllReviews(){
    try{
      return reviewRepository.findAll();
    } catch (DataAccessException e){
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  public Review postReview(ReviewDTO reviewDTO){
//    TODO: add validation to this.
//    TODO: figure out if you need to set the id, user and createdAt or if that's automatic.
    Review review = new Review();
    review.setTitle(reviewDTO.getTitle());
    review.setReview(reviewDTO.getReview());
    review.setRating(reviewDTO.getRating());

    try{
      return reviewRepository.save(review);
    } catch (DataAccessException e){
      logger.error(e.getMessage());
      throw new BadRequest(e.getMessage());
    }
  }
}
