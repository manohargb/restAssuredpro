package com.mobi.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    private final Properties props = new Properties();

    private static PropertiesLoader instance = null;

    private PropertiesLoader() {
        if (System.getProperty("env") == null) {
            LOGGER.info("Loading properties from default");
            loadPropertiesFromFile("qa");
        }
        else {
            LOGGER.info("Loading properties from " + System.getProperty("env"));
            loadPropertiesFromFile(System.getProperty("env"));
        }
    }

    private static synchronized PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    private void loadPropertiesFromFile(String sEnv) {
        try {
            String fileName = sEnv + ".properties";
            InputStream propsStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName);
            props.load(propsStream);
        } catch (Exception e) {
            throw new IllegalStateException("Could not load properties file");
        }
    }

    public static String readProperty(String key) {
        return getInstance().props.getProperty(key);
    }
}

