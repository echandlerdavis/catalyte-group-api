package io.catalyte.training.sportsproducts.domains.user;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * User entity in database
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "email", insertable = false, updatable = false)
  String email;
  @Column
  String role;
  @Column
  String firstName;
  @Column
  String lastName;
  @Embedded
  BillingAddress billingAddress;

  public User(String email, String firstName, String lastName, BillingAddress billingAddress) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.billingAddress = billingAddress;
  }

  public User(Long id, String email, String role, String firstName, String lastName, BillingAddress billingAddress) {
    this.id = id;
    this.email = email;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.billingAddress = billingAddress;
  }

  public User(String email, String role, String firstName, String lastName, BillingAddress billingAddress) {
    this.email = email;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.billingAddress = billingAddress;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public BillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(BillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", role='" + role + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", billingAddress='" + billingAddress + '\'' +
        '}';
  }

  public static final List<User> TEST_USERS = Arrays.asList(
    new User("cgandy@catalyte.io","Casey", "Gandy",
            new BillingAddress("123 Main St", "", "Seattle", "WA", 98101)),
    new User("cdavis@catalyte.io","Chandler", "Davis",
            new BillingAddress("123 Main St", "", "Seattle", "WA", 98101)),
    new User("dduval@catalyte.io","Devin", "Duval",
            new BillingAddress("123 Main St", "", "Seattle", "WA", 98101)),
    new User("bmiller@catalyte.io","Blake", "Miller",
            new BillingAddress("123 Main St", "", "Seattle", "WA", 98101)),
    new User("kfreeman@catalyte.io", "Kaschae", "Freeman",
            new BillingAddress("123 Main St", "", "Seattle", "WA", 98101))
  );
}
