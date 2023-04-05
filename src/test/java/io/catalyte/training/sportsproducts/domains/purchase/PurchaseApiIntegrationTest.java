package io.catalyte.training.sportsproducts.domains.purchase;

import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.OneToMany;
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
public class PurchaseApiIntegrationTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  private Purchase testPurchase;
  private String testEmail = "test@email.com";

  @Resource
  private PurchaseRepository purchaseRepository;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    testPurchase = new Purchase();
    BillingAddress addr = new BillingAddress();
    addr.setEmail(testEmail);
    testPurchase.setBillingAddress(addr);
  }

  /**
   * Test that post request to /purchases returns the posted purchase object and that the object
   * was saved int he database.
   *
   * @throws Exception
   */
  @Test
  public void postPurchasesReturnsPurchaseObject() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    MockHttpServletResponse response =
        mockMvc.perform(
            post(PURCHASES_PATH)
                .contentType("application/json")
                .content(mapper.writeValueAsString(testPurchase)))
        .andReturn().getResponse();
    Purchase returnedPurchase = mapper.convertValue(response.getContentAsString(), Purchase.class);
    List<Purchase> dbPurchases = purchaseRepository.findByBillingAddressEmail(testEmail);

    assertTrue(twoPurchasesEqual(testPurchase, returnedPurchase));
    assertTrue(dbPurchases.size() > 1);
    assertTrue(twoPurchasesEqual(testPurchase, dbPurchases.get(0)));
  }

  public boolean twoPurchasesEqual(Purchase p1, Purchase p2){
    if(p1 == null && p2 != null) {return false;}
    if(p1 != null && p2 == null) {return false;}
    if(p1.getId() != p2.getId()) {return false;}
    if(p1.getDeliveryAddress() != p2.getDeliveryAddress()) {return false;}
    if(p1.getBillingAddress() != p2.getBillingAddress()) {return false;}
    return p1.getCreditCard() != p2.getCreditCard();
  }

}
