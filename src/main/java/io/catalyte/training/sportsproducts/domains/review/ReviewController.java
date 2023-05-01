package io.catalyte.training.sportsproducts.domains.review;
import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;

import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//I don't think Request Mapping is Correct
@RestController
@RequestMapping(value = PRODUCTS_PATH)
public class ReviewController {
  Logger logger = LogManager.getLogger(ReviewController.class);
  @Autowired
  private ReviewService reviewService;

  @GetMapping(value = "/reviews")
  public ResponseEntity<List<Review>> getAllReviews(){
    logger.info("Request received for getAllReviews");
    return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<Review> postReview(@Valid @RequestBody ReviewDTO reviewDTO){
    logger.info("Request received for postReview");
    return new ResponseEntity<>(reviewService.postReview(reviewDTO), HttpStatus.OK);
  }

}
