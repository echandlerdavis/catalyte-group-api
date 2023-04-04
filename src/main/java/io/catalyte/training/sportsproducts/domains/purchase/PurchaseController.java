package io.catalyte.training.sportsproducts.domains.purchase;

import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes endpoints for the purchase domain
 */
@RestController
@RequestMapping(value = PURCHASES_PATH)
public class PurchaseController {

  Logger logger = LogManager.getLogger(PurchaseController.class);

  private PurchaseService purchaseService;

  @Autowired
  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  /**
   * Handles a POST request to /purchases. This creates a new purchase that gets saved to the database.
   *
   * @param purchase
   * @return
   */
  @PostMapping
  public ResponseEntity savePurchase(@RequestBody Purchase purchase) {

    purchaseService.savePurchase(purchase);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Handles a GET request directed at /purchases.
   *
   * @return ResponseEntity with a body containing an empty array and http status 200
   */
  @GetMapping
  public ResponseEntity findAllPurchases() {
    List<Purchase> emptyList = new ArrayList<>();
    return new ResponseEntity<>(emptyList, HttpStatus.OK);
  }
}
