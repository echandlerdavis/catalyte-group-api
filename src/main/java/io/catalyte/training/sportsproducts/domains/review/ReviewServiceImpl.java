package io.catalyte.training.sportsproducts.domains.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.constants.StringConstants;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.product.ProductService;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * This class provides the implementation for the ReviewService interface.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

  ReviewRepository reviewRepository;

  ProductService productService;

  @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService) {
    this.reviewRepository = reviewRepository;
    this.productService = productService;
  }

  /**
   * Retrieves all reviews from the database attributed to a specific product id.
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

  public Review postReview(Long productId, ReviewDTO reviewDTO){
    List<String> reviewErrors = getReviewErrors(reviewDTO);

    if(!reviewErrors.isEmpty()){
      throw new BadRequest(String.join("\n", reviewErrors));
    }

    Review review = new Review();
    review.setTitle(reviewDTO.getTitle());
    review.setReview(reviewDTO.getReview());
    review.setRating(reviewDTO.getRating());
    review.setProduct(productService.getProductById(productId));
    review.setCreatedAt(reviewDTO.getCreatedAt());
    review.setUserName(reviewDTO.getUserName());
    review.setUserEmail(reviewDTO.getUserEmail());

    try{
      return reviewRepository.save(review);
    } catch (DataAccessException e){
      logger.error(e.getMessage());
      throw new BadRequest(e.getMessage());
    }
  }

  public List<String> getReviewErrors(ReviewDTO reviewDTO){
    List<String> errors = new ArrayList<>();

    List<String> nullFields = getEmptyOrNullFields(reviewDTO).get("nullFields");
    List<String> emptyFields = getEmptyOrNullFields(reviewDTO).get("emptyFields");

    if(!nullFields.isEmpty()){
      errors.add(StringConstants.REVIEW_FIELDS_NULL(nullFields));
    }

    if(!emptyFields.isEmpty()){
      errors.add(StringConstants.REVIEW_FIELDS_EMPTY(emptyFields));
    }

    return errors;
  }

  public HashMap<String, List<String>> getEmptyOrNullFields(ReviewDTO reviewDTO){
    HashMap<String, List<String>> results = new HashMap<>();
    List<Field> reviewFields = Arrays.asList(ReviewDTO.class.getDeclaredFields());
    List<String> reviewFieldNames = new ArrayList<>();
    List<String> emptyFields = new ArrayList<>();
    List<String> nullFields = new ArrayList<>();

    reviewFields.forEach(field -> {
      String name = field.getName();
      if(name != "product") {
        reviewFieldNames.add(name);
      }
    });

    ObjectMapper mapper = new ObjectMapper();
    Map reviewMap = mapper.convertValue(reviewDTO, HashMap.class);
    reviewFieldNames.forEach((fieldName) -> {
      if(reviewMap.get(fieldName) == null){
        nullFields.add(fieldName);
      }else if(reviewMap.get(fieldName).toString().trim() == ""){
        emptyFields.add(fieldName);
      }
    });

    results.put("nullFields", nullFields);
    results.put("emptyFields", emptyFields);
    return results;
  }
}



