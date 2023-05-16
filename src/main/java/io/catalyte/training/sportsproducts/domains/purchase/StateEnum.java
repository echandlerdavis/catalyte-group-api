package io.catalyte.training.sportsproducts.domains.purchase;

public enum StateEnum {
  AL("Alabama", shippingCosts.DEFAULT.cost),
  AK("Alaska", shippingCosts.HIGHER.cost),
  AZ("Arizona", shippingCosts.DEFAULT.cost),
  AR("Arkansas", shippingCosts.DEFAULT.cost),
  CA("California", shippingCosts.DEFAULT.cost),
  CO("Colorado", shippingCosts.DEFAULT.cost),
  DE("Delaware", shippingCosts.DEFAULT.cost),
  DC("District of Columbia", shippingCosts.DEFAULT.cost),
  FL("Florida", shippingCosts.DEFAULT.cost),
  GA("Georgia", shippingCosts.DEFAULT.cost),
  HI("Hawaii", shippingCosts.HIGHER.cost),
  ID("Idaho", shippingCosts.DEFAULT.cost),
  IL("Illinois", shippingCosts.DEFAULT.cost),
  IN("Indiana", shippingCosts.DEFAULT.cost),
  IA("Iowa", shippingCosts.DEFAULT.cost),
  KS("Kansas", shippingCosts.DEFAULT.cost),
  KY("Kentucky", shippingCosts.DEFAULT.cost),
  LA("Louisiana", shippingCosts.DEFAULT.cost),
  ME("Maine", shippingCosts.DEFAULT.cost),
  MA("Massachusetts", shippingCosts.DEFAULT.cost),
  MI("Michigan", shippingCosts.DEFAULT.cost),
  MN("Minnesota", shippingCosts.DEFAULT.cost),
  MS("Mississippi", shippingCosts.DEFAULT.cost),
  MO("Missouri", shippingCosts.DEFAULT.cost),
  MT("Montana", shippingCosts.DEFAULT.cost),
  NE("Nebraska", shippingCosts.DEFAULT.cost),
  NV("Nevada", shippingCosts.DEFAULT.cost),
  NH("New Hampshire", shippingCosts.DEFAULT.cost),
  NJ("New Jersey", shippingCosts.DEFAULT.cost),
  NM("New Mexico", shippingCosts.DEFAULT.cost),
  NY("New York", shippingCosts.DEFAULT.cost),
  NC("North Carolina", shippingCosts.DEFAULT.cost),
  ND("North Dakota", shippingCosts.DEFAULT.cost),
  OH("Ohio", shippingCosts.DEFAULT.cost),
  OK("Oklahoma", shippingCosts.DEFAULT.cost),
  OR("Oregon", shippingCosts.DEFAULT.cost),
  PA("Pennsylvania", shippingCosts.DEFAULT.cost),
  RI("Rhode Island", shippingCosts.DEFAULT.cost),
  SC("South Carolina", shippingCosts.DEFAULT.cost),
  SD("South Dakota", shippingCosts.DEFAULT.cost),
  TN("Tennessee", shippingCosts.DEFAULT.cost),
  TX("Texas", shippingCosts.DEFAULT.cost),
  UT("Utah", shippingCosts.DEFAULT.cost),
  VT("Vermont", shippingCosts.DEFAULT.cost),
  VA("Virginia", shippingCosts.DEFAULT.cost),
  WA("Washington", shippingCosts.DEFAULT.cost),
  WV("West Virginia", shippingCosts.DEFAULT.cost),
  WI("Wisconsin", shippingCosts.DEFAULT.cost),
  WY("Wyoming", shippingCosts.DEFAULT.cost);

  private enum shippingCosts {

    DEFAULT(5),
    HIGHER(10);
    public final double cost;
    private shippingCosts (double value) {
      this.cost = value;
    }
  }

  public final String fullName;
  public final double shippingCost;

  private StateEnum(String fullName, double shippingCost){
    this.fullName = fullName;
    this.shippingCost = shippingCost;
  }

  public static StateEnum getEnumByAbbreviation(String abbreviation){

  }

}
