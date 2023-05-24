package io.catalyte.training.sportsproducts.domains.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.catalyte.training.sportsproducts.domains.product.Product;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "reviews")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private Double rating;
  private String review;
  private String createdAt;
  private String editedAt;
  private String userName;
  private String userEmail;
  @ManyToOne
  @JsonIgnore
  private Product product;
  public Review(){}
  public Review(String title, Double rating, String review, String createdAt, String editedAt, String userName, String userEmail, Product product) {
    this.title = title;
    this.rating = rating;
    this.review = review;
    this.createdAt = createdAt;
    this.editedAt = editedAt;
    this.userName = userName;
    this.userEmail = userEmail;
    this.product = product;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getEditedAt() {
    return editedAt;
  }

  public void setEditedAt(String editedAt) {
    this.editedAt = editedAt;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
