package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
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
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.dao.DataAccessException;

public class PurchaseFactory {
  private static final Random random = new Random();
  private static final int MAX_QUANTITY = 100;

  private List<Product> availableProducts;
  private static final UserFactory userFactory = new UserFactory();
  //TODO: calculate shipping costs based on delivery address state
  //TODO: Generate lineitems

  public Product getRandomProduct(){
    return availableProducts.get(random.nextInt(availableProducts.size()));
  }
  public LineItem generateLineItem(){
    LineItem line = new LineItem();
    line.setProduct(getRandomProduct());
    line.setQuantity(random.nextInt(MAX_QUANTITY));
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


}


