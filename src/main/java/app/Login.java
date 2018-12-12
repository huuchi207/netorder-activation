package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.BundleUtils;
import util.Messenger;

/**
 * Inicialização tela de login
 *
 * @author angelicalleite
 */
public class Login extends Application {

  public static Stage mStage;
  private Scene mScene;
  private AnchorPane rootPane;

  private Screen screen = Screen.getPrimary();
  private Rectangle2D windows = screen.getVisualBounds();

  @Override
  public void start(final Stage stage) {
    try {
      mStage = stage;

      FXMLLoader fxml = new FXMLLoader(App.class.getResource("/fxml/login.fxml"));
      fxml.setResources(BundleUtils.getResourceBundle());
      rootPane = fxml.load();

      mScene = new Scene(rootPane);

//            stage.setX(windows.getMinX());
//            stage.setY(windows.getMinY());
//            stage.setWidth(619);
//            stage.setHeight(445);
      stage.resizableProperty().setValue(Boolean.FALSE);
      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
          Platform.exit();
          System.exit(0);
        }
      });
//            stage.setMinWidth(windows.getWidth()-200);
//            stage.setMinHeight(windows.getHeight()-100);
      stage.centerOnScreen();


      stage.getIcons().addAll(new Image(Login.class.getResourceAsStream("/icon.png")));

      stage.setScene(mScene);
//            ResizeHelper.addResizeListener/(stage);
      stage.show();

    } catch (Exception ex) {
      Messenger.erro(BundleUtils.getResourceBundle().getString("txt_error") + ex);
    }
  }

  public static void main(String[] args) {
    Application.launch(Login.class, (String[]) null);
  }

  public static Stage getmStage() {
    return mStage;
  }

}
