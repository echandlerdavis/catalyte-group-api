package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.promotions.PromotionalCode;
import io.catalyte.training.sportsproducts.domains.purchase.CreditCard;
import io.catalyte.training.sportsproducts.domains.purchase.DeliveryAddress;
import io.catalyte.training.sportsproducts.domains.purchase.LineItem;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.domains.user.UserBillingAddress;
import io.catalyte.training.sportsproducts.domains.purchase.BillingAddress;
import io.catalyte.training.sportsproducts.domains.purchase.StateEnum;
import io.catalyte.training.sportsproducts.domains.user.User;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.dao.DataAccessException;

public class PurchaseFactory {
  private static final Random random = new Random();
  private static final int MAX_QUANTITY = 100;

  private List<Product> availableProducts;
  private List<PromotionalCode> availablePromoCodes;
  /*oldest date that will appear on a purchase*/
  private Date minDate;
  /*most recent date that will appear on a purchase */
  private Date maxDate;
  private static final UserFactory userFactory = new UserFactory();
  PurchaseFactory(){
    //set minDate and maxDate
    Date today = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    cal.set(cal.get(Calendar.YEAR) - 5, 1, 1);
    this.minDate = cal.getTime();
    this.maxDate = today;
  }

  public void setAvailablePromoCodes(List<PromotionalCode> codes){
    this.availablePromoCodes = codes;
  }

  public Product getRandomProduct(){
    return availableProducts.get(random.nextInt(availableProducts.size()));
  }
  public PromotionalCode getRandomPromoCode(){
    return availablePromoCodes.get(random.nextInt(availablePromoCodes.size()));
  }

  public LineItem generateLineItem(Product p){
    LineItem line = new LineItem();
    line.setProduct(p);
    line.setQuantity(random.nextInt(MAX_QUANTITY));
    return line;
  }
  public LineItem generateLineItem(){
    LineItem line = generateLineItem(getRandomProduct());
    return line;
  };
  public LineItem generateLineItem(Purchase purchase){
    LineItem line = generateLineItem();
    line.setPurchase(purchase);
    return line;
  }
  public void setAvailableProducts(
      List<Product> availableProducts) {
    this.availableProducts = availableProducts;
  }

  public List<Product> getAvailableProducts() {
    return availableProducts;
  }

  /**
   * Get a Purchase BillingAddress from the given user
   * @param user User
   * @return BillingAddress
   */
  public static BillingAddress getBillingAddressFromUser(User user) {
    return new BillingAddress(
        user.getBillingAddress().getBillingStreet(),
        user.getBillingAddress().getBillingStreet2(),
        user.getBillingAddress().getBillingCity(),
        user.getBillingAddress().getBillingState(),
        user.getBillingAddress().getBillingZip(),
        user.getEmail(),
        user.getBillingAddress().getPhone()
    );
  }
    /**
     * Get a purchase package DeliveryAddress for the user
     * @param user User
     * @return DeliveryAddress
     */
    public static DeliveryAddress getDeliveryAddressFromUser(User user){
      return new DeliveryAddress(
          user.getFirstName(),
          user.getLastName(),
          user.getBillingAddress().getBillingStreet(),
          user.getBillingAddress().getBillingStreet2(),
          user.getBillingAddress().getBillingCity(),
          user.getBillingAddress().getBillingState(),
          user.getBillingAddress().getBillingZip()
      );
  }

  public Date getRandomHistoricalDate(){
      long startMilliseconds = this.minDate.getTime();
      long endMilliseconds = this.maxDate.getTime();
      long randomMilliseconds = ThreadLocalRandom
          .current()
          .nextLong(startMilliseconds, endMilliseconds);
      return new Date(randomMilliseconds);
  }

  public Purchase generateRandomPurchase(User user, PromotionalCode promoCode){
      //the new purchase
      Purchase purchase = new Purchase();
      //get a random date
      Date dt = getRandomHistoricalDate();
      purchase.setDate(dt);
      //get address objects from the user
      DeliveryAddress deliver = getDeliveryAddressFromUser(user);
      BillingAddress billing = getBillingAddressFromUser(user);
      purchase.setDeliveryAddress(deliver);
      purchase.setBillingAddress(billing);
      // add the credit card
      purchase.setCreditCard(
          UserFactory.generateRandomCreditCard(user.getFirstName() + " " + user.getLastName()
          ));
      //set promocode
      purchase.setPromoCode(promoCode);
      //generate lineItems
      int numberOfProducts = random.nextInt(MAX_QUANTITY);
      if (numberOfProducts == 0){
        numberOfProducts = 1;
      }
      Set<LineItem> lineItems = new HashSet<>();
      Set<Product> products = new HashSet<>();
      while (lineItems.size() < numberOfProducts){
        Product randomProduct = getRandomProduct();
        if (!products.contains(randomProduct)) {
          products.add(randomProduct);
          //make a new line item
          LineItem line = generateLineItem(randomProduct);
          //add line item
          lineItems.add(line);
        }
        purchase.setProducts(lineItems);
      }
      //set shipping costs
      if (purchase.applyShippingCharge()) {
        purchase.setShippingCharge(
            StateEnum.getShippingByName(purchase.getDeliveryAddress().getDeliveryState())
        );
      }
      return purchase;
  }
  public Purchase generateRandomPurchase(){
      User user = userFactory.generateRandomUser();
      PromotionalCode promoCode = null;
      if (random.nextBoolean()){
        promoCode = getRandomPromoCode();
      }
      return generateRandomPurchase(user, promoCode);
  }

  public Purchase generateRandomPurchase(User user){
    PromotionalCode promoCode = null;
    if (random.nextBoolean()){
      promoCode = getRandomPromoCode();
    }
    return generateRandomPurchase(user, promoCode);
  }
}


