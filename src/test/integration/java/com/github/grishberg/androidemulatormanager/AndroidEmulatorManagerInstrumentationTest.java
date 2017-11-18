package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFabric;
import com.github.grishberg.androidemulatormanager.avdmanager.HardwareManager;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFabric;
import com.github.grishberg.androidemulatormanager.utils.SysUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Starts emulator and waits for online state.
 */
public class AndroidEmulatorManagerInstrumentationTest extends BaseTestCaseWithLogger {
    private AndroidEmulatorManager emulatorManager;
    private AdbFacade adbFacade;
    private EmulatorConfig arg_phone = new EmulatorConfig("test_phone",
            DisplayMode.PHONE_HDPI, 26);
    private EmulatorConfig arg_tablet = new EmulatorConfig("test_tablet",
            DisplayMode.TABLET_XHDPI, 26);

    public AndroidEmulatorManagerInstrumentationTest() {
    }

    @Before
    public void setUp() throws Exception {
        PreferenceContext context = new PreferenceContext();
        adbFacade = new AdbFacade(getLogger());
        adbFacade.init();
        HardwareManager hardwareManager = new HardwareManager(SysUtils.getAvdHomeDir(), getLogger());
        emulatorManager = new AndroidEmulatorManager(context, adbFacade,
                new EmulatorManagerFabric(getLogger()),
                new AvdManagerFabric(context, hardwareManager, getLogger()),
                getLogger());
    }

    @After
    public void tearDown() throws Exception {
        adbFacade.terminate();
    }

    @Test
    public void runEmulator() throws Exception {
        EmulatorConfig[] args = {arg_phone, arg_tablet};
        emulatorManager.createEmulators(args);
        emulatorManager.startEmulators(args);
        emulatorManager.waitForEmulatorStarts(args, 60 * 1000);
        emulatorManager.stopRunningEmulators();
        emulatorManager.deleteEmulators(args);
    }
}