package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Roles.ADMIN;
import static io.catalyte.training.sportsproducts.constants.Roles.CUSTOMER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.auth.GoogleAuthService;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.Date;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.web.server.ResponseStatusException;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserServiceImpl.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private GoogleAuthService googleAuthService;

  private User testUser;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
  private Long id;
  private Date lastActive;

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
    when(userRepository.save(any(User.class))).thenAnswer((u) -> {
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

    //userRepo.getById mock
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
    //userRepo.findByEmail
    when(userRepository.findByEmail(anyString())).thenReturn(testUser);
  }

  public void setUserData(User emptyUser, Long id, String firstName, String lastName, String email,
      String role, Date lastActive) {
    emptyUser.setId(id);
    emptyUser.setFirstName(firstName);
    emptyUser.setLastName(lastName);
    emptyUser.setEmail(email);
    emptyUser.setRole(role);
    emptyUser.setLastActive(lastActive);
  }

  @Test
  public void updateLastActiveSetsNewerTimeTest() throws InterruptedException {
    //make test wait after setup to make sure last active is before the updated value
    Thread.sleep(500);
    User updated = userService.updateLastActive(testUser.getEmail(), id, testUser);
    assertTrue(updated.getLastActive().after(lastActive));
  }

  @Test(expected = ResponseStatusException.class)
  public void updateLastActiveAuthenticationFailureThrowsResponseStatusExceptionTest() {
    when(googleAuthService.authenticateUser(anyString(), any(User.class))).thenReturn(false);
    User updated = userService.updateLastActive(testUser.getEmail(), id, testUser);
    fail(); //this shouldn't run
  }

  @Test(expected = ServerError.class)
  public void updateLastActiveAuthenticationFailureThrowsServerErrorTest() {
    when(userRepository.save(any(User.class))).thenThrow(
        new DataAccessResourceFailureException("Server down"));
    User updated = userService.updateLastActive(testUser.getEmail(), id, testUser);
    fail(); //this shouldn't run
  }

  @Test
  public void isAdminReturnsTrueIfUserIsAdmin() {
    User admin = new User();
    admin.setRole(ADMIN);
    admin.setEmail("admin@email.com");
    when(userRepository.findByEmail(anyString())).thenReturn(admin);
    assertTrue(userService.isAdmin("admin@email.com"));
  }

  @Test
  public void isAdminReturnsFalseIfUserIsNotAdmin() {
    User admin = new User();
    admin.setRole(CUSTOMER);
    admin.setEmail("admin@email.com");
    when(userRepository.findByEmail(anyString())).thenReturn(admin);
    assertFalse(userService.isAdmin("admin@email.com"));
  }

  @Test
  public void isAdminReturnFalseIfUserRoleIsNull() {
    User admin = new User();
    admin.setRole(null);
    admin.setEmail("admin@email.com");
    when(userRepository.findByEmail(anyString())).thenReturn(admin);
    assertFalse(userService.isAdmin("admin@email.com"));
  }

}
