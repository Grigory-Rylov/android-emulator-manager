package com.github.grishberg.androidemulatormanager.ext;

/**
 * Extension object for configuring Android emulator manager.
 */
public class EmulatorManagerConfig {
    long waitingTimeout = 60 * 1000;
    String androidSdkPath;
    String userHome;
    String additionalEmulatorParameters = "";
    boolean installSystemImageIfNotExists;

    public long getWaitingTimeout() {
        return waitingTimeout;
    }

    public void setWaitingTimeout(long waitingTimeout) {
        this.waitingTimeout = waitingTimeout;
    }

    public String getAndroidSdkPath() {
        return androidSdkPath;
    }

    public void setAndroidSdkPath(String androidSdkPath) {
        this.androidSdkPath = androidSdkPath;
    }

    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    public boolean isInstallSystemImageIfNotExists() {
        return installSystemImageIfNotExists;
    }

    public void setInstallSystemImageIfNotExists(boolean installSystemImageIfNotExists) {
        this.installSystemImageIfNotExists = installSystemImageIfNotExists;
    }

    public String getAdditionalEmulatorParameters() {
        return additionalEmulatorParameters;
    }

    public void setAdditionalEmulatorParameters(String additionalEmulatorParameters) {
        this.additionalEmulatorParameters = additionalEmulatorParameters;
    }
}
