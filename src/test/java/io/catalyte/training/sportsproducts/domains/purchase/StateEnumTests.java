package io.catalyte.training.sportsproducts.domains.purchase;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StateEnumTests {
  private double LOWER_48_SHIPPING;
  private double OTHER_SHIPPING;

  @Before
  public void setUp(){
    LOWER_48_SHIPPING = 10;
    OTHER_SHIPPING = 5;
  }

  @Test
  public void formatStateNameSingleNameTest(){
    String testString = "wASHINGTON";
    String expected = "Washington";
    String actual = StateEnum.formatStateName(testString);
    assertEquals(expected, actual);
  }

  @Test
  public void formatStateNameMultipleNameTest(){
    String testString = "nEW mEXICO";
    String expected = "New Mexico";
    String actual = StateEnum.formatStateName(testString);
    assertEquals(expected, actual);
  }


}
