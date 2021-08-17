package com.nimai.email.emailproperties;


import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import java.util.ResourceBundle;

public class PropertyHandler {

    static PropertyHandler propertyHandler = null;
    ResourceBundle rb = ResourceBundle.getBundle("com.nimai.email.emailproperties.bundle");

    private Properties prop = null;

    public PropertyHandler(String path) {
        this.loaddata(path);

    }

    public PropertyHandler() {

    }

    private void loaddata(String path) {

        try {

            Properties p = new Properties();
            String baseDirPath = rb.getString("dcbsp.master.properties.location");
            String masterPropertyFile = "master.properties";
            File file = new File(baseDirPath, masterPropertyFile);
            InputStream in = new FileInputStream(file);
            p.load(in);

            Enumeration en = p.elements();
            prop = new Properties();
            while (en.hasMoreElements()) {
                String filename = en.nextElement().toString();
                File tempFile = new File(baseDirPath, filename);
                InputStream tempInFile = new FileInputStream(tempFile);
                prop.load(tempInFile);
                tempInFile.close();
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            }

    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}



