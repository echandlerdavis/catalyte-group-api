package io.catalyte.training.sportsproducts.domains.user;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.auth.GoogleAuthService;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import io.catalyte.training.sportsproducts.exceptions.UnprocessableContent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataAccessException;

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

  @BeforeEach
  public void setUp() {
    //set all the inner variables
    firstName = "Bob";
    lastName = "Belcher";
    email = "Bob@BobsBurgers.com";
    role = "BurgerMeister";
    id = 1l;
    lastActive = new Date();
    //set testUser
    setUserData(testUser, id, firstName, lastName, role, lastActive);
    //set Mocks
    //google mock
    when(googleAuthService.authenticateUser(any(), any())).thenReturn(true);
    //userRepo.findById mock
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
    //userRepo.save mock
    when(userRepository.save(any(User.class))).thenAnswer((u) ->{
      User copyUser = new User();
      User passedUser = u.getArgument(0);
      setUserData(copyUser,
                  passedUser.getId(),
                  passedUser.getFirstName(),
                  passedUser.getLastName(),
                  passedUser.getRole(),
                  passedUser.getLastActive());

      return copyUser;
    });
  }
  public void setUserData(User emptyUser, Long id, String firstName, String lastName, String role, Date lastActive){
    emptyUser.setId(id);
    emptyUser.setFirstName(firstName);
    emptyUser.setLastName(lastName);
    emptyUser.setRole(role);
    emptyUser.setLastActive(lastActive);
  }

  @Test
  public void updateLastActiveSetsNewerTimeTest() {
    Date updatedTime = userService.updateLastActive("abcd", testUser.getId());
    assertTrue(updatedTime.before(lastActive));
  }

}
