package controller;

import config.ConstantConfig;
import customview.AutocompletionlTextField;
import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class CreateOrderScreenControler extends AnchorPane {
  //header
  public Label lbTitle;
  public TextField txtSearch;
  //product list
  public AnchorPane apProductList;
  public TableView<ProductWithImage> tbProduct;
  //    public TableColumn colId;
  public TableColumn colProductName;
  public TableColumn colDescription;
  public TableColumn colPrice;
  public TableColumn colImage;
  //order
  public AnchorPane apEditOrderList;
  public TableView<ProductInOrder> tbProductInOrder;
  //    public TableColumn colIdInOrder;
  public TableColumn colProductNameInOrder;
  public TableColumn colDescriptionInOrder;
  public TableColumn colPriceInOrder;
  public TableColumn colImageInOrder;
  public TableColumn<ProductInOrder, String> colCountInOrder;
  public TableColumn colStatus;
  public TableColumn<ProductInOrder, String> colMoreRequirement;
  public GridPane gridEditOrderList;
  public AutocompletionlTextField<DeviceInfo> txtChooseDevice;
  //bottom button
  public Button btEditOrder;
  public Button btCreateOrder;
  public Button btBackToList;
  public Label lbLegend;

  private ResourceBundle bundle;

  private List<ProductWithImage> productList;
  private List<ProductInOrder> productInOrders;

  private ObservableList<ProductWithImage> productObservableList;
  private ObservableList<ProductInOrder> productInOrderObservableList;
  private int totalPrice;
  private ObservableList<DeviceInfo> deviceInfoObservableList = FXCollections.observableList(new ArrayList<>());

  public CreateOrderScreenControler() {
    try {
      FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/create_order_screen.fxml"));

      fxml.setRoot(this);
      fxml.setController(this);
      bundle = BundleUtils.getResourceBundle();
      fxml.setResources(bundle);
      fxml.load();

    } catch (Exception ex) {
      Messenger.erro(bundle.getString("txt_loading_screen_error") + ex);
    }
  }


  @FXML
  public void initialize() {
    initTable();
    goToProductList(null);

    txtSearch.textProperty().addListener((obs, old, novo) -> {
      filter(novo, FXCollections.observableArrayList(productList));
    });
  }

  private void initTable() {
    tbProduct.getSelectionModel().setSelectionMode(
      SelectionMode.MULTIPLE
    );
//        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
    colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    colImage.setCellValueFactory(new PropertyValueFactory<>("productImage"));


//        colIdInOrder.setCellValueFactory(new PropertyValueFactory<>("id"));
    colProductNameInOrder.setCellValueFactory(new PropertyValueFactory<>("productName"));
    colDescriptionInOrder.setCellValueFactory(new PropertyValueFactory<>("description"));
    colPriceInOrder.setCellValueFactory(new PropertyValueFactory<>("price"));
    colCountInOrder.setCellValueFactory(new PropertyValueFactory<>("tfNumber"));
    colMoreRequirement.setCellValueFactory(new PropertyValueFactory<>("tfNote"));
    colImageInOrder.setCellValueFactory(new PropertyValueFactory<>("productImage"));

  }

  /**
   * Mapear dados objetos para inserção dos dados na updateTable
   */
  private void updateProductTable() {
    productObservableList = FXCollections.observableArrayList(productList);
    tbProduct.setItems(productObservableList);
  }

  private void updateProductInOrderTable() {
    productInOrderObservableList = FXCollections.observableArrayList(productInOrders);

    tbProductInOrder.setItems(productInOrderObservableList);
  }

  /**
   * FieldViewUtils de pesquisar para filtrar dados na updateTable
   */
  private void filter(String valor, ObservableList<ProductWithImage> products) {

    FilteredList<ProductWithImage> filteredList = new FilteredList<>(products, Product -> true);
    filteredList.setPredicate(product -> {

      if (valor == null || valor.isEmpty()) {
        return true;
      } else if (product.getProductName().toLowerCase().contains(valor.toLowerCase())) {
        return true;
      }

      return false;
    });

    SortedList<ProductWithImage> dadosOrdenados = new SortedList<>(filteredList);
    dadosOrdenados.comparatorProperty().bind(tbProduct.comparatorProperty());
//    FilterUtils.mensage(legenda, dadosOrdenados.size(), "Quantidade de Estratigrafias encontradas");

    tbProduct.setItems(dadosOrdenados);
  }

  private void getProductList() {
    if (ConstantConfig.FAKE) {
      updateProductList(FakeDataUtils.getFakeProductList());
      updateProductTable();
    } else {

    }
  }

  @FXML
  void editOrder(ActionEvent event) {
    productInOrders = createProductInOrderListFromSelectedProductList();
    if (productInOrders.isEmpty()) {
      Messenger.info(bundle.getString("txt_please_choose_product"));
      return;
    } else {
      for (ProductInOrder productInOrder : productInOrders) {
        if (productInOrder.getInStock() == null || productInOrder.getInStock() == 0) {
          Messenger.erro("Sản phẩm " + productInOrder.getProductName() + " hiện đang hết hàng. Vui lòng chọn sản phẩm khác!");
          return;
        }
      }
    }
    updateProductInOrderTable();
    updateProductInOrderScreenData();
    getDeviceList();
    FieldViewUtils.resetField(txtChooseDevice);
    lbTitle.setText(bundle.getString("txt_edit_order"));
    NavigationUtils.setVisibility(false, btEditOrder, apProductList, txtSearch);
    NavigationUtils.setVisibility(true, btBackToList, btCreateOrder, gridEditOrderList);

  }

  @FXML
  void createOrder(ActionEvent event) {
    if (totalPrice <= 0) {
      Messenger.info(bundle.getString("txt_please_choose_number_of_product_correctly"));
      return;
    }
    if (txtChooseDevice.getSelectedItem() == null) {
      Messenger.info("Vui lòng chọn máy!");
      return;
    }
    if (ConstantConfig.FAKE) {
      Messenger.info(bundle.getString("msg_create_order_successfully") + "\"" + bundle.getString("txt_order_created") + "\"");
    } else {

    }
  }

  private void putOrder(OrderDetail orderDetail) {

  }

  @FXML
  void goToProductList(ActionEvent event) {
    getProductList();
    lbTitle.setText(bundle.getString("txt_product_list"));
    lbLegend.setText(bundle.getString("txt_hold_ctrl_to_choose_items"));
    tbProduct.getSelectionModel().clearSelection();

    NavigationUtils.setVisibility(true, btEditOrder, apProductList, txtSearch);
    NavigationUtils.setVisibility(false, btBackToList, btCreateOrder, gridEditOrderList);
  }

  private List<ProductInOrder> createProductInOrderListFromSelectedProductList() {
    return new ArrayList<>();
  }

  private void updateProductInOrderScreenData() {
    //reset total for counting
    totalPrice = 0;
    ObservableList<ProductInOrder> orderObservableList = tbProductInOrder.getItems();

    ListIterator<ProductInOrder> iterator = orderObservableList.listIterator();
    while (iterator.hasNext()) {
      ProductInOrder item = iterator.next();
      totalPrice += item.getPrice() * item.getCount();
    }
    //update list
//        iterator.forEachRemaining(productInOrders::add);
    //update text
    lbLegend.setText(bundle.getString("txt_total_price") + ": " + totalPrice + " " + bundle.getString("txt_vnd"));
  }

  private void updateProductList(List<Product> products) {
    List<ProductWithImage> list = new ArrayList<>();
    for (Product product : products) {
      ProductWithImage productWithImage = product.convertToProductWithImage();
      productWithImage.updateStatus();
      list.add(productWithImage);
    }
    productList = list;
  }



  private void getDeviceList() {
    if (ConstantConfig.FAKE){
//      txtChooseDevice.getEntries().clear();
//      txtChooseDevice.getEntries().addAll(data);
      return;
    }
  }
}
