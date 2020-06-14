package com.github.grishberg.androidemulatormanager;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.github.grishberg.androidemulatormanager.utils.Logger;

import java.io.File;

/**
 * Facade for adb.
 */
public class AdbFacade {
    private static final int ADB_TIMEOUT = 10;
    private static final int ONE_SECOND = 1000;
    private final PreferenceContext preferenceContext;
    private final Logger logger;
    private AndroidDebugBridge adb;
    private boolean initiated;

    public AdbFacade(PreferenceContext preferenceContext, Logger logger) {
        this.preferenceContext = preferenceContext;
        this.logger = logger;
    }

    public void initIfNeeded() throws InterruptedException {
        if (initiated) {
            return;
        }
        AndroidDebugBridge.initIfNeeded(false);
        File adbFile = new File(preferenceContext.getAndroidSdkPath(), "platform-tools/adb");
        adb = AndroidDebugBridge.createBridge(adbFile.getAbsolutePath(), false);
        waitForAdb();
        logger.info("adb initiated");
        initiated = true;
    }

    public IDevice[] getDevices() {
        return adb.getDevices();
    }

    public void terminate() {
        // at the moment not needed
        logger.info("terminating");
        initiated = false;
    }

    private void waitForAdb() throws InterruptedException {
        for (int counter = 0; counter < ADB_TIMEOUT; counter++) {
            if (adb.isConnected()) {
                break;
            }
            Thread.sleep(ONE_SECOND);
        }
    }
}
