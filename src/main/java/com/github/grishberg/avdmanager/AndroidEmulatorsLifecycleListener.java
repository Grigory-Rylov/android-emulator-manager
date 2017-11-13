package com.github.grishberg.avdmanager;

/**
 * Created by grishberg on 12.11.17.
 */
public interface AndroidEmulatorsLifecycleListener {
    void onEmulatorStarted(AndroidEmulator emulator);

    void onEmulatorKilled(AndroidEmulator emulator);
}
