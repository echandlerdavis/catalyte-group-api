package io.catalyte.training.sportsproducts.domain.purchases;

import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.domains.purchase.BillingAddress;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.domains.purchase.PurchaseRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseApiTest {
  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;
  private List<BillingAddress> testAddresses;
  private String[] emails = {"email@address.com", "email@address.net", "email@address.edu"};
  private Map<String, Integer> purchaseCounts;
  @Resource
  public PurchaseRepository purchaseRepository;

  /**
   * Create a mockMvc to run tests with and load some new purchases into the test
   * database to use in testing.
   */
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    testAddresses = new ArrayList<>();
    purchaseCounts = new HashMap<>();
    for(String email: emails){
      BillingAddress temp = new BillingAddress();
      temp.setEmail(email);
      testAddresses.add(temp);
    }
    for(String email: emails){
      BillingAddress tempAddress = new BillingAddress();
      tempAddress.setEmail(email);
      Purchase tempPurchase = new Purchase();
      tempPurchase.setBillingAddress(tempAddress);
      purchaseRepository.save(tempPurchase);
      purchaseCounts.put(email, 1);
    }

    Purchase secondPurchase = new Purchase();
    BillingAddress secondEmail = new BillingAddress();
    secondEmail.setEmail(emails[0]);
    secondPurchase.setBillingAddress(secondEmail);
    purchaseRepository.save(secondPurchase);
    purchaseCounts.replace(emails[0], purchaseCounts.get(emails[0]) + 1);

  }

  /**
   * Ensure that data is loaded into the test database.
   * @throws Exception
   */
  @Test
  public void dataBaseFixtureTest() throws Exception {
    List<Purchase> testPurchases = purchaseRepository.findAll();
    assertTrue(testPurchases.size() > 0);
  }

  /**
   * Tests that the API returns an empty list when a non-existent email is send to /purchases/{email}
    * @throws Exception
   */
  @Test
  public void findPurchasesByEmailEmailNotFoundReturnsEmptyList() throws Exception {
    String purchases = mockMvc.perform(get(PURCHASES_PATH + "/not@anEmail.com"))
        .andReturn().getResponse().getContentAsString();
    assertEquals("[]", purchases);
  }

  /**
   * Tests that the API returns a response with status 200 when a non-existent email is searched for
   * @throws Exception
   */
  @Test
  public void findPurchasesByEmailEmailNotFoundReturnsOk() throws Exception {
    mockMvc.perform(get(PURCHASES_PATH + "/not@anEmail.com"))
        .andExpect(status().isOk());
  }

  /**
   * Tests that a GET request send to /purchases returns a 404 error.
   * @throws Exception
   */
  @Test
  public void getAllPurchasesReturns404() throws Exception {
    mockMvc.perform(get(PURCHASES_PATH))
        .andExpect(status().is(404));
  }

  /**
   * Tests that findPurchasesByEmail returns the expected number of purchases.
   * @throws Exception
   */
  @Test
  public void findPurchasesByEmailReturnsEmailList() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    for (String email: emails) {
      MockHttpServletResponse response = mockMvc.perform(get(PURCHASES_PATH + "/" + email))
          .andReturn().getResponse();
      List<Purchase> purchases = mapper.readValue(response.getContentAsString(),
          new TypeReference<List<Purchase>>() {
          });
      assertEquals(Integer.valueOf(purchaseCounts.get(email)), Integer.valueOf(purchases.size()));
    }
  }


}
