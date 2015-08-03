package cn.zhangzuofeng.ZAlarm.util;

import javax.swing.*;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ZResource {
    private static ResourceBundle resources;
    private static final String imageSuffix = ".image";
    private static final String textSuffix = ".label";

    static {
        try {
            resources = ResourceBundle.getBundle("cn.zhangzuofeng.ZAlarm.util.resources.ZAlarm",
                    Locale.getDefault());
        } catch (MissingResourceException mre) {
            System.err.println("cn/zhangzuofeng/util/resources/ZAlarm.properties not found");
            System.exit(1);
        }
    }

    public static String getResourceString(String nm) {
        String str;
        try {
            str = resources.getString(nm);
        } catch (MissingResourceException mre) {
            str = null;
        }
        return str;
    }

    public URL getResources(String key) {
        String name = getResourceString(key);
        if (name != null) {
            return this.getClass().getResource(name);
        }
        return null;
    }

    public static ImageIcon getImageIcon(String cmd) {
        URL url = new ZResource().getResources(cmd + imageSuffix);
        if (url != null) {
            return new ImageIcon(url);
        }
        System.err.println("image label \"" + cmd + "\" not exist! ");
        return null;
    }

    public static java.awt.Image getImage(String cmd) {
        return getImageIcon(cmd).getImage();
    }

    public static String getAppTitle() {
        return getResourceString("Application.title");
    }

    public static String getAppName() {
        return getResourceString("Application.name");
    }

    public static String getAppVersion() {
        return getResourceString("Application.version");
    }

    public static ImageIcon getAppImageIcon() {
        return getImageIcon("Application");
    }

    public static java.awt.Image getAppImage() {
        return getAppImageIcon().getImage();
    }

    public static ImageIcon getWelcomeImage() {
        return getImageIcon("welcome");
    }

    public static String getAppHomePage() {
        return getResourceString("Application.homepage");
    }

    public static String getLabel(String... s) {
        String result = "";
        for (String s1 : s) {
            String str;
            try {
                str = resources.getString(s1 + textSuffix);
            } catch (MissingResourceException mre) {
                str = null;
            }
            if (str == null) {
                System.out.println("label \"" + s1 + "\" not exist!");
            }
            result += str;
        }

        return result;
    }

}
