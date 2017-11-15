package com.github.grishberg.avdmanager;

import com.github.grishberg.androidemulatormanager.*;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFabric;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFabric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Starts emulator and waits for online state.
 */
public class AndroidEmulatorManagerInstrumentationTest extends BaseTestCaseWithLogger {
    private AndroidEmulatorManager emulatorManager;
    private AdbFacade adbFacade;
    private EmulatorConfig arg = new EmulatorConfig("test", DisplayMode.HDPI, 26);

    public AndroidEmulatorManagerInstrumentationTest() {
    }

    @Before
    public void setUp() throws Exception {
        PreferenceContext context = new PreferenceContext();
        adbFacade = new AdbFacade(getLogger());
        emulatorManager = new AndroidEmulatorManager(context, adbFacade,
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
        emulatorManager.createEmulators(args);
        emulatorManager.startEmulators(args);
        emulatorManager.waitForEmulatorStarts(args, 30 * 1000);
        emulatorManager.stopRunningEmulators();
        emulatorManager.deleteEmulators(args);
    }
}