package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Criptografar text atraves de MD5
 */
public class CryptographyUtils {

    /**
     * Converter string em hash MD5 para criptografia da senha do usuario na base de dados
     */
    public static String converter(String original) {
        try {
            StringBuilder hexString = new StringBuilder();
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Messenger.erro(BundleUtils.getResourceBundle().getString("txt_error") + ex);
        }

        return "";
    }
}
