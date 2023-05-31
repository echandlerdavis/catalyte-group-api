package io.catalyte.training.sportsproducts.domains.user;

/**
 * This interface provides an abstraction layer for the User Service
 */
public interface UserService {

  User updateUser(User user);

  User updateUser(String credentials, Long id, User user);

  User createUser(User user);

  public User getUserByEmail(String email);

  public User updateLastActive(String credentials, Long id, User user);

  public Boolean isAdmin(String email);
}
