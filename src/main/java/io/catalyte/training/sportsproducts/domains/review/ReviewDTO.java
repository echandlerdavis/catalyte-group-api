package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.product.Product;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A DTO (Data Transfer Object) representing the input data for creating a review.
 */
public class ReviewDTO {

  private String title;
  private Double rating;
  private String review;
  private String createdAt;
  private String userName;
  private String userEmail;
  private Product product;

  private Boolean isActive;

  public ReviewDTO() {
  }

  ;

  public ReviewDTO(String title, Double rating, String review, String createdAt, String userName,
      String userEmail, Product product) {
    this.title = title;
    this.rating = rating;
    this.review = review;
    this.createdAt = createdAt;
    this.userName = userName;
    this.userEmail = userEmail;
    this.product = product;
    this.isActive = true;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
