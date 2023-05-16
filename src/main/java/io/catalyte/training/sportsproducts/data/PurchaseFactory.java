package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.user.UserBillingAddress;
import io.catalyte.training.sportsproducts.domains.purchase.BillingAddress;
import io.catalyte.training.sportsproducts.domains.purchase.StateEnum;
import io.catalyte.training.sportsproducts.domains.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PurchaseFactory {
  private static final Random random = new Random();
  private static final int MAX_NAME_LENGTH = 10;
  private static final int MAX_STREET_DIGITS = 4;
  public static final List<User> ACTUAL_USERS = new ArrayList();
  //TODO: generate BillingAddress
  //TODO: generate DeliveryAddress
  //TODO: add date to purchase
  //TODO: add shipping cost to purchase
  //TODO: calculate shipping costs based on delivery address state
  //TODO: Generate lineitems
  //TODO: generate user information
  public static StateEnum getRandomState() {
    int size = StateEnum.values().length;
    return StateEnum.values()[random.nextInt(size)];
  }

  public static String generateRandomString(int length){
    int leftLimit = 97;
    int rightLimit = 122;
    return random.ints(leftLimit, rightLimit + 1)
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  public static int getRandomInt(int maxDigits) {
    String numberString = "";
    int digitCount = 0;
    while (digitCount++ < maxDigits) {
      numberString = numberString + Integer.toString(random.nextInt(9));
    }
    return Integer.parseInt(numberString);
  }

  public static String generateRandomPhoneNumber(){
    String areaCode = Integer.toString(getRandomInt(3));
    String prefix = Integer.toString(getRandomInt(3));
    String lineNumber = Integer.toString(getRandomInt(4));

    while(areaCode.length() < 3){
      areaCode += "0";
    }

    while(prefix.length() < 3){
      prefix += "0";
    }

    while(lineNumber.length() < 4) {
      lineNumber += "0";
    }
    return areaCode + "-" + prefix + "-" + lineNumber;
  }

  public static String generateRandomEmailAddress(){
    String userName = generateRandomString(random.nextInt(12));
    String domain = generateRandomString(random.nextInt(12));
    return userName + "@" + domain + ".com";
  }

  public static UserBillingAddress generateRandomUserBillingAddress(){
    final UserBillingAddress address = new UserBillingAddress();
    StateEnum state = getRandomState();
    String street1 = generateRandomString(random.nextInt(MAX_STREET_DIGITS)) + "St.";
    String city = generateRandomString(random.nextInt(MAX_NAME_LENGTH));
    String zip = Integer.toString(getRandomInt(5));
    while(zip.length() < 5){
      zip += "0";
    };
    address.setBillingStreet(street1);
    if (random.nextBoolean()){
      address.setBillingStreet2("#" + Integer.toString(getRandomInt(3)));
    }
    address.setBillingState(state.fullName);
    address.setBillingZip(Integer.valueOf(zip));
    address.setPhone(generateRandomPhoneNumber());

    return address;
  }

  private static void setActualUsers(){
    String domain = "@catalyte.io";
    String[] firstNames = {
        "Devin",
        "Casey",
        "Blake",
        "Chandler",
        "Kaschae"
    };
    String[] lastNames = {
        "Duval",
        "Gandy",
        "Miller",
        "Davis",
        "Freeman"
    };
    String[] emails = {
        "dduval" + domain,
        "cgandy" + domain,
        "bmiller" + domain,
        "cdavis" + domain,
        "kfreeman" + domain
    };
    for (int i = 0; i < firstNames.length; i++){
      ACTUAL_USERS.add(
          new User(emails[i],
              firstNames[i],
              lastNames[i],
              generateRandomUserBillingAddress()));
      };
    }

  static {
    setActualUsers();
  }
  public static void main(String[] args){};
}


