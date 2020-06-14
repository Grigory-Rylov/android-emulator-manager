package com.github.grishberg.androidemulatormanager;

import com.android.ddmlib.EmulatorConsole;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.MultiLineReceiver;
import com.github.grishberg.androidemulatormanager.utils.Logger;

/**
 * Created by grishberg on 26.12.17.
 */
public class AvdStopperImpl implements AvdStopper {
    private final IDevice device;
    private final Logger logger;

    public AvdStopperImpl(IDevice device, Logger logger) {
        this.device = device;
        this.logger = logger;
    }

    private final MultiLineReceiver receiver = new MultiLineReceiver() {
        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                logger.info(line);
            }
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    };

    @Override
    public void stopEmulator() {
        try {
            EmulatorConsole console = EmulatorConsole.getConsole(device);
            if (console != null) {
                console.kill();
            }
        } catch (Exception e) {
            logger.error("Executing 'emulator-5556 emu kill' failed", e);
        }
    }
}
