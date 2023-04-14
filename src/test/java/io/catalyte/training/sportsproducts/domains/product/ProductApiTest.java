package io.catalyte.training.sportsproducts.domains.product;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import org.hamcrest.Matchers;
import org.hamcrest.core.Every;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApiTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    ProductFactory productFactory = new ProductFactory();

    Product testProduct1 = productFactory.createRandomProduct();
    Product testProduct2 = productFactory.createRandomProduct();

    List<String> brands = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    List<String> demographics = new ArrayList<>();
    List<String> prices = new ArrayList<>();
    List<String> primaryColors = new ArrayList<>();
    List<String> materials = new ArrayList<>();

    @Before
    public void setUp() {
        setTestProducts();
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private void setTestProducts() {
        productRepository.saveAll(Arrays.asList(testProduct1, testProduct2));
    }

    @After
    public void removeTestData() {
        productRepository.delete(testProduct1);
        productRepository.delete(testProduct2);
    }

    @Test
    public void getProductsReturns200() throws Exception {
        mockMvc.perform(get(PRODUCTS_PATH))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductByIdReturnsProductWith200() throws Exception {
        mockMvc.perform(get(PRODUCTS_PATH + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDistinctTypesReturnsWith200() throws Exception {
        mockMvc.perform(get(PRODUCTS_PATH + "/types"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDistinctCategoriesReturnsWith200() throws Exception {
        mockMvc.perform(get(PRODUCTS_PATH + "/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDistinctTypesReturnsAllAndOnlyUniqueTypes() throws Exception {

        //GET categories and check if it is returning each unique type, only once.
        mockMvc.perform(get(PRODUCTS_PATH + "/types"))
                .andExpect(ResultMatcher.matchAll(jsonPath("$", Matchers.containsInAnyOrder(
                        "Pant",
                        "Short",
                        "Shoe",
                        "Glove",
                        "Jacket",
                        "Tank Top",
                        "Sock",
                        "Sunglasses",
                        "Hat",
                        "Helmet",
                        "Belt",
                        "Visor",
                        "Shin Guard",
                        "Elbow Pad",
                        "Headband",
                        "Wristband",
                        "Hoodie",
                        "Flip Flop",
                        "Pool Noodle"))));
    }

    @Test
    public void getDistinctCategoriesReturnsAllAndOnlyUniqueCategories() throws Exception {


        //GET categories and check if it is returning each unique category, only once.
        mockMvc.perform(get(PRODUCTS_PATH + "/categories")).andExpect(ResultMatcher.matchAll(jsonPath("$", Matchers.containsInAnyOrder("Golf",
                "Soccer",
                "Basketball",
                "Hockey",
                "Football",
                "Running",
                "Baseball",
                "Skateboarding",
                "Boxing",
                "Weightlifting"))));
    }


    @Test
    public void getProductsByFilterQueryParamsWithOnlyBrandReturnsProductListWith200() throws Exception {

        String testBrand = testProduct1.getBrand();
        System.out.println("mockMvc = " + mockMvc.perform(get(PRODUCTS_PATH+"/filter?brand=" + testBrand )).andReturn().getResponse().getContentAsString());
        mockMvc.perform(get(PRODUCTS_PATH + "/filter?brand=" + testBrand))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].brand")
                        .value(Every.everyItem(is(testBrand))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithMultipleBrandsReturnsProductListWith200() throws Exception {
        brands.add(testProduct1.getBrand());
        brands.add(testProduct2.getBrand());
        String brandsString = String.join("&brand=", brands);



        mockMvc.perform(get(PRODUCTS_PATH + "/filter?brand=" + brandsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].brand").value(Every.everyItem(anyOf(is(brands.get(0)),is(brands.get(1))))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithOnlyCategoryReturnsProductListWith200() throws Exception {

        String testCategory = testProduct1.getCategory();

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?category=" + testCategory))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].category")
                        .value(Every.everyItem(is(testCategory))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithMultipleCategoriesReturnsProductListWith200() throws Exception {
        categories.add(testProduct1.getCategory());
        categories.add(testProduct2.getCategory());
        String categoriesString = String.join("&category=", categories);

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?category=" + categoriesString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].category")
                        .value(Every.everyItem(anyOf(is(categories.get(0)),is(categories.get(1))))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithOnlyDemographicReturnsProductListWith200() throws Exception {

        String testDemographic = testProduct1.getDemographic();

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?demographic=" + testDemographic))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].demographic")
                        .value(Every.everyItem(is(testDemographic))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithMultipleDemographicsReturnsProductListWith200() throws Exception {
        demographics.add(testProduct1.getDemographic());
        demographics.add(testProduct2.getDemographic());
        String demographicsString = String.join("&demographic=", demographics);

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?demographic=" + demographicsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].demographic")
                        .value(Every.everyItem(anyOf(is(demographics.get(0)),is(demographics.get(1))))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithOnlyPriceReturnsProductListWith200() throws Exception {
        prices.add(String.valueOf(testProduct1.getPrice()));
        prices.add(String.valueOf(testProduct2.getPrice()));
        String pricesString = String.join("&price=", prices);

        Double max;
        Double min;

        if(testProduct1.getPrice()> testProduct2.getPrice()){
            max = testProduct1.getPrice();
            min = testProduct2.getPrice();
        } else {
            max = testProduct2.getPrice();
            min = testProduct1.getPrice();
        }

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?price=" + pricesString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].price")
                        .value(Every.everyItem(anyOf(lessThanOrEqualTo(min),greaterThanOrEqualTo(max)))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithOnlyOnePriceReturns400() throws Exception {


        mockMvc.perform(get(PRODUCTS_PATH + "/filter?price=" + String.valueOf(testProduct1.getPrice())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProductsByFilterQueryParamsWithOnlyPrimaryColorReturnsProductListWith200() throws Exception {

        String testPrimaryColor = testProduct1.getPrimaryColorCode();

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?primaryColor=" + testPrimaryColor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].primaryColorCode")
                        .value(Every.everyItem(is(testPrimaryColor))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithMultiplePrimaryColorsReturnsProductListWith200() throws Exception {
        primaryColors.add(testProduct1.getPrimaryColorCode());
        primaryColors.add(testProduct2.getPrimaryColorCode());
        String primaryColorsString = String.join("&primaryColor=", primaryColors);

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?primaryColor=" + primaryColorsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].primaryColorCode")
                        .value(Every.everyItem(anyOf(is(primaryColors.get(0)), is(primaryColors.get(1))))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithOnlyMaterialReturnsProductListWith200() throws Exception {
        String testMaterial = testProduct1.getMaterial();

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?material=" + testMaterial))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].material")
                        .value(Every.everyItem(is(testMaterial))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithMultipleMaterialsReturnsProductListWith200() throws Exception {

        materials.add(testProduct1.getPrimaryColorCode());
        materials.add(testProduct2.getPrimaryColorCode());
        String materialsString = String.join("&material=", materials);

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?material=" + materialsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].material")
                        .value(Every.everyItem(oneOf(materials))));
    }

    @Test
    public void getProductsByFilterQueryParamsWithAllFiltersReturnsProductListWith200() throws Exception {
        brands.addAll(Arrays.asList(testProduct1.getBrand(), testProduct2.getBrand()));
        categories.addAll(Arrays.asList(testProduct1.getCategory(), testProduct2.getCategory()));
        demographics.addAll(Arrays.asList(testProduct1.getDemographic(), testProduct2.getDemographic()));
        prices.addAll(Arrays.asList(String.valueOf(testProduct1.getPrice()), String.valueOf(testProduct2.getPrice())));
        primaryColors.addAll(Arrays.asList(testProduct1.getPrimaryColorCode(), testProduct2.getPrimaryColorCode()));
        materials.addAll(Arrays.asList(testProduct1.getMaterial(), testProduct2.getMaterial()));

        MultiValueMap<String, List<String>> filters = new LinkedMultiValueMap<>();

        StringBuilder filterString = new StringBuilder();

        filters.add("brand", brands);
        String brandsString = String.join("&brand=", brands).replaceAll("\\s", "%20");
        filterString.append("brand=" + brandsString);

        filters.add("category",categories);
        String categoriesString = String.join("&category=", brands).replaceAll("\\s", "%20");
        filterString.append("&category=" + categoriesString);

        filters.add("price", prices);
        String pricesString = String.join("&price=", prices).replaceAll("\\s", "%20");
        filterString.append("&price=" + pricesString);

        filters.add("primaryColor", primaryColors);
        String primaryColorsString = String.join("&primaryColor=", primaryColors).replaceAll("\\s", "%20");
        filterString.append("&primaryColor=" + primaryColorsString);

        filters.add("material", materials);
        String materialsString = String.join("&material=", materials).replaceAll("\\s", "%20");
        filterString.append("&material=" + materialsString);

        System.out.println("filterString = " + filterString);

        mockMvc.perform(get(PRODUCTS_PATH + "/filter?" + filterString))
                .andExpect(status().isOk());

    }
}
