package util;


import static config.ConstantConfig.APP_DATA_FOLDER_NAME;
import static config.ConstantConfig.APP_NAME;

public class StaticVarUtils {
  private static String macAddress = NetworkUtils.getAddress("mac");
  private static String token;
  private static String APP_DATA_PATH = System.getProperty("user.home") + "\\" + APP_NAME + "\\" + APP_DATA_FOLDER_NAME;


  public static String getToken() {
    return token;
  }

  public static void setToken(String token) {
    StaticVarUtils.token = token;
  }

  public static String getAppDataPath() {
    return APP_DATA_PATH;
  }

  public static void setAppDataPath(String appDataPath) {
    APP_DATA_PATH = appDataPath;
  }
}
