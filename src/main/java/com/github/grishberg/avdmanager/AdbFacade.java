package com.github.grishberg.avdmanager;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * Created by grishberg on 12.11.17.
 */
public class AdbFacade {
    private AndroidDebugBridge adb;

    public void init() {
        AndroidDebugBridge.initIfNeeded(false);
        adb = AndroidDebugBridge.createBridge();
        waitForAdb();
    }

    public IDevice[] getDevices() {
        return adb.getDevices();
    }

    public void terminate() {

    }

    private static void waitForAdb() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
