package com.github.grishberg.androidemulatormanager;

/**
 * Contains info about started emulator.
 */
public class AndroidEmulator {
    private final Process emulatorsProcess;
    private final EmulatorConfig config;

    public AndroidEmulator(Process emulatorsProcess,
                           EmulatorConfig config) {
        this.emulatorsProcess = emulatorsProcess;
        this.config = config;
    }

    public boolean isAlive() {
        return emulatorsProcess.isAlive();
    }

    public void stopProcess() {
        emulatorsProcess.destroy();
    }

    public String getAvdName() {
        return config.getName();
    }
}
