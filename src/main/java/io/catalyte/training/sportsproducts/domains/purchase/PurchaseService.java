package io.catalyte.training.sportsproducts.domains.purchase;

import java.util.List;
import java.util.Map;

public interface PurchaseService {

  Purchase savePurchase(Purchase purchaseToSave);

  List<Purchase> findAllPurchases();

  List<Purchase> findByBillingAddressEmail(String email);
  List<Map<String, Double>> getStateOptions();

}
