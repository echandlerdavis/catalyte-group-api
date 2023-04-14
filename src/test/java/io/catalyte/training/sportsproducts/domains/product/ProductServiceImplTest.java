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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    brands.add(testProduct2.getBrand());
    List<Product> actual = productServiceImpl.getProductsByBrands(productRepository.findAll(), brands);
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoBrandsListOfProducts() {
    brands.add(testProduct1.getBrand());
    brands.add(testProduct2.getBrand());
    List<Product> actual = productServiceImpl.getProductsByBrands(productRepository.findAll(), brands);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOneCategoryReturnsListOfProducts() {
    categories.add(testProduct2.getCategory());
    List<Product> actual = productServiceImpl.getProductsByCategories(productRepository.findAll(), categories);
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoCategoriesListOfProducts() {
    categories.add(testProduct1.getCategory());
    categories.add(testProduct2.getCategory());
    List<Product> actual = productServiceImpl.getProductsByCategories(productRepository.findAll(), categories);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOneDemographicReturnsListOfProducts() {
    demographics.add(testProduct2.getDemographic());
    List<Product> actual = productServiceImpl.getProductsByDemographics(productRepository.findAll(), demographics);
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoDemographicsListOfProducts() {
    demographics.add(testProduct1.getDemographic());
    demographics.add(testProduct2.getDemographic());
    List<Product> actual = productServiceImpl.getProductsByDemographics(productRepository.findAll(), demographics);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOnePriceReturnsThrowsError() {
    prices.add(String.valueOf(testProduct2.getPrice()));

    assertThrows(BadRequest.class, () -> productServiceImpl.getProductsByPrice(productRepository.findAll(), prices));
  }

  @Test
  public void getProductByTwoPricesReturnsListOfProducts() {
    prices.add(String.valueOf(testProduct1.getPrice()));
    prices.add(String.valueOf(testProduct2.getPrice()));
    List<Product> actual = productServiceImpl.getProductsByPrice(productRepository.findAll(), prices);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByTwoPricesThrowsErrorIfOnePriceIsNotANumber() {
    prices.add("abc");
    prices.add(String.valueOf(testProduct2.getPrice()));

    assertThrows(BadRequest.class, () -> productServiceImpl.getProductsByPrice(productRepository.findAll(), prices));
  }

  @Test
  public void getProductByThreePricesThrowsError() {
    prices.add(String.valueOf(testProduct1.getPrice()));
    prices.add(String.valueOf(testProduct2.getPrice()));
    prices.add("10.00");
    assertThrows(BadRequest.class, () -> productServiceImpl.getProductsByPrice(productRepository.findAll(), prices));
  }

  @Test
  public void getProductByOnePrimaryColorReturnsListOfProducts() {
    primaryColors.add(testProduct2.getPrimaryColorCode());
    List<Product> actual = productServiceImpl.getProductsByPrimaryColors(productRepository.findAll(), primaryColors);
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoPrimaryColorsReturnsListOfProducts() {
    primaryColors.add(testProduct1.getPrimaryColorCode());
    primaryColors.add(testProduct2.getPrimaryColorCode());
    List<Product> actual = productServiceImpl.getProductsByPrimaryColors(productRepository.findAll(), primaryColors);
    assertEquals(testProductsList, actual);
  }

  @Test
  public void getProductByOneMaterialReturnsListOfProducts() {
    materials.add(testProduct2.getMaterial());
    List<Product> actual = productServiceImpl.getProductsByMaterials(productRepository.findAll(), materials);
    assertEquals(Arrays.asList(testProduct2), actual);
  }

  @Test
  public void getProductByTwoMaterialsReturnsListOfProducts() {
    materials.add(testProduct1.getMaterial());
    materials.add(testProduct2.getMaterial());
    List<Product> actual = productServiceImpl.getProductsByMaterials(productRepository.findAll(), materials);
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

    MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();

    filters.addAll("brand", brands);
    filters.addAll("category",categories);
    filters.addAll("price", prices);
    filters.addAll("primaryColor", primaryColors);
    filters.addAll("material", materials);

    List<Product> actual = productServiceImpl.getProductsByFilters(filters);
    assertEquals(testProductsList, actual);
  }
}
