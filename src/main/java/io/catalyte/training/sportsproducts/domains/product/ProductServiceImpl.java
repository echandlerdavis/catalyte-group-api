package io.catalyte.training.sportsproducts.domains.product;

import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the implementation for the ProductService interface.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products from the database, optionally making use of an example if it is passed.
     *
     * @param product - an example product to use for querying
     * @return - a list of products matching the example, or all products if no example was passed
     */
    public List<Product> getProducts(Product product) {
        try {
            return productRepository.findAll(Example.of(product));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new ServerError(e.getMessage());
        }
    }

    /**
     * Retrieves the product with the provided id from the database.
     *
     * @param id - the id of the product to retrieve
     * @return - the product
     */
    public Product getProductById(Long id) {
        Product product;

        try {
            product = productRepository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new ServerError(e.getMessage());
        }

        if (product != null) {
            return product;
        } else {
            logger.info("Get by id failed, it does not exist in the database: " + id);
            throw new ResourceNotFound("Get by id failed, it does not exist in the database: " + id);
        }
    }

    /**
     * @return a list of unique Types in the database
     */
    public List<String> getDistinctTypes() {
        try {
            return productRepository.findDistinctTypes();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new ServerError(e.getMessage());
        }
    }

    /**
     * @return a list of unique Categories in the database
     */
    public List<String> getDistinctCategories() {
        try {
            return productRepository.findDistinctCategories();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new ServerError(e.getMessage());
        }
    }

    /**
     * Adds a product to the database
     *
     * @param products - list of product objects
     * @return list of product objects that are added to database
     */
    public List<Product> addProducts(List<Product> products) {
        try {
            return productRepository.saveAll(products);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new ServerError(e.getMessage());
        }
    }

    @Override
    public List<Product> getProductsByFilters(MultiValueMap<String, List<String>> filters) {
        return null;
    }

    public List<Product> getProductsByBrands(List<Product> products, List<String> brands) {
        // Create new list for brand names to allow matching without case sensitivity
        List<String> brandNames = new ArrayList<>();
        // Add lowercase of filter to brand names list
        brands.forEach(brand -> brandNames.add(brand.toLowerCase()));
        // Remove ongoing products list if product's brand does not match any brand filters
        products.removeIf(product -> !brandNames.contains(product.getBrand().toLowerCase()));

        return products;
    }

    public List<Product> getProductsByCategories(List<Product> products, List<String> categories) {

        List<String> categoryNames = new ArrayList<>();

        categories.forEach(brand -> categoryNames.add(brand.toLowerCase()));

        products.removeIf(product -> !categoryNames.contains(product.getCategory().toLowerCase()));

        return products;
    }

    public List<Product> getProductsByDemographics(List<Product> products, List<String> demographics) {

        List<String> demographicNames = new ArrayList<>();

        demographics.forEach(brand -> demographicNames.add(brand.toLowerCase()));

        products.removeIf(product -> !demographicNames.contains(product.getDemographic().toLowerCase()));

        return products;

    }

    public List<Product> getProductsByPrice(List<Product> products, List<String> price) {
        return null;
    }

    public List<Product> getProductsByPrimaryColors(List<Product> products, List<String> primaryColors) {

        List<String> primaryColorsCodes = new ArrayList<>();

        primaryColors.forEach(brand -> primaryColorsCodes.add(brand.toLowerCase()));

        products.removeIf(product -> !primaryColorsCodes.contains(product.getPrimaryColorCode().toLowerCase()));

        return products;
    }

    public List<Product> getProductsByMaterials(List<Product> products, List<String> materials) {

        List<String> materialNames = new ArrayList<>();

        materials.forEach(brand -> materialNames.add(brand.toLowerCase()));

        products.removeIf(product -> !materialNames.contains(product.getMaterial().toLowerCase()));

        return products;
    }
}