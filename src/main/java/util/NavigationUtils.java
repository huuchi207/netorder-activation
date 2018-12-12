package util;


import controller.CreateOrderScreenControler;
import controller.RevenueStatisticController;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Main class for controlling and loading the application modules,
 * each menu and some submenus of the application represents a module in the application
 */
public class NavigationUtils {
  private static CreateOrderScreenControler createOrderScreenControler;
  private static RevenueStatisticController revenueStatisticController;

  private NavigationUtils() {
  }

  /**
   * Content screen setup for panel resetField and
   * add new screen, resized its size to fill the screen
   */
  public static void config(AnchorPane box, AnchorPane conteudo) {
    box.getChildren().clear();
    conteudo.setPrefSize(box.getWidth(), box.getHeight());
    box.getChildren().add(conteudo);
    ResizeUtils.margin(conteudo, 0);
  }

  /**
   * Assist in the visualization of screen elements like: subenus, subtelas and etc ...
   */
  public static void setVisibility(boolean valor, Node... no) {
    for (Node element : no) {
      element.setVisible(valor);
    }
  }

  public static void getCreateOrderScreen(AnchorPane box) {
    createOrderScreenControler = new CreateOrderScreenControler();
    config(box, createOrderScreenControler);
  }

  public static void getStatisticScreen(AnchorPane box) {
    revenueStatisticController = new RevenueStatisticController();
    config(box, revenueStatisticController);
  }

  public static void config(AnchorPane box, StackPane conteudo) {
    box.getChildren().clear();
    box.getChildren().add(conteudo);
    ResizeUtils.margin(conteudo, 0);
  }
}
