package io.catalyte.training.sportsproducts.domains.purchase;

import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PurchaseApiUnitTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  private Purchase testPurchase;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    testPurchase = new Purchase();
    BillingAddress addr = new BillingAddress();
    addr.setEmail("test@email.com");
    testPurchase.setBillingAddress(addr);
  }

  /**
   * Test that post request to /purchases returns a 201 status.
   * @throws Exception
   */
  @Test
  public void postPurchasesReturns201() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mockMvc.perform(
        post(PURCHASES_PATH)
            .contentType("application/json")
            .content(mapper.writeValueAsString(testPurchase)))
        .andExpect(status().isCreated());
  }

}
