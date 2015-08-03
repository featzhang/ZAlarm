package cn.zhangzuofeng.config;


import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

public class ZConfigManager {

    private static final String ENABLE = "enable";
    private static final String DISABLE = "disable";

    public static Object loadProperties(Class<?> aClass, String configFileFullPath) {
        if (aClass == null) {
            System.err.print("aClass == null");
            return null;
        }
        File propertiesFile = doCheckFile(configFileFullPath);

        if (propertiesFile == null) {
            System.err.println("file "+configFileFullPath+" not exist!");
            return null;
        }
        Properties localProperties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(
                    propertiesFile);
            localProperties.load(fileInputStream);
        } catch (Exception e) {
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Object instance = null;
        try {
//            Class<?> aClass = v.getClass();
            instance = aClass.newInstance();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                Class<?> type = field.getType();
                String property = localProperties.getProperty(name);
                field.setAccessible(true);
                if (property == null) {
                    continue;
                } else if (("").equals(property.trim())) {
                    if (type == String.class) {
                        field.set(instance, property);
                    } else {
                        continue;
                    }
                }
                if (type == Boolean.class) {
                    if (property.equals(ENABLE)) {
                        field.set(instance, new Boolean(true));
                    } else {
                        field.set(instance, new Boolean(false));
                    }
                } else if (type == Integer.class) {
                    field.set(instance, new Integer(property));
                } else if (type == Double.class) {
                    field.set(instance, new Double(property));
                } else if (type == String.class) {
                    field.set(instance, property);
                }

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static File doCheckFile(String configFileFullPath) {
        if (configFileFullPath == null || configFileFullPath.trim().length() == 0) {
            System.err.print("\"configFileFullPath\" is empty!");
        }
        File file = new File(configFileFullPath);
        checkFileExist(file.getParentFile());
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    private static boolean checkFileExist(File file) {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            checkFileExist(parentFile);
            file.mkdir();
        }
        return true;
    }


    public static void saveProperties(Object v, String configFileFullPath, String fileHead) {
        if (v == null) {
            System.err.println("v == null");
            return;
        }
        Class<?> aClass = v.getClass();
        Field[] fields = aClass.getDeclaredFields();

        Properties localProperties = new Properties();

        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                field.setAccessible(true);
                Object o = field.get(v);
                if (o == null) {
                    localProperties.setProperty(fieldName, "");
                } else {
                    Class<?> type = field.getType();
                    if (type == Boolean.class) {
                        if (o.equals(Boolean.TRUE)) {
                            localProperties.setProperty(fieldName, ENABLE);
                        } else {
                            localProperties.setProperty(fieldName, DISABLE);
                        }
                    } else {
                        localProperties.setProperty(fieldName, o + "");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        File propertiesFile = doCheckFile(configFileFullPath);
        if (propertiesFile == null) {
            try {
                System.err.println("propertiesFile "+configFileFullPath+" not exist! create it!");
                propertiesFile = new File(configFileFullPath);
                propertiesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(
                    propertiesFile);
            localProperties.store(fileOutputStream, fileHead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getConfigFilePath(String applicationName, String configFileName) {
        String property = System.getProperty("user.home");
        property += File.separator + "." + applicationName;
        property += File.separator + configFileName;
        return property;
    }

    public static void main(String[] args) {
        String systemConfigPath = getConfigFilePath("ZAlarm", "timer.zsc");
        System.out.println(systemConfigPath);
    }
}