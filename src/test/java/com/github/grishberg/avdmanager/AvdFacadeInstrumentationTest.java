package com.github.grishberg.avdmanager;

import com.github.grishberg.avdmanager.avdManager.AvdManagerFabric;
import com.github.grishberg.avdmanager.emulatorManager.EmulatorManagerFabric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Starts emulator and waits for online state.
 */
public class AvdFacadeInstrumentationTest {
    private AndroidAvdFacade avdFacade;
    private AdbFacade adbFacade;
    private EmulatorConfig arg = new EmulatorConfig("test", DisplayMode.HDPI, 26);

    @Before
    public void setUp() throws Exception {
        PreferenceContext context = new PreferenceContext();
        adbFacade = new AdbFacade();
        adbFacade.init();
        avdFacade = new AndroidAvdFacade(context, adbFacade,
                new EmulatorManagerFabric(), new AvdManagerFabric());
    }

    @After
    public void tearDown() throws Exception {
        adbFacade.terminate();
    }

    @Test
    public void runEmulator() throws Exception {
        EmulatorConfig[] args = {arg};
        avdFacade.createEmulators(args);
        avdFacade.startEmulators(args);
        avdFacade.waitForEmulatorStarts(args, 30 * 1000);
        avdFacade.stopRunningEmulators();
        avdFacade.deleteEmulators(args);
    }
}