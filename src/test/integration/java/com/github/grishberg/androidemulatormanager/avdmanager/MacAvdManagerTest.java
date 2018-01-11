package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.*;
import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Creates and deletes AVDs with names "test1" and "test2".
 */
public class MacAvdManagerTest extends BaseTestCaseWithLogger {
    private AvdManagerWrapper avdManagerWrapper;
    private final EmulatorConfig arg1 = new EmulatorConfig("test1", DisplayMode.getPhoneHdpi(), 26);
    private final EmulatorConfig arg2 = new EmulatorConfig("test2", DisplayMode.getPhoneHdpi(), 26);

    @Before
    public void setUp() throws Exception {
        EmulatorManagerConfig extensionObject = new EmulatorManagerConfig();
        PreferenceContext context = new PreferenceContext(extensionObject, getLogger());
        HardwareManager hardwareManager = new HardwareManager(context, getLogger());
        SdkManager sdkManager = new SdkManager(context, "/tools/bin/sdkmanager", getLogger());
        avdManagerWrapper = new UnixAvdManagerWrapper(context, hardwareManager, sdkManager, getLogger());
    }

    @After
    public void tearDown() throws Exception {
        avdManagerWrapper.deleteAvd(arg1);
        avdManagerWrapper.deleteAvd(arg2);
    }

    @Test
    public void testCreateEmulator() throws Exception {
        avdManagerWrapper.createAvd(arg1);
        avdManagerWrapper.createAvd(arg2);
    }
}