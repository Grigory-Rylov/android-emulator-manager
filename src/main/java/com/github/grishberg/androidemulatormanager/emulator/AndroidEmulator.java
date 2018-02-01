package com.github.grishberg.androidemulatormanager.emulator;

import com.github.grishberg.androidemulatormanager.AvdStopper;
import com.github.grishberg.androidemulatormanager.EmulatorConfig;
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
    private ReadErrorThread readErrorThread;
    private ReadOutputThread readOutputThread;


    public AndroidEmulator(Process emulatorsProcess,
                           EmulatorConfig config, Logger logger) {
        this.emulatorsProcess = emulatorsProcess;
        this.config = config;
        this.logger = logger;
        startReadErrorThread();
    }

    private void startReadErrorThread() {
        if (readErrorThread != null) {
            return;
        }
        readErrorThread = new ReadErrorThread(config.getName(), emulatorsProcess.getErrorStream(), logger);
        readErrorThread.start();

        readOutputThread = new ReadOutputThread(config.getName(), emulatorsProcess, logger);
        readOutputThread.start();
    }

    private void stopReadErrorThread() {
        readErrorThread.interrupt();
    }

    public boolean isAlive() {
        return emulatorsProcess.isAlive();
    }

    public void stopEmulator() throws InterruptedException {
        stopReadErrorThread();
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

    public void stopEmulatorForcibly() {
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
