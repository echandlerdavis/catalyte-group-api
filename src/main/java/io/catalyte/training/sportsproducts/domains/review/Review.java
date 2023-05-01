package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.user.User;
import java.time.Instant;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

//TODO: Figure out if this should just be connected to the Products and therefore I don't need all this mess.
public class Review {
  private String title;
  private int rating;
  private String review;
  @CreatedDate
  private Instant createdAt;
  @CreatedBy
  private User user;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Review(){

  }
  public Review(String title, int rating, String review, Instant createdAt, User user, Long id) {
    this.title = title;
    this.rating = rating;
    this.review = review;
    this.createdAt = createdAt;
    this.user = user;
    this.id = id;
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
  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
