package util;

import config.ConstantConfig;

import java.util.Locale;
import java.util.ResourceBundle;

public class BundleUtils {
  public static ResourceBundle getResourceBundle(){
    Locale locale= null;
    try {
      locale = new Locale(ConstantConfig.defaultLocaleCode);
    }catch (Exception e){

    }
    if (locale != null) {
      return ResourceBundle
          .getBundle("LangBundle", locale);
    }
    return ResourceBundle
        .getBundle("LangBundle");
  }
}
