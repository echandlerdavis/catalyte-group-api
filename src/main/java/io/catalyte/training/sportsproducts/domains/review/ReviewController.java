package io.catalyte.training.sportsproducts.domains.review;
import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;

import io.catalyte.training.sportsproducts.domains.product.ProductService;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ReviewController {
  Logger logger = LogManager.getLogger(ReviewController.class);
  @Autowired
  private ReviewService reviewService;

  @GetMapping(value = "products/{productId}/reviews")
  public ResponseEntity<List<Review>> getAllReviewsByProductId(@PathVariable(value = "productId") Long productId){
    logger.info("Request received for getAllReviews");
    return new ResponseEntity<>(reviewService.getAllReviewsByProductId(productId), HttpStatus.OK);
  }

//  @PostMapping(value = "products/{productId}/reviews")
//  @ResponseStatus(value = HttpStatus.OK)
//  public ResponseEntity<Review> postReview(@PathVariable Long productId, @Valid @RequestBody ReviewDTO reviewDTO){
//    logger.info("Request received for postReview");
//    return new ResponseEntity<>(reviewService.postReview(productId, reviewDTO), HttpStatus.OK);
//  }

}
