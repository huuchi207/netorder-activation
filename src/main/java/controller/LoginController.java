package controller;

import app.App;
import app.Login;
import config.ConstantConfig;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import remote.BaseCallback;
import remote.ServiceBuilder;
import util.FieldViewUtils;
import util.Messenger;

public class LoginController {

  public AnchorPane apSecureQuestion;
  public AnchorPane apLogin;
  public ComboBox<String> cbQuestions;
  public TextField tfAnswer;
  public Button btOK;
  public Button btCancel;
  public Label lbSecureQuestion;

  @FXML
  private ProgressIndicator progressIndicator;

  @FXML
  private PasswordField pfPass;
  @FXML
  private Label lbErrorLogin;
  @FXML
  private TextField tfKey;
  private boolean isLoginPressed = false;


  @FXML
  void active(ActionEvent event) {
    if (FieldViewUtils.noEmpty(tfKey)){
      return;
    }
    if (ConstantConfig.FAKE){
      Messenger.info("Kích hoạt thành công!");
      Platform.exit();
      System.exit(0);
    } else {
      showProgressDialog();
      ServiceBuilder.getApiService().activeServer(tfKey.getText().trim()).enqueue(new BaseCallback<Object>() {
        @Override
        public void onError(String errorCode, String errorMessage) {
          hideProgressDialog();
          Messenger.erro(errorMessage);
        }

        @Override
        public void onSuccess(Object data) {
          hideProgressDialog();
          Messenger.info("Kích hoạt thành công!");
          Platform.exit();
          System.exit(0);
        }
      });
    }
  }

  private void startMain() {
    new App().start(new Stage());
    Login.getmStage().close();
  }

  @FXML
  void initialize() {

  }

  public void forgotPassword(ActionEvent event) {

  }

  public void ok(ActionEvent event) {

  }

  public void cancel(ActionEvent event) {

  }

  public void showProgressDialog() {
    progressIndicator.setVisible(true);
  }

  public void hideProgressDialog() {
    progressIndicator.setVisible(false);
  }
}
