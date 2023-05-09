package io.catalyte.training.sportsproducts.domains.review;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.review.Review;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ReviewServiceImpl.class)
public class ReviewServiceImplTest {

  @InjectMocks
  private ReviewServiceImpl reviewServiceImpl;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ReviewRepository reviewRepository;

  Review testReview1;
  Review testReview2;
  Review testReview3;
  Product testProduct1;
  Product testProduct2;
  ProductFactory productFactory;

  List<Review> testReviewsForProduct1List = new ArrayList<>();
  List<Review> testReviewsForProduct2List = new ArrayList<>();

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);

    setTestReviews();
    when(reviewRepository.findByProductId(anyLong())).thenReturn(testReviewsForProduct1List);

  }

  private void setTestReviews(){
    //Create three random reviews set to two null products.
    productFactory = new ProductFactory();
    testReview1 = new Review(
        "Test Review 1",
        4,
        "This is an example of a review for test product 1",
        "2005-11-01",
        "testUserNameOne",
        testProduct1
    );
    testReview2 = new Review(
        "Test Review 2",
        2,
        "This is a second example of a review for test product 1",
        "2007-25-03",
        "testUserNameTwo",
        testProduct1
    );
    testReview3 = new Review(
        "Test Review 3",
        5,
        "This is an example of a review for test product 2",
        "2010-13-01",
        "testUserNameThree",
        testProduct2
    );
    testProduct1 = new Product();
    testProduct2 = new Product();

    testReviewsForProduct1List.add(testReview1);
    testReviewsForProduct1List.add(testReview1);
    testReviewsForProduct2List.add(testReview2);

    testProduct1.setReviews(testReviewsForProduct1List);
    testProduct2.setReviews(testReviewsForProduct2List);

  }

  @Test
  public void getAllReviewByProductIdReturnsReviews(){
    List<Review> actual = reviewServiceImpl.getAllReviewsByProductId(123L);
    assertEquals(testReviewsForProduct1List, actual);
  }

  @Test
  public void getAllReviewsByProductIdThrowsErrorWhenNotFound(){
    when(reviewRepository.findByProductId(anyLong())).thenReturn(Optional.empty());
  }

}
