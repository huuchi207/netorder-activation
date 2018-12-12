package dto;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;

public class ProductInOrder extends ProductWithImage {
  private int count;
  private String countString;
  private String moreRequirement;
  private TextField tfNumber;
  private TextField tfNote;
  private OnContentChange onContentChange;

  public ProductInOrder(String id, String productName, String description, Integer price, Integer inStock, String imageid,
                        ImageView imageView, int count) {
    super(id, productName, description, price, inStock, imageid);
    this.setProductImage(imageView);

    this.count = count;
    this.countString = String.valueOf(count);

    tfNumber = new TextField();
    tfNumber.setMaxSize(40, 30);
    tfNote = new TextField();

    tfNote.setText("");
    tfNumber.setText("1");
    tfNumber.textProperty().addListener((observable, oldValue, newValue) -> {
      int newNumber;
      if (StringUtils.isEmpty(newValue)){
        newValue = "0";
      }
      try {
        newNumber = Integer.valueOf(newValue);
        if (onContentChange!= null)
          onContentChange.onNumberChange(null, newNumber);
      } catch (NumberFormatException e) {
//        Messenger.erro("Yêu cầu là số!");
        tfNumber.setText(oldValue);
      }

    });
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
    this.countString = String.valueOf(count);
  }

  public String getCountString() {
    return countString;
  }

  public void setCountString(String countString) {
    this.countString = countString;
  }

  public String getMoreRequirement() {
    return moreRequirement;
  }

  public void setMoreRequirement(String moreRequirement) {
    this.moreRequirement = moreRequirement;
  }


  public TextField getTfNumber() {
    return tfNumber;
  }

  public void setTfNumber(TextField tfNumber) {
    this.tfNumber = tfNumber;
  }

  public TextField getTfNote() {
    return tfNote;
  }

  public void setTfNote(TextField tfNote) {
    this.tfNote = tfNote;
  }

  public OnContentChange getOnContentChange() {
    return onContentChange;
  }

  public ProductInOrder setOnContentChange(OnContentChange onContentChange) {
    this.onContentChange = onContentChange;
    return this;
  }

  public interface OnContentChange {
    void onNumberChange(Integer oldNumber, Integer newNumber);
  }
}
