package io.catalyte.training.sportsproducts.domains.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;


/**
 * The ProductController exposes endpoints for product related actions.
 */
@RestController
@RequestMapping(value = PRODUCTS_PATH)
public class ProductController {

    Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(Product product) {
        logger.info("Request received for getProducts");

        return new ResponseEntity<>(productService.getProducts(product), HttpStatus.OK);
    }

    /**
     * GET request - returns a single product based on an id defined in the path variable
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("Request received for getProductsById: " + id);

        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    /**
     * GET request - returns all unique values of type
     */
    @GetMapping(value = "/types")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List> getDistinctType() {
        logger.info("Request received for getDistinctTypes");
        return new ResponseEntity<>(productService.getDistinctTypes(), HttpStatus.OK);
    }


    /**
     * GET request - returns all unique values of category
     */
    @GetMapping(value = "/categories")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List> getDistinctCategory() {
        logger.info("Request received for getDistinctCategory");
        return new ResponseEntity<>(productService.getDistinctCategories(), HttpStatus.OK);
    }

    /**
     * Handles a POST request to /products. This creates a new product object that gets saved to the database.
     *
     * @param products - list of product object(s)
     * @return product(s) added to database
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List> postProduct(@RequestBody List<Product> products) {
        productService.addProducts(products);
        return new ResponseEntity<>(productService.addProducts(products), HttpStatus.CREATED);
    }

    /**
     * Handles a GET request to /products/filters. This retrieves all products in the database with applied query.
     * Methods have been implemented to handle the following query params: brand, category, priceMin, priceMax, material, primaryColor, and demographic
     * For multiple values passed into a query param the URL-Encoded character for |, "%7C" should be placed in between in url request
     *
     * @param filters - the filters to be read from the request parameters
     * @return product(s) found matching the given filters
     */
    @GetMapping(value = "/filter")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List> getProductsByFilters(@RequestParam HashMap<String, String> filters) {
        logger.info("Request received for getProductsByFilters");
        return new ResponseEntity<>(productService.getProductsByFilters(filters), HttpStatus.OK);
    }

}
