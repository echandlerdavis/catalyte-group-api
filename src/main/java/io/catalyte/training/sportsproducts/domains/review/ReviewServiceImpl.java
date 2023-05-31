package io.catalyte.training.sportsproducts.domains.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.constants.StringConstants;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductService;
import io.catalyte.training.sportsproducts.domains.purchase.LineItem;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.domains.purchase.PurchaseService;
import io.catalyte.training.sportsproducts.domains.user.UserService;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * This class provides the implementation for the ReviewService interface.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

  ReviewRepository reviewRepository;

  ProductService productService;

  PurchaseService purchaseService;
  UserService userService;

  @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService,
      PurchaseService purchaseService, UserService userService) {
    this.reviewRepository = reviewRepository;
    this.productService = productService;
    this.purchaseService = purchaseService;
    this.userService = userService;
  }

  /**
   * Retrieves all reviews from the database attributed to a specific product id.
   *
   * @param productId - the id of the product the review belongs to
   * @return - a list of reviews belonging to a product with the given product id.
   */
  public List<Review> getAllReviewsByProductId(Long productId) {
    try {
      return reviewRepository.findByProductId(productId);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }
  /**
   * Get all reivews that are active for the given prodcut
   *
   * @param productId Long
   * @return List of reviews
   */
  @Override
  public List<Review> getAllActiveReviewsByProductId(Long productId) {
    try {
      return reviewRepository.findAllActiveReviewsByProductId(productId);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Handles a soft delete of a review
   *
   * @param reviewId        Long id of review to be deleted
   * @param requestingEmail Email of user who is requesting delete
   * @return Boolean whether or not action was performed
   */
  @Override
  public HttpStatus deactivateReview(Long reviewId, String requestingEmail) {
    //get review
    Optional<Review> schrodingersReview = reviewRepository.findById(reviewId);
    //if no review, return false
    if (!schrodingersReview.isPresent()) {
      return HttpStatus.NOT_FOUND;
    }
    Review review = schrodingersReview.get();
    //if admin or if user who created review, allow for deactivation
    if (userService.isAdmin(requestingEmail)
        || (review.getUserEmail() != null
        && review.getUserEmail().equals(requestingEmail))) {
      review.setActive(false);
      try {
        reviewRepository.save(review);
      } catch (DataAccessException dae) {
        logger.error(dae.getMessage());
        throw new ServerError(dae.getMessage());
      }
      return HttpStatus.NO_CONTENT;
    }
    //if user is not admin and did not create review, do nothing and return false
    return HttpStatus.FORBIDDEN;
  }

  public Review postReview(Long productId, ReviewDTO reviewDTO) {
    List<String> reviewErrors = getReviewErrors(reviewDTO, productId);

    if (!reviewErrors.isEmpty()) {
      throw new BadRequest(String.join("\n", reviewErrors));
    }

    Review review = new Review();
    review.setTitle(reviewDTO.getTitle());
    review.setReview(reviewDTO.getReview());
    review.setRating(reviewDTO.getRating());
    review.setProduct(productService.getProductById(productId));
    review.setCreatedAt(reviewDTO.getCreatedAt());
    review.setEditedAt(reviewDTO.getEditedAt());
    review.setUserName(reviewDTO.getUserName());
    review.setUserEmail(reviewDTO.getUserEmail());
    review.setUserName(reviewDTO.getUserName());
    review.setUserEmail(reviewDTO.getUserEmail());
    review.setActive(true);

    try {
      return reviewRepository.save(review);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new BadRequest(e.getMessage());
    }
  }

  public List<String> getReviewErrors(ReviewDTO reviewDTO, Long productId) {
    List<String> errors = new ArrayList<>();

    List<String> nullFields = getEmptyOrNullFields(reviewDTO).get("nullFields");
    List<String> emptyFields = getEmptyOrNullFields(reviewDTO).get("emptyFields");

    if (!nullFields.isEmpty()) {
      errors.add(StringConstants.REVIEW_FIELDS_NULL(nullFields));
    }

    if (!emptyFields.isEmpty()) {
      errors.add(StringConstants.REVIEW_FIELDS_EMPTY(emptyFields));
    }

    if(!inputsAreValid(reviewDTO)){
      errors.add(StringConstants.REVIEW_INPUTS_INVALID);
    }


    if (!ratingIsValid(reviewDTO)) {
      errors.add(StringConstants.REVIEW_RATING_INVALID);
    }

    if (!userHasPurchasedProduct(reviewDTO, productId)) {
      errors.add(StringConstants.REVIEW_USER_PURCHASE_INVALID);
    }

    if (!userHasNotLeftReview(reviewDTO, productId)) {
      errors.add(StringConstants.REVIEW_USER_HAS_ALREADY_REVIEWED);
    }

    return errors;
  }

  public HashMap<String, List<String>> getEmptyOrNullFields(ReviewDTO reviewDTO) {
    HashMap<String, List<String>> results = new HashMap<>();
    List<Field> reviewFields = Arrays.asList(ReviewDTO.class.getDeclaredFields());
    List<String> reviewFieldNames = new ArrayList<>();
    List<String> emptyFields = new ArrayList<>();
    List<String> nullFields = new ArrayList<>();

    reviewFields.forEach(field -> {
      String name = field.getName();
      if (name != "product" && name != "isActive") {
        reviewFieldNames.add(name);
      }
    });

    ObjectMapper mapper = new ObjectMapper();
    Map reviewMap = mapper.convertValue(reviewDTO, HashMap.class);
    reviewFieldNames.forEach((fieldName) -> {
      if (reviewMap.get(fieldName) == null) {
        nullFields.add(fieldName);
      } else if (reviewMap.get(fieldName).toString().trim() == "") {
        emptyFields.add(fieldName);
      }
    });

    results.put("nullFields", nullFields);
    results.put("emptyFields", emptyFields);
    return results;
  }

  public Boolean ratingIsValid(ReviewDTO reviewDTO) {
    Double rating = reviewDTO.getRating();
    if (rating != null) {
      if (rating >= 0.5 && rating <= 5) {
        String[] ratingString = String.valueOf(rating).split("\\.");
        Boolean ratingInCorrectIncrement =
            ratingString[1].equals("0") || ratingString[1].equals("5");
        return ratingInCorrectIncrement;
      }
    }
    return false;
  }

  public Boolean inputsAreValid(ReviewDTO reviewDTO) {
    ObjectMapper mapper = new ObjectMapper();
    Map reviewMap = mapper.convertValue(reviewDTO, HashMap.class);
    if (reviewMap.get("title") == null || reviewMap.get("title").toString().trim() == "") {
      if (reviewMap.get("review") == null || reviewMap.get("review").toString().trim() == "") {
        return false;
      }
    }
    return true;
  };

  public Boolean userHasPurchasedProduct(ReviewDTO reviewDTO, Long productId) {
    Product product = productService.getProductById(productId);
    String userEmail = reviewDTO.getUserEmail();
    List<Purchase> userPurchases = purchaseService.findByBillingAddressEmail(userEmail);

    if (!userPurchases.isEmpty()) {
      for (Purchase purchase : userPurchases) {
        Set<LineItem> productsInPurchase = purchase.getProducts();
        for (LineItem productInPurchase : productsInPurchase) {
          if (productInPurchase.getProduct() == product) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public Boolean userHasNotLeftReview(ReviewDTO reviewDTO, Long productId){
    List<Review> reviewList = reviewRepository.findByUserEmail(reviewDTO.getUserEmail());
    if(reviewList.isEmpty()){
      return true;
    }
    for(Review review : reviewList){
      if(review.getProduct().getId() == productId){
          return false;
        }
      }
    return true;

  }
}






