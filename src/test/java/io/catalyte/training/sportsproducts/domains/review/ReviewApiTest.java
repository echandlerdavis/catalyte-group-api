package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static io.catalyte.training.sportsproducts.constants.Roles.ADMIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.data.PurchaseFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.purchase.BillingAddress;
import io.catalyte.training.sportsproducts.domains.purchase.CreditCard;
import io.catalyte.training.sportsproducts.domains.purchase.DeliveryAddress;
import io.catalyte.training.sportsproducts.domains.purchase.LineItem;
import io.catalyte.training.sportsproducts.domains.purchase.LineItemRepository;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.domains.purchase.PurchaseRepository;
import io.catalyte.training.sportsproducts.domains.purchase.PurchaseService;
import io.catalyte.training.sportsproducts.domains.user.User;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
  @Autowired
  private PurchaseRepository purchaseRepository;
  @Autowired
  private LineItemRepository lineItemRepository;

  @Autowired
  PurchaseService purchaseService;

  private ProductFactory productFactory = new ProductFactory();
  private PurchaseFactory purchaseFactory = new PurchaseFactory();
  private Product testProduct = productFactory.createRandomProduct();
  private Purchase testPurchase;
  private LineItem testLineItem;
  private DeliveryAddress testDelivery;
  private BillingAddress testBilling;
  private CreditCard testCreditCard;
  private Review testReview1 = productFactory.createRandomReview(testProduct, 1, null);
  private Review testReview2 = productFactory.createRandomReview(testProduct, 2, null);
  private ReviewDTO writeReview;
  private String testEmail;
  private User admin;
  private User user;
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
    setWriteReview();
    setTestPurchase();
  }

  @After
  public void tearDown() {
    deleteAdmin();
  }

  public void createAdmin() {
    //check to see if admin is already in database
    User checkAdmin = userRepository.findByEmail(adminEmail);
    if (checkAdmin == null) {
      //if no admin, create admin
      admin = new User();
      admin.setRole(ADMIN);
      admin.setEmail(adminEmail);
      User savedAdmin = userRepository.save(admin);
      admin.setId(savedAdmin.getId());
    }
  }

  public void setWriteReview() {
    User checkUser = userRepository.findByEmail(testEmail);
    if (checkUser == null) {
      user = new User();
      user.setEmail(testEmail);
      user.setFirstName("Devin");
      user.setLastName("Duval");
      User savedUser = userRepository.save(user);
      user.setId(savedUser.getId());
    } else {
      user = checkUser;
    }
    ;
    writeReview = new ReviewDTO(
        "Title",
        4.5,
        "An excellent product!",
        "10/12/2023",
        "10/12/2023",
        user.getFirstName(),
        user.getEmail(),
        testProduct
    );
  }

  public void deleteAdmin() {
    Optional<User> optionalAdmin = userRepository.findById(admin.getId());
    //if admin, remove from database
    if (optionalAdmin.isPresent()) {
      userRepository.delete(admin);
    }
  }

  private void setTestReviews() {
    Product savedProduct = productRepository.save(testProduct);
    testProduct.setReviews(Arrays.asList(testReview1, testReview2));
    testProduct.setActive(true);
    testProduct.setId(savedProduct.getId());
    //set testReview 1
    Review savedReview = reviewRepository.save(testReview1);
    testReview1.setId(savedReview.getId());
    //set testReview 2
    Review savedReview2 = reviewRepository.save(testReview2);
    testReview2.setId(savedReview2.getId());
  }

  private void setTestPurchase() {
    testPurchase = new Purchase();

    testDelivery = new DeliveryAddress(
        "Test",
        "User",
        "Street 1",
        null,
        "City",
        "New York",
        12345);
    testBilling = new BillingAddress(
        "Street 1",
        null,
        "City",
        "New York",
        12345,
        testEmail,
        "1234567899");
    testCreditCard = new CreditCard(
        "1234567812345678",
        "123",
        "03/25",
        "Test User"
    );

    Set<LineItem> lineItems = new HashSet<>();
    testLineItem = new LineItem();

    testLineItem.setProduct(testProduct);
    testLineItem.setQuantity(1);
    lineItems.add(testLineItem);

    testPurchase.setProducts(lineItems);
    testPurchase.setDeliveryAddress(testDelivery);
    testPurchase.setShippingCharge(10.00);
    testPurchase.setBillingAddress(testBilling);
    testPurchase.setCreditCard(testCreditCard);
    testPurchase.setPromoCode(null);
    Purchase savedPurchase = purchaseRepository.save(testPurchase);

    testLineItem.setPurchase(savedPurchase);

    testPurchase.setId(savedPurchase.getId());
    lineItemRepository.saveAll(savedPurchase.getProducts());
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
            delete(String.format("/reviews/%d/email/%s", testReview2.getId(), testEmail)))
        .andReturn();
    Review updatedReview = reviewRepository.findById(testReview2.getId()).get();

    assertFalse(updatedReview.getActive());
  }

  @Test
  public void deleteReviewDeletesReviewIfUserIsAdmin() throws Exception {
    //setup test
    testReview2.setUserEmail(testEmail);
    reviewRepository.save(testReview2);
    //run test
    mockMvc.perform(
            delete(String.format("/reviews/%d/email/%s", testReview2.getId(), admin.getEmail())))
        .andReturn();
    Review updatedReview = reviewRepository.findById(testReview2.getId()).get();

    assertFalse(updatedReview.getActive());
  }

  @Test
  public void deleteReviewReturns204WhenReviewIsDeleted() throws Exception {
    //setup test
    testReview2.setUserEmail(testEmail);
    reviewRepository.save(testReview2);
    //run test
    mockMvc.perform(
            delete(String.format("/reviews/%d/email/%s", testReview2.getId(), admin.getEmail())))
        .andExpect(status().is(204));
  }

  @Test
  public void deleteReviewReturns403WhenUserDidNotCreateReviewOrIsNotAdmin() throws Exception {
    //setup test
    testReview2.setUserEmail("randomEmail@email.com");
    reviewRepository.save(testReview2);
    //run test
    mockMvc.perform(
            delete(String.format("/reviews/%d/email/%s", testReview2.getId(), testEmail)))
        .andExpect(status().isForbidden());
  }

  @Test
  public void deactivateReviewReturns404WhenReviewDoesNotExist() throws Exception {
    //setup test
    long reviewsCount = reviewRepository.count();
    long nonExistentId = reviewsCount + 100;
    //run test
    mockMvc.perform(
            delete(String.format("/reviews/%d/email/%s", nonExistentId, admin.getEmail())))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deactivateReviewReturns403WhenReviewDoesNotHaveEmail() throws Exception {
    //setup test
    testReview2.setUserEmail(null);
    reviewRepository.save(testReview2);
    //run test
    mockMvc.perform(
            delete(String.format("/reviews/%d/email/%s", testReview2.getId(), testEmail)))
        .andExpect(status().isForbidden());
  }

  @Test
  public void saveReviewReturns201WithReviewObject() throws Exception {
    testReview1.setUserEmail("test1@test.com");
    testReview2.setUserEmail("test2@test.com");
    ObjectMapper mapper = new ObjectMapper();
    mockMvc.perform(
            post(String.format("/products/%d/reviews", testProduct.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(writeReview)))
        .andExpect(status().isCreated());
  }
}
