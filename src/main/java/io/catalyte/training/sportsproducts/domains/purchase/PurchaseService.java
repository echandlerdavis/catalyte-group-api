package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.domains.purchase.StateEnum.stateJson;
import java.util.List;
import java.util.Map;

public interface PurchaseService {

  Purchase savePurchase(Purchase purchaseToSave);

  List<Purchase> findAllPurchases();

  List<Purchase> findByBillingAddressEmail(String email);
  List<stateJson> getStateOptions();

}
