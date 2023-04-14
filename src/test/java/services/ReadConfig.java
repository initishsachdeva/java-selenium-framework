package services;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    Properties properties;

    public ReadConfig() {
        File src = new File("./config.properties");
        try {
            FileInputStream fis = new FileInputStream(src);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            System.out.println("Exception in getting properties from config file -> " + e.getMessage());
        }
    }

    public String getApplicationURL() {
        String url = properties.getProperty("baseURL");
        return url;
    }

    public String getUsername() {
        String username = properties.getProperty("username");
        return username;
    }

    public String getPassword() {
        String password = properties.getProperty("password");
        return password;
    }
}
