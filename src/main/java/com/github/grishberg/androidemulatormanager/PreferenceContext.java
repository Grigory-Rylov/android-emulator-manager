package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig;
import com.github.grishberg.androidemulatormanager.utils.Logger;

import java.io.File;

/**
 * Provides path to android sdk components.
 */
public class PreferenceContext {
    private final Logger logger;
    private final EmulatorManagerConfig config;
    private String androidSdkPath;
    private String userHomePath;

    public PreferenceContext(EmulatorManagerConfig config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    /**
     * @return Absolute path to android sdk directory.
     */
    public File getAndroidSdkPath() {
        if (androidSdkPath == null) {
            androidSdkPath = getSdkPathFromConfigOrEnv();
        }
        return new File(androidSdkPath);
    }

    private String getSdkPathFromConfigOrEnv() {
        if (config.getAndroidSdkPath() == null) {
            String androidHome = System.getenv("ANDROID_HOME");
            logger.info("EmulatorManagerConfig.androidSdkPath is empty, get it from $ANDROID_HOME = {}",
                    androidHome);
            return androidHome;
        }
        return config.getAndroidSdkPath();
    }

    /**
     * @return Absolute path to users HOME directory.
     */
    public File getUserHomePath() {
        if (userHomePath == null) {
            userHomePath = getUserHomePathFromConfigOrEnv();
        }
        return new File(userHomePath);
    }

    private String getUserHomePathFromConfigOrEnv() {
        if (config.getUserHome() == null) {
            String home = System.getenv("HOME");
            logger.info("EmulatorManagerConfig.getUserHome is empty, get it from $HOME = {}", home);
            return home;
        }
        return config.getAndroidSdkPath();
    }

    public String getAvdHomeDir() {
        String homeDir = getUserHomePathFromConfigOrEnv();
        return homeDir + "/.android/avd";
    }

    public String[] getAdditionalEmulatorParameters() {
        return config.getAdditionalEmulatorParameters().trim().split(" ");
    }
}

