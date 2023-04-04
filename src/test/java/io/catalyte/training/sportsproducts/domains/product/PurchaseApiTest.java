package io.catalyte.training.sportsproducts.domains.product;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import java.util.List;
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
public class PurchaseApiTest {
  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  /**
   * Create a mockMvc to run tests with
   */
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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


}
