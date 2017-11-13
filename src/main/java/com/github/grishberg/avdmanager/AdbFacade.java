package com.github.grishberg.avdmanager;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import org.gradle.api.logging.Logger;

/**
 * Created by grishberg on 12.11.17.
 */
public class AdbFacade {
    private final Logger logger;
    private AndroidDebugBridge adb;

    public AdbFacade(Logger logger) {
        this.logger = logger;
        init();
    }

    private void init() {
        AndroidDebugBridge.initIfNeeded(false);
        adb = AndroidDebugBridge.createBridge();
        waitForAdb();
    }

    public IDevice[] getDevices() {
        return adb.getDevices();
    }

    public void terminate() {

    }

    private void waitForAdb() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error("Error while wailing", e);
        }
    }
}
