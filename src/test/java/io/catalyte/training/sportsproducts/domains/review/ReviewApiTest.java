package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.data.PurchaseFactory;
import io.catalyte.training.sportsproducts.data.UserFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.purchase.LineItem;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.domains.purchase.PurchaseRepository;
import io.catalyte.training.sportsproducts.domains.user.User;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewApiTest {

  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  ProductRepository productRepository;

  @Autowired
  PurchaseRepository purchaseRepository;

  ProductFactory productFactory = new ProductFactory();
  Product testProduct = productFactory.createRandomProduct();

  UserFactory userFactory = new UserFactory();
  User testUser = new User("testemail@email.com", "TestFirstName", "TestLastName", userFactory.generateRandomUserBillingAddress());
  PurchaseFactory purchaseFactory = new PurchaseFactory();
  Purchase testPurchase = purchaseFactory.generateRandomPurchase(testUser);
  Review testReview1 = productFactory.createRandomReview(testProduct, 1);
  Review testReview2 = productFactory.createRandomReview(testProduct, 2);
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    setTestReviews();
    setTestPurchase();
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  private void setTestReviews() {
    productRepository.save(testProduct);
    testProduct.setReviews(Arrays.asList(testReview1, testReview2));
    testProduct.setActive(true);
    reviewRepository.saveAll(Arrays.asList(testReview1, testReview2));
  }

  private void setTestPurchase(){
    Set<LineItem> products = new HashSet<>();
    purchaseRepository.save(testPurchase);
    LineItem lineItem = purchaseFactory.generateLineItem(testProduct);
    products.add(lineItem);
    testPurchase.setProducts(products);
  }
  @Test
  public void getReviewsByProductIdReturns200() throws Exception {
    mockMvc.perform(get(PRODUCTS_PATH + "/1/reviews"))
        .andExpect(status().isOk());
  }
}
