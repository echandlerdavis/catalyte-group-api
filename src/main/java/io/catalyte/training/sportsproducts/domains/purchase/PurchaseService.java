package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.domains.product.Product;
import java.util.List;

public interface PurchaseService {

  Purchase savePurchase(Purchase purchaseToSave);

  List<Purchase> findAllPurchases();

  List<Purchase> findByBillingAddressEmail(String email);

  List<StateEnumDTO> getStateOptions();

  List<Long> getProductIdsPurchasedByBillingAddressEmail(String email);

}
