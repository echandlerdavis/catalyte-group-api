package io.catalyte.training.sportsproducts.domains.product;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ProductServiceImpl.class)
public class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productServiceImpl;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ProductRepository productRepository;

  Product testProduct1;

  Product testProduct2;

  ProductFactory productFactory;

  List<Product> testProductsList = new ArrayList<>();

  List<String> brands = new ArrayList<>();
  List<String> categories = new ArrayList<>();
  List<String> demographics = new ArrayList<>();
  List<String> prices = new ArrayList<>();
  List<String> primaryColors = new ArrayList<>();
  List<String> materials = new ArrayList<>();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    setTestProducts();

    when(productRepository.findById(anyLong())).thenReturn(Optional.of(testProduct1));
    when(productRepository.findAll()).thenReturn(testProductsList);

  }

  private void setTestProducts(){

    // Create Two Random Test Products
    productFactory = new ProductFactory();
    testProduct1 = new Product(
            "Test1",
            "product created for testing purposes",
            "Men",
            "Baseball",
            "Hoodie",
            "2003-12-02",
            "Champion",
            "www.myimageurl.com",
            "Nylon",
            1,
            180.35,
            false,
            "po-6123888",
            "sc88763",
            "#f092b0",
            "#51b46d");
    testProduct2 = new Product(
            "Test2",
            "product created for testing purposes",
            "Women",
            "Soccer",
            "Pant",
            "2014-09-24",
            "Adidas",
            "www.myimageurl.com",
            "Polyester",
            1,
            75.95,
            true,
            "po-2151664",
            "sc72141",
            "#3079ab",
            "sc72141");

    testProductsList.add(testProduct1);
    testProductsList.add(testProduct2);
  }

  @Test
  public void getProductByIdReturnsProduct() {
    Product actual = productServiceImpl.getProductById(123L);
    assertEquals(testProduct1, actual);
  }

  @Test
  public void getProductByIdThrowsErrorWhenNotFound() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFound.class, () -> productServiceImpl.getProductById(123L));
  }

  @Test
  public void getProductByOneBrandReturnsListOfProducts() {
    List<Product> actual = productServiceImpl.getProductsByBrands(productRepository.findAll(), testProduct2.getBrand());
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoBrandsListOfProducts() {
    brands.add(testProduct1.getBrand());
    brands.add(testProduct2.getBrand());
    String brandsString = String.join("%7C",brands);
    List<Product> actual = productServiceImpl.getProductsByBrands(productRepository.findAll(), brandsString);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOneCategoryReturnsListOfProducts() {
    categories.add(testProduct2.getCategory());
    List<Product> actual = productServiceImpl.getProductsByCategories(productRepository.findAll(), testProduct2.getCategory());
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoCategoriesListOfProducts() {
    categories.add(testProduct1.getCategory());
    categories.add(testProduct2.getCategory());
    String categoryString = String.join("%7C",categories);
    List<Product> actual = productServiceImpl.getProductsByCategories(productRepository.findAll(), categoryString);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOneDemographicReturnsListOfProducts() {

    List<Product> actual = productServiceImpl.getProductsByDemographics(productRepository.findAll(), testProduct2.getDemographic());
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoDemographicsListOfProducts() {
    demographics.add(testProduct1.getDemographic());
    demographics.add(testProduct2.getDemographic());
    String demographicsString = String.join("%7C",demographics);
    List<Product> actual = productServiceImpl.getProductsByDemographics(productRepository.findAll(), demographicsString);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOnePriceReturnsThrowsError() {

    assertThrows(BadRequest.class, () -> productServiceImpl.getProductsByPrice(productRepository.findAll(), String.valueOf(testProduct2.getPrice())));
  }

  @Test
  public void getProductByTwoPricesReturnsListOfProducts() {
    prices.add(String.valueOf(testProduct1.getPrice()));
    prices.add(String.valueOf(testProduct2.getPrice()));
    String pricesString = String.join("%7C",prices);
    List<Product> actual = productServiceImpl.getProductsByPrice(productRepository.findAll(), pricesString);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByTwoPricesThrowsErrorIfOnePriceIsNotANumber() {
    prices.add("abc");
    prices.add(String.valueOf(testProduct2.getPrice()));
    assertThrows(BadRequest.class, () -> productServiceImpl.getProductsByPrice(productRepository.findAll(), prices.get(0)));
  }

  @Test
  public void getProductByThreePricesThrowsError() {
    prices.add(String.valueOf(testProduct1.getPrice()));
    prices.add(String.valueOf(testProduct2.getPrice()));
    prices.add("10.00");
    String pricesString = String.join("%7C",prices);
    assertThrows(BadRequest.class, () -> productServiceImpl.getProductsByPrice(productRepository.findAll(), pricesString));
  }

  @Test
  public void getProductByOnePrimaryColorReturnsListOfProducts() {

    List<Product> actual = productServiceImpl.getProductsByPrimaryColors(productRepository.findAll(), testProduct2.getPrimaryColorCode());
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoPrimaryColorsReturnsListOfProducts() {
    primaryColors.add(testProduct1.getPrimaryColorCode());
    primaryColors.add(testProduct2.getPrimaryColorCode());
    String primaryColorsString = String.join("%7C",primaryColors);
    List<Product> actual = productServiceImpl.getProductsByPrimaryColors(productRepository.findAll(), primaryColorsString);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOneMaterialReturnsListOfProducts() {
    List<Product> actual = productServiceImpl.getProductsByMaterials(productRepository.findAll(), testProduct2.getMaterial());
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoMaterialsReturnsListOfProducts() {
    materials.add(testProduct1.getMaterial());
    materials.add(testProduct2.getMaterial());
    String materialsString = String.join("%7C",materials);
    List<Product> actual = productServiceImpl.getProductsByMaterials(productRepository.findAll(), materialsString);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByMultipleFiltersReturnsListOfProducts() {
    brands.addAll(Arrays.asList(testProduct1.getBrand(), testProduct2.getBrand()));
    categories.addAll(Arrays.asList(testProduct1.getCategory(), testProduct2.getCategory()));
    demographics.addAll(Arrays.asList(testProduct1.getDemographic(), testProduct2.getDemographic()));
    prices.addAll(Arrays.asList(String.valueOf(testProduct1.getPrice()), String.valueOf(testProduct2.getPrice())));
    primaryColors.addAll(Arrays.asList(testProduct1.getPrimaryColorCode(), testProduct2.getPrimaryColorCode()));
    materials.addAll(Arrays.asList(testProduct1.getMaterial(), testProduct2.getMaterial()));

    HashMap<String, String> filters = new HashMap<>();

    filters.put("brand", String.join("%7C",brands));
    filters.put("category",String.join("%7C",categories));
    filters.put("price", String.join("%7C",prices));
    filters.put("primaryColor", String.join("%7C",prices));
    filters.put("material", String.join("%7C",materials));

    List<Product> actual = productServiceImpl.getProductsByFilters(filters);
    assertEquals(testProductsList, actual);
  }
}
