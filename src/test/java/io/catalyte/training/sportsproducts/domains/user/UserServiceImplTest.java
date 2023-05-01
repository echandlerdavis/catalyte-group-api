package io.catalyte.training.sportsproducts.domains.user;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.auth.GoogleAuthService;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserServiceImpl.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private GoogleAuthService googleAuthService;

  User testUser;
  String firstName;
  String lastName;
  String email;
  String role;
  Long id;
  Date lastActive;

  @Before
  public void setUp() {
    //set all the inner variables
    firstName = "Bob";
    lastName = "Belcher";
    email = "Bob@BobsBurgers.com";
    role = "BurgerMeister";
    id = 1l;
    lastActive = new Date();
    //set testUser
    testUser = new User();
    setUserData(testUser, id, firstName, lastName, email, role, lastActive);
    //set Mocks
    //google mock
    when(googleAuthService.authenticateUser(anyString(), any(User.class))).thenReturn(true);
    //userRepo.save mock
    when(userRepository.save(any(User.class))).thenAnswer((u) ->{
      User copyUser = new User();
      User passedUser = u.getArgument(0);
      setUserData(copyUser,
                  passedUser.getId(),
                  passedUser.getFirstName(),
                  passedUser.getLastName(),
                  passedUser.getRole(),
                  passedUser.getEmail(),
                  passedUser.getLastActive());

      return copyUser;
    });
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
  public void updateLastActiveSetsNewerTimeTest() {
    Date updatedTime = userService.updateLastActive(testUser.getEmail(), testUser);
    assertTrue(updatedTime.after(lastActive));
  }

}
