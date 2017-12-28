package com.github.grishberg.androidemulatormanager;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import org.gradle.api.logging.Logger;

/**
 * Facade for adb.
 */
public class AdbFacade {
    private final PreferenceContext preferenceContext;
    private final Logger logger;
    private AndroidDebugBridge adb;

    public AdbFacade(PreferenceContext preferenceContext, Logger logger) {
        this.preferenceContext = preferenceContext;
        this.logger = logger;
    }

    public void init() throws InterruptedException {
        AndroidDebugBridge.initIfNeeded(false);
        adb = AndroidDebugBridge.createBridge(preferenceContext.getAndroidSdkPath() + "/platform-tools/adb", false);
        waitForAdb();
        logger.info("adb initiated");
    }

    public IDevice[] getDevices() {
        return adb.getDevices();
    }

    public void terminate() {
        // at the moment not needed
        logger.info("terminating");
    }

    private void waitForAdb() throws InterruptedException {
        Thread.sleep(1000);
    }
}
