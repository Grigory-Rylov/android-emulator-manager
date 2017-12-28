package com.github.grishberg.androidemulatormanager;

import org.gradle.api.logging.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Contains info about started emulator.
 */
public class AndroidEmulator {
    private final Logger logger;
    private final Process emulatorsProcess;
    private final EmulatorConfig config;
    private AvdStopper connectedDevice = new EmptyEmulatorStopper();

    public AndroidEmulator(Process emulatorsProcess,
                           EmulatorConfig config, Logger logger) {
        this.emulatorsProcess = emulatorsProcess;
        this.config = config;
        this.logger = logger;
    }

    public boolean isAlive() {
        return emulatorsProcess.isAlive();
    }

    public void stopEmulator() throws InterruptedException {
        logger.info("try to stop emulator {}", config.getName());
        connectedDevice.stopEmulator();
        for (int i = 0; i < 12; i++) {
            emulatorsProcess.waitFor(5, TimeUnit.SECONDS);
            if (!emulatorsProcess.isAlive()) {
                logger.info("emulator {} stopped", config.getName());
                return;
            }
        }
        logger.info("emulator {} destroyForcibly", config.getName());
        emulatorsProcess.destroyForcibly();
    }

    public void stopEmulatorForcibly(){
        logger.info("emulator {} destroyForcibly", config.getName());
        emulatorsProcess.destroyForcibly();
    }

    public String getAvdName() {
        return config.getName();
    }

    public void setConnectedDevice(AvdStopper connectedDevice) {
        this.connectedDevice = connectedDevice;
    }

    private static class EmptyEmulatorStopper implements AvdStopper {
        @Override
        public void stopEmulator() {
            // not used
        }
    }
}
