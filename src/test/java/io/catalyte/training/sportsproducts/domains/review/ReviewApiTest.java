package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewApiTest {

  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  ProductRepository productRepository;
  ProductFactory productFactory = new ProductFactory();
  Product testProduct = productFactory.createRandomProduct();
  Review testReview1 = productFactory.createRandomReview(testProduct, 1);
  Review testReview2 = productFactory.createRandomReview(testProduct, 2);
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    setTestReviews();
  }

  private void setTestReviews() {
    productRepository.save(testProduct);
    testProduct.setReviews(Arrays.asList(testReview1, testReview2));
    reviewRepository.saveAll(Arrays.asList(testReview1, testReview2));
  }

  @Test
  public void getReviewsByProductIdReturns200() throws Exception {
    mockMvc.perform(get(PRODUCTS_PATH + "/" + Long.toString(testProduct.getId()) + "/reviews"))
        .andExpect(status().isOk());
  }

  @Test
  public void getActiveReviewsByProductIdReturnsActiveReviews() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    //change a review to inactive
    long startingActiveReviews = reviewRepository.findAllActiveReviewsByProductId(
        testProduct.getId()).size();
    testReview2.setActive(false);
    reviewRepository.save(testReview2);
    //set the number of expected reviews
    long expectedReviewCount = startingActiveReviews - 1;
    //set actuals
    long actualReviewCount = reviewRepository.findAllActiveReviewsByProductId(testProduct.getId())
        .size();
    MvcResult result = mockMvc.perform(
            get(PRODUCTS_PATH + String.format("/%d/reviews", testProduct.getId())))
        .andReturn();
    List<Review> reviews = mapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Review>>() {
        });
    assertEquals(expectedReviewCount,
        reviewRepository.findAllActiveReviewsByProductId(testProduct.getId()).size());
    assertTrue(!reviews.contains(testReview2));
  }
}
