package utils;

import java.util.ResourceBundle;

public class ConfigUtils {
    private static ResourceBundle bundle;
    static {
        bundle=ResourceBundle.getBundle("config");
    }
    public static String get(String str){
        return bundle.getString(str);
    }
}
