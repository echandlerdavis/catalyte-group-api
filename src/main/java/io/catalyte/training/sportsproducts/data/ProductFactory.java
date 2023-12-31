package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.product.Product;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This class provides tools for random generation of products.
 */
public class ProductFactory {
  private static final String[] brands = {
      "Nike",
      "Brooks",
      "Adidas",
      "Champion",
      "Hoka",
      "Lululemon",
      "Athleta",
      "New Balance",
      "Under Armor",
      "Puma",
      "Alo"
  };

  private static final String[] materials = {
      "Cotton",
      "Nylon",
      "Microfiber",
      "Polyester",
      "Gore-Tex",
      "Spandex",
      "Merino Wool",
      "Fleece"
  };
  private static final String[] colors = {
      "#000000", // white
      "#ffffff", // black
      "#39add1", // light blue
      "#3079ab", // dark blue
      "#c25975", // mauve
      "#e15258", // red
      "#f9845b", // orange
      "#838cc7", // lavender
      "#7d669e", // purple
      "#53bbb4", // aqua
      "#51b46d", // green
      "#e0ab18", // mustard
      "#637a91", // dark gray
      "#f092b0", // pink
      "#b7c0c7"  // light gray
  };
  private static final String[] demographics = {
      "Men",
      "Women",
      "Kids"
  };
  private static final String[] categories = {
      "Golf",
      "Soccer",
      "Basketball",
      "Hockey",
      "Football",
      "Running",
      "Baseball",
      "Skateboarding",
      "Boxing",
      "Weightlifting"
  };
  private static final String[] adjectives = {
      "Lightweight",
      "Slim",
      "Shock Absorbing",
      "Exotic",
      "Elastic",
      "Fashionable",
      "Trendy",
      "Next Gen",
      "Colorful",
      "Comfortable",
      "Water Resistant",
      "Wicking",
      "Heavy Duty"
  };
  private static final String[] types = {
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
      "Pool Noodle"
  };

  private static final Random randomGenerator = new Random();
  /**
   * Returns a random brand from the list of brands.
   *
   * @return - a brand string
   */
  public static String getBrand(){
    return brands[randomGenerator.nextInt(brands.length)];
  }

  /**
   * Returns a random material from the list of materials.
   *
   * @return - a material string
   */
  public static String getMaterial(){
    return materials[randomGenerator.nextInt(materials.length)];
  }

  /**
   * Returns a random double between minimum and maximum parameters to two decimal places.
   * @param min - a double minimum value
   * @param max - a double maximum value
   * @return - a double between minimum and maximum values as the price to two decimal places.
   */
  public static Double getPrice(double min, double max){
    DecimalFormat df = new DecimalFormat("0.00");
    return Double.valueOf(df.format((randomGenerator.nextDouble() * (max-min)) + min));
  }

  /**
   * Returns a random integer below a maximum value
   *
   * @param max - a maximum value integer
   * @return - an integer representing quantity
   */
  public static Integer getQuantity(int max){
    return randomGenerator.nextInt(max);
  }
  /**
   * Returns a random demographic from the list of demographics.
   *
   * @return - a demographic string
   */
  public static String getDemographic() {
    return demographics[randomGenerator.nextInt(demographics.length)];
  }

  /**
   * Returns a random category from the list of categories.
   *
   * @return - a category string
   */
  public static String getCategory(){
    return categories[randomGenerator.nextInt(categories.length)];
  }

  /**
   * Returns a random type from the list of types.
   *
   * @return - a type string
   */
  public static String getType(){
    return types[randomGenerator.nextInt(types.length)];
  }

  /**
   * Returns a random adjective from the list of adjectives.
   *
   * @return - an adjective string
   */
  public static String getAdjective(){
    return adjectives[randomGenerator.nextInt(adjectives.length)];
  }

  /**
   * Returns a random color code from the list of color codes.
   *
   * @return - a color code string
   */
  public static String getColorCode(){
    return colors[randomGenerator.nextInt(colors.length)];
  }

  /**
   * Generates a random product offering id.
   *
   * @return - a product offering id
   */
  public static String getRandomProductId() {
    return "po-" + RandomStringUtils.random(7, false, true);
  }

  /**
   * Generates a random style code.
   *
   * @return - a style code string
   */
  public static String getStyleCode() {
    return "sc" + RandomStringUtils.random(5, false, true);
  }

  /**
   * Finds a random date between two date bounds.
   *
   * @param startInclusive - the beginning bound
   * @param endExclusive   - the ending bound
   * @return - a random date as a LocalDate
   */
  private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = endExclusive.toEpochDay();
    long randomDay = ThreadLocalRandom
        .current()
        .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }

  /**
   * Generates a random boolean
   *
   * @return - a boolean
   */
  private static boolean isActive(){
    Random randomGenerator = new Random();
    return randomGenerator.nextBoolean();
  }


  /**
   * Generates a number of random products based on input.
   *
   * @param numberOfProducts - the number of random products to generate
   * @return - a list of random products
   */
  public List<Product> generateRandomProducts(Integer numberOfProducts) {

    List<Product> productList = new ArrayList<>();

    for (int i = 0; i < numberOfProducts; i++) {
      productList.add(createRandomProduct());
    }

    return productList;
  }

  /**
   * Uses random generators to build a product.
   *
   * @return - a randomly generated product
   */
  public Product createRandomProduct() {
    Product product = new Product();
//    Strings that need to be reused
    String demographic = ProductFactory.getDemographic();
    String category = ProductFactory.getCategory();
    String type = ProductFactory.getType();
    String space = " ";
    String comma = ", ";
//    Setters
    product.setBrand(ProductFactory.getBrand());
    product.setImageSrc("www.myimageurl.com");
    product.setPrice(ProductFactory.getPrice(1.0, 300.0));
    product.setQuantity(ProductFactory.getQuantity(100));
    product.setMaterial(ProductFactory.getMaterial());
    product.setDemographic(demographic);
    product.setCategory(category);
    product.setType(type);
    product.setDescription(category + comma + demographic + comma + ProductFactory.getAdjective());
    product.setName(ProductFactory.getAdjective() + space + category + space + type);
    product.setPrimaryColorCode(ProductFactory.getColorCode());
    product.setSecondaryColorCode(ProductFactory.getColorCode());
    product.setGlobalProductCode(ProductFactory.getRandomProductId());
    product.setStyleNumber(ProductFactory.getStyleCode());
    product.setReleaseDate(String.valueOf(
        ProductFactory.between(LocalDate.parse("2000-01-01"), LocalDate.now())));
    product.setActive(ProductFactory.isActive());

    return product;
  }

}
