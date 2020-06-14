package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Creates config initIfNeeded.
 */
public class HardwareManager {
    private PreferenceContext context;
    private final Logger logger;

    public HardwareManager(PreferenceContext context, Logger logger) {
        this.context = context;
        this.logger = logger;
    }

    void writeHardwareFile(EmulatorConfig config) {
        String configName = new File(context.getAvdHomeDir(),
                String.format(Locale.US, "%s.avd/config.ini", config.getName()))
                .getAbsolutePath();
        HashMap<String, String> defaultParams = readDefaultConfig(configName);

        Properties prop = new Properties();

        try (OutputStream output = new FileOutputStream(configName)) {

            for (Map.Entry<String, String> entry : defaultParams.entrySet()) {
                prop.setProperty(entry.getKey(), entry.getValue());
            }
            // set the properties value
            prop.setProperty("hw.lcd.density", String.valueOf(config.getDisplayMode().getDensity()));
            prop.setProperty("hw.lcd.height", String.valueOf(config.getDisplayMode().getHeight()));
            prop.setProperty("hw.lcd.width", String.valueOf(config.getDisplayMode().getWidth()));
            prop.setProperty("skin.name", String.format("%dx%d",
                    config.getDisplayMode().getWidth(),
                    config.getDisplayMode().getHeight()));
            if (config.getDiskSize() > 0) {
                prop.setProperty("disk.dataPartition.size", getDiskSizeAsString(config.getDiskSize()));
            }
            if (config.getSdCardSize() > 0) {
                prop.setProperty("sdcard.size", getDiskSizeAsString(config.getSdCardSize()));
            }

            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException e) {
            logger.error("Error while writing " + configName, e);
        }
    }

    private String getDiskSizeAsString(int sdCardSize) {
        if (sdCardSize > 1024 && sdCardSize % 1024 == 0) {
            return String.format(Locale.US, "%dG", sdCardSize / 1024);
        }
        return String.format(Locale.US, "%dM", sdCardSize);
    }

    private HashMap<String, String> readDefaultConfig(String fileName) {
        HashMap<String, String> params = new HashMap<>();
        try (InputStream inputStream = new FileInputStream(fileName)) {
            Properties prop = new Properties();
            prop.load(inputStream);

            HashSet<String> argNames = new HashSet<>();
            argNames.add("PlayStore.enabled");
            argNames.add("abi.type");
            argNames.add("avd.ini.encoding");
            argNames.add("hw.cpu.arch");
            argNames.add("image.sysdir.1");
            argNames.add("tag.display");
            argNames.add("tag.id");

            // get the property value and print it out
            for (String key : argNames) {
                params.put(key, prop.getProperty(key));
            }
        } catch (Exception e) {
            logger.error("Exception while reading config.ini:", e);
        }
        return params;
    }
}
