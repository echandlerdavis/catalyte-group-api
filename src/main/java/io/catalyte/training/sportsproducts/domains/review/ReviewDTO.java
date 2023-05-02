package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.user.User;
import java.time.Instant;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReviewDTO {
  @NotBlank
  private String title;
  @NotNull
  @Min(1)
  @Max(5)
  private int rating;
  @NotBlank
  private String review;
  private Instant createdAt;
  private User user;
//  private Long id;
  public ReviewDTO(){};

  public ReviewDTO(String title, int rating, String review, Instant createdAt, User user) {
    this.title = title;
    this.rating = rating;
    this.review = review;
    this.createdAt = createdAt;
    this.user = user;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
}
