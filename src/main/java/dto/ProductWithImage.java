package dto;

import javafx.scene.image.ImageView;
import remote.ServiceBuilder;

public class ProductWithImage  extends  Product{
  private ImageView productImage;

  public ProductWithImage(String id, String productName, String description, Integer price, Integer inStock, String imageid) {
    super(id, productName, description, price, inStock);
    this.imageid = imageid;
    createImage();
  }

  public ProductWithImage(String productName, String productid) {
    super(productName, productid);
  }

  public ProductWithImage(String productName, String productid, ImageView productImage) {
    super(productName, productid);
    this.productImage = productImage;
  }

  public ImageView getProductImage() {
    return productImage;
  }

  public void setProductImage(ImageView productImage) {
    this.productImage = productImage;
  }

  public void createImage(){
    productImage = new ImageView(ServiceBuilder.getBASEURL()+"image?imageid="+ imageid);
    productImage.setFitHeight(150);
    productImage.setFitWidth(150);
  }

  public ProductInOrder convertToProductInOrder() {
    return new ProductInOrder(id, productName, description, price, inStock, imageid, productImage, 1);
  }

}
