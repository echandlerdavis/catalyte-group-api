package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductService;
import io.catalyte.training.sportsproducts.domains.user.UserService;
import io.catalyte.training.sportsproducts.domains.user.UserServiceImpl;
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

  private final ReviewRepository reviewRepository;
  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService,
      UserService userService){
    this.reviewRepository = reviewRepository;
    this.productService = productService;
    this.userService = userService;
  }

  public List<Review> getAllReviewsByProductId(Long productId){
    try{
      return reviewRepository.findByProductId(productId);
    } catch (DataAccessException e){
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

//  public Review postReview(Long productId, ReviewDTO reviewDTO){
////    TODO: add validation to this.
////    TODO: figure out if you need to set the id, user and createdAt or if that's automatic.
//    Review review = new Review();
//    review.setTitle(reviewDTO.getTitle());
//    review.setReview(reviewDTO.getReview());
//    review.setRating(reviewDTO.getRating());
//    review.setProduct(productService.getProductById(productId));
//
//    try{
//      return reviewRepository.save(review);
//    } catch (DataAccessException e){
//      logger.error(e.getMessage());
//      throw new BadRequest(e.getMessage());
//    }
//  }
}
