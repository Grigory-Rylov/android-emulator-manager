package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import org.gradle.api.logging.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by grishberg on 15.11.17.
 */
public class HardwareManager {
    private static final String HARDWARE_FILE_NAME = "hardware-qemu.ini";
    private final Logger logger;
    private final String avdHomeDir;

    public HardwareManager(String avdHomeDir, Logger logger) {
        this.logger = logger;
        this.avdHomeDir = avdHomeDir;
    }

    public void writeHardwareFile(EmulatorConfig config) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(avdHomeDir + "/" + config.getName() + "/config.ini");

            // set the properties value
            prop.setProperty("hw.lcd.density", String.valueOf(config.getDisplayMode().getDensity()));
            prop.setProperty("hw.lcd.height", String.valueOf(config.getDisplayMode().getHeight()));
            prop.setProperty("hw.lcd.widt", String.valueOf(config.getDisplayMode().getWidth()));
            prop.setProperty("skin.name", String.format("%dx%d",
                    config.getDisplayMode().getWidth(),
                    config.getDisplayMode().getHeight()));

            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private HashMap<String, String> readConfigFile() {
        HashMap<String, String> params = new HashMap<>();
        try (InputStream inputStream = ){
            Properties prop = new Properties();
            String propFileName = "config.ini";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            String user = prop.getProperty("user");


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }
}
