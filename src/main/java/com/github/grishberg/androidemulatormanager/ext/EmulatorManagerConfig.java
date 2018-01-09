package com.github.grishberg.androidemulatormanager.ext;

import com.github.grishberg.androidemulatormanager.EmulatorConfig;

/**
 * Extension object for configuring Android emulator manager.
 */
public class EmulatorManagerConfig {
    EmulatorConfig[] emulatorArgs;
    long waitingTimeout = 60 * 1000;
    String androidSdkPath;
    String userHome;

    public EmulatorConfig[] getEmulatorArgs() {
        return emulatorArgs;
    }

    public void setEmulatorArgs(EmulatorConfig[] emulatorArgs) {
        this.emulatorArgs = emulatorArgs;
    }

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
}
