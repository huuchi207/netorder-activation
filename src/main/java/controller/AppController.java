package controller;

import config.ConstantConfig;
import config.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import util.Link;
import util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class AppController {
  private static AppController instance;

  @FXML
  private AnchorPane boxContainer;
  @FXML
  private VBox boxNotas;
  private ToggleButton currentScreen;
  @FXML
  private ProgressIndicator progressIndicator;

  private List<Node> userListBoxMenu = new ArrayList<>();
  private List<Node> userListFeatureView;
  public ToggleButton btCreateOrder;
  public VBox boxOrderSection;
  public static AppController getInstance() {
    return instance;
  }

  @FXML
  void logOut(ActionEvent event) {
    showProgressDialog();

  }

  @FXML
  void initialize() {
    instance = this;
    if (ConstantConfig.FAKE){
      userListBoxMenu.add(boxOrderSection);
      openCreateOrderScreen(new ActionEvent(btCreateOrder, null));
    }
  }

  /**
   * Obter componente para exbição das notas
   */
  public VBox boxNotas() {
    return boxNotas;
  }

  public void openCreateOrderScreen(ActionEvent actionEvent) {
    if (actionEvent.getSource() == currentScreen) {
      return;
    }
    NavigationUtils.getCreateOrderScreen(boxContainer);
    setCurrentSubMenuAndStyleThenHideProgressIndicator((ToggleButton) actionEvent.getSource());
  }

  public void openRevenueStatistic(ActionEvent actionEvent) {
    if (actionEvent.getSource() == currentScreen) {
      return;
    }
    NavigationUtils.getStatisticScreen(boxContainer);
    setCurrentSubMenuAndStyleThenHideProgressIndicator((ToggleButton) actionEvent.getSource());
  }

  public void setCurrentSubMenuAndStyleThenHideProgressIndicator(ToggleButton currentSelectedSubMenu) {
    currentScreen = currentSelectedSubMenu;
    for (Node boxMenu : userListBoxMenu) {
      if (boxMenu instanceof VBox) {
        List<Node> children = ((VBox) boxMenu).getChildren();
        if (children != null && !children.isEmpty()) {
          for (Node child : children) {
            if (child instanceof ToggleButton) {
              if (child != currentSelectedSubMenu) {
                ((ToggleButton) child).setSelected(false);
              } else {
                ((ToggleButton) child).setSelected(true);
              }
            }
          }
        }
      }
    }
    hideProgressDialog();
  }

  public void showProgressDialog() {
    progressIndicator.setVisible(true);
  }

  public void hideProgressDialog() {
    progressIndicator.setVisible(false);
  }

  public void goSite(ActionEvent actionEvent) {
    //TODO: change link
    Link.address(Constants.WEB_LINK);
  }

}
