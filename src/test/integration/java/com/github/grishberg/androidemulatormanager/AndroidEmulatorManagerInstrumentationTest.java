package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFabric;
import com.github.grishberg.androidemulatormanager.avdmanager.HardwareManager;
import com.github.grishberg.androidemulatormanager.avdmanager.SdkManager;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFabric;
import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Starts emulator and waits for online state.
 */
public class AndroidEmulatorManagerInstrumentationTest extends BaseTestCaseWithLogger {
    private AndroidEmulatorManager emulatorManager;
    private AdbFacade adbFacade;
    private EmulatorConfig argPhone = new EmulatorConfig("test_phone",
            DisplayMode.getPhoneHdpi(), 26);
    private EmulatorConfig argTablet = new EmulatorConfig("test_tablet",
            DisplayMode.getTabletXhdpi(), 26);

    @Before
    public void setUp() throws Exception {
        EmulatorManagerConfig config = new EmulatorManagerConfig();
        PreferenceContext context = new PreferenceContext(config, getLogger());
        adbFacade = new AdbFacade(context, getLogger());
        HardwareManager hardwareManager = new HardwareManager(context, getLogger());
        SdkManager sdkManager = new SdkManager(context, "/tools/bin/sdkmanager",
                getLogger());

        emulatorManager = new AndroidEmulatorManager(context, adbFacade,
                new EmulatorManagerFabric(getLogger()),
                new AvdManagerFabric(context, hardwareManager, sdkManager, getLogger()),
                getLogger());

        emulatorManager.initIfNeeded();
    }

    @After
    public void tearDown() throws Exception {
        adbFacade.terminate();
    }

    @Test
    public void runEmulator() throws Exception {
        List<EmulatorConfig> args = Arrays.asList(argPhone, argTablet);
        emulatorManager.createEmulators(args);
        emulatorManager.startEmulators(args);
        emulatorManager.waitForEmulatorStarts(args, 60 * 1000);
        emulatorManager.stopRunningEmulators();
        //emulatorManager.deleteEmulators(args);
    }
}