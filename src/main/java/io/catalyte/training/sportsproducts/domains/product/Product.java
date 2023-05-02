package io.catalyte.training.sportsproducts.domains.product;

import io.catalyte.training.sportsproducts.domains.review.Review;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * This class is a representation of a sports apparel product.
 */
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String brand;

  private String imageSrc;

  private String material;

  private Double price;

  private Integer quantity;

  private String name;

  private String description;

  private String demographic;

  private String category;

  private String type;

  private String releaseDate;

  private String primaryColorCode;

  private String secondaryColorCode;

  private String styleNumber;

  private String globalProductCode;

  private Boolean active;

  @OneToMany(mappedBy = "product")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Review> reviews;

  public Product() {
  }

  public Product(String name, String description,
      String demographic, String category,
      String type, String releaseDate,
      String brand,String imageSrc, String material,
      Integer quantity, Double price, Boolean active, String globalProductCode,
      String styleNumber, String secondaryColorCode, String primaryColorCode, Set<Review> reviews) {
    this.name = name;
    this.description = description;
    this.demographic = demographic;
    this.category = category;
    this.type = type;
    this.releaseDate = releaseDate;
    this.brand = brand;
    this.imageSrc = imageSrc;
    this.material = material;
    this.quantity = quantity;
    this.price = price;
    this.active = active;
    this.globalProductCode = globalProductCode;
    this.styleNumber = styleNumber;
    this.secondaryColorCode = secondaryColorCode;
    this.primaryColorCode = primaryColorCode;
    this.reviews = reviews;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDemographic() {
    return demographic;
  }

  public void setDemographic(String demographic) {
    this.demographic = demographic;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getPrimaryColorCode() {
    return primaryColorCode;
  }

  public void setPrimaryColorCode(String primaryColorCode) {
    this.primaryColorCode = primaryColorCode;
  }

  public String getSecondaryColorCode() {
    return secondaryColorCode;
  }

  public void setSecondaryColorCode(String secondaryColorCode) {
    this.secondaryColorCode = secondaryColorCode;
  }

  public String getStyleNumber() {
    return styleNumber;
  }

  public void setStyleNumber(String styleNumber) {
    this.styleNumber = styleNumber;
  }

  public String getGlobalProductCode() {
    return globalProductCode;
  }

  public void setGlobalProductCode(String globalProductCode) {
    this.globalProductCode = globalProductCode;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getBrand() {
    return brand; 
  }

  public void setBrand(String brand) {
    this.brand = brand; 
  }

  public String getImageSrc() {
    return imageSrc;
  }
  public void setImageSrc(String imageSrc){ 
    this.imageSrc = imageSrc; 
  }

  public Integer getQuantity() {
    return quantity; 
  }
  public void setQuantity(Integer quantity){ 
    this.quantity = quantity; 
  }

  public String getMaterial() { 
    return  material; 
  }
  public void setMaterial(String material) { 
    this.material = material; 
  }

  public Double getPrice() {
    return price; 
  }
  public void setPrice(Double price) { 
    this.price = price;
  }

  public Set<Review> getReviews() {
    return reviews;
  }

  public void setReviews(Set<Review> reviews) {
    this.reviews = reviews;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Product product = (Product) o;

    if (id != null ? !id.equals(product.id)
            : product.id != null) {
      return false;
    }
    if (brand != null ? !brand.equals(product.brand)
            : product.brand != null) {
      return false;
    }
    if (imageSrc != null ? !imageSrc.equals(product.imageSrc)
            : product.imageSrc != null) {
      return false;
    }
    if (material != null ? !material.equals(product.material)
            : product.material != null) {
      return false;
    }
    if (price != null ? !price.equals(product.price)
            : product.price != null) {
      return false;
    }
    if (quantity != null ? !quantity.equals(product.quantity)
            : product.quantity != null) {
      return false;
    }
    if (name != null ? !name.equals(product.name) : product.name != null) {
      return false;
    }
    if (description != null ? !description.equals(product.description)
        : product.description != null) {
      return false;
    }
    if (demographic != null ? !demographic.equals(product.demographic)
        : product.demographic != null) {
      return false;
    }
    if (category != null ? !category.equals(product.category) : product.category != null) {
      return false;
    }
    if (type != null ? !type.equals(product.type) : product.type != null) {
      return false;
    }
    if (releaseDate != null ? !releaseDate.equals(product.releaseDate)
        : product.releaseDate != null) {
      return false;
    }
    if (primaryColorCode != null ? !primaryColorCode.equals(product.primaryColorCode)
        : product.primaryColorCode != null) {
      return false;
    }
    if (secondaryColorCode != null ? !secondaryColorCode.equals(product.secondaryColorCode)
        : product.secondaryColorCode != null) {
      return false;
    }
    if (styleNumber != null ? !styleNumber.equals(product.styleNumber)
        : product.styleNumber != null) {
      return false;
    }
    if (globalProductCode != null ? !globalProductCode.equals(product.globalProductCode)
        : product.globalProductCode != null) {
      return false;
    }
    return active != null ? active.equals(product.active) : product.active == null;
  }


  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (demographic != null ? demographic.hashCode() : 0);
    result = 31 * result + (category != null ? category.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
    result = 31 * result + (primaryColorCode != null ? primaryColorCode.hashCode() : 0);
    result = 31 * result + (secondaryColorCode != null ? secondaryColorCode.hashCode() : 0);
    result = 31 * result + (styleNumber != null ? styleNumber.hashCode() : 0);
    result = 31 * result + (globalProductCode != null ? globalProductCode.hashCode() : 0);
    result = 31 * result + (active != null ? active.hashCode() : 0);
    result = 31 * result + (brand != null ? brand.hashCode() : 0);
    result = 31 * result + (imageSrc != null ? imageSrc.hashCode() : 0);
    result = 31 * result + (material  != null ? material.hashCode() : 0);
    result = 31 * result + (price  != null ? price.hashCode() : 0);
    result = 31 * result + (quantity  != null ? quantity.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return
        "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", demographic='" + demographic + '\'' +
        ", category='" + category + '\'' +
        ", type='" + type + '\'' +
        ", releaseDate='" + releaseDate + '\'' +
        ", primaryColorCode='" + primaryColorCode + '\'' +
        ", secondaryColorCode='" + secondaryColorCode + '\'' +
        ", styleNumber='" + styleNumber + '\'' +
        ", globalProductCode='" + globalProductCode + '\'' +
        ", active='" + active + '\'' +
        ", brand='" + brand + '\'' +
        ", imageSrc='" + imageSrc + '\'' +
        ", material'" + material + '\'' +
        ", price'" + price + '\'' +
      ", quantity'" + quantity + '\'' +
            ", reviews'" + reviews + '\''+
        '}';
  }
}
