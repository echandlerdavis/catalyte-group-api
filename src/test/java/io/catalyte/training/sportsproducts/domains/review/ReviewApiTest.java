package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static io.catalyte.training.sportsproducts.constants.Roles.ADMIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.user.User;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
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
  private ReviewRepository reviewRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;
  private ProductFactory productFactory = new ProductFactory();
  private Product testProduct = productFactory.createRandomProduct();
  private Review testReview1 = productFactory.createRandomReview(testProduct, 1);
  private Review testReview2 = productFactory.createRandomReview(testProduct, 2);
  private String testEmail;
  private User admin;
  private String adminEmail;
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    testEmail = "dduval@catalyte.io";
    adminEmail = "admin@admin.com";
    setTestReviews();
    createAdmin();
  }

  public void createAdmin() {
    admin = new User();
    admin.setRole(ADMIN);
    admin.setEmail(adminEmail);
    userRepository.save(admin);
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

  @Test
  public void deleteReviewDeletesReviewIfUserCreatedReview() throws Exception {
    //setup test
    testReview2.setUserEmail(testEmail);
    reviewRepository.save(testReview2);
    //run test
    mockMvc.perform(
            delete(String.format("/reviews/%d", testProduct.getId()))
                .contentType("application/json")
                .content("{\"requestingEmail\": \"" + testEmail + "\"}"))
        .andReturn();
    Review updatedReview = reviewRepository.findById(testReview2.getId()).get();

    assertFalse(updatedReview.getActive());
  }

  @Test
  public void deleteReviewDeletesReviewIfUserIsAdmin() {
    fail();
  }

  @Test
  public void deleteReviewReturns204WhenReviewIsDeleted() {
    fail();
  }

  @Test
  public void deleteReviewReturns403WhenUserDidNotCreateReviewOrIsNotAdmin() {
    fail();
  }

  @Test
  public void deactivateReviewReturns403WhenReviewDoesNotExist() {
    fail();
  }
}
