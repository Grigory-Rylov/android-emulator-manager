package com.github.grishberg.avdmanager;

import com.github.grishberg.avdmanager.avdmanager.AvdManagerFabric;
import com.github.grishberg.avdmanager.emulatormanager.EmulatorManagerFabric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Starts emulator and waits for online state.
 */
public class AvdFacadeInstrumentationTest extends BaseTestCaseWithLogger {
    private AndroidAvdFacade avdFacade;
    private AdbFacade adbFacade;
    private EmulatorConfig arg = new EmulatorConfig("test", DisplayMode.HDPI, 26);

    public AvdFacadeInstrumentationTest() {
    }

    @Before
    public void setUp() throws Exception {
        PreferenceContext context = new PreferenceContext();
        adbFacade = new AdbFacade(getLogger());
        avdFacade = new AndroidAvdFacade(context, adbFacade,
                new EmulatorManagerFabric(getLogger()),
                new AvdManagerFabric(getLogger()),
                getLogger());
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