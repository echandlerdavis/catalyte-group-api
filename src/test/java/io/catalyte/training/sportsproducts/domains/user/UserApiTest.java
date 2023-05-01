package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Paths.USERS_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;
  private MockMvc mockMvc;
  private User testUser;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
  private Long id;
  private Date lastActive;

  private ObjectMapper mapper;

  @Before
  public void setUp(){
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    //initialize test variables
    firstName = "Bob";
    lastName = "Belcher";
    email = "Bob@BobsBurgers.com";
    role = "BurgerMeister";
    id = 1l;
    lastActive = new Date();
    //set test user
    testUser = new User();
    setUserData(testUser, id, firstName, lastName, email, role, lastActive);
    //save test user to database
    // and reset testUser and id in case there is existing data
    testUser = userService.createUser(testUser);
    id = testUser.getId();
    //set object mapper
    mapper = new ObjectMapper();

  }

  @After
  public void setTearDown() {
    //remove testUser from database
    userRepository.delete(testUser);
  }
  public void setUserData(User emptyUser, Long id, String firstName, String lastName, String email, String role, Date lastActive){
    emptyUser.setId(id);
    emptyUser.setFirstName(firstName);
    emptyUser.setLastName(lastName);
    emptyUser.setEmail(email);
    emptyUser.setRole(role);
    emptyUser.setLastActive(lastActive);
  }

  @Test
  @WithMockUser(username="")
  public void updateLastActiveTest() throws Exception {
    final String testPath = USERS_PATH + "/" + testUser.getId().toString() + "/updateLastActive";
    mockMvc.perform(put(testPath))
          .andExpect(status().isOk());
  }

}
