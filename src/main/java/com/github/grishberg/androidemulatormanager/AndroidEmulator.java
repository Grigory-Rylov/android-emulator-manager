package com.github.grishberg.androidemulatormanager;

import org.gradle.api.logging.Logger;

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
        logger.info("try to stop emulator {}", config.getName());
        emulatorsProcess.destroy();
        emulatorsProcess.waitFor();
        logger.info("emulator {} stopped", config.getName());
    }

    public String getAvdName() {
        return config.getName();
    }
}
