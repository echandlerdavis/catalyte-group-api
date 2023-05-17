package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.purchase.CreditCard;
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
  //TODO: generate DeliveryAddress
  //TODO: add date to purchase
  //TODO: add shipping cost to purchase
  //TODO: calculate shipping costs based on delivery address state
  //TODO: Generate lineitems
  //TODO: generate user information

}


