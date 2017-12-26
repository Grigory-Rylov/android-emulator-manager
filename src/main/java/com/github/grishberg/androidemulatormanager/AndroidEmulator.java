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

    public AndroidEmulator(Process emulatorsProcess,
                           EmulatorConfig config, Logger logger) {
        this.emulatorsProcess = emulatorsProcess;
        this.config = config;
        this.logger = logger;
    }

    public boolean isAlive() {
        return emulatorsProcess.isAlive();
    }

    public void stopProcess() throws InterruptedException {
        for (int i = 0; i < 60; i++) {
            logger.info("try #{} to stop emulator {}", i + 1, config.getName());
            emulatorsProcess.destroy();
            emulatorsProcess.waitFor(1, TimeUnit.SECONDS);
            if (!emulatorsProcess.isAlive()) {
                logger.info("emulator {} is not alive", config.getName());
                break;
            }
        }
        if (emulatorsProcess.isAlive()) {
            logger.info("emulator {} destroyForcibly", config.getName());
            emulatorsProcess.destroyForcibly();
        }
        logger.info("emulator {} stopped", config.getName());
    }

    public String getAvdName() {
        return config.getName();
    }
}
