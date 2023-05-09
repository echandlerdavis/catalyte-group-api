package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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

  ProductFactory productFactory;

  List<Review> testReviewsList = new ArrayList<>();

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);


  }

  private void setTestReviews(){

  }

}
