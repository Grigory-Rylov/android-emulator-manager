package com.github.grishberg.avdmanager.avdmanager;

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerWrapper;
import com.github.grishberg.androidemulatormanager.avdmanager.UnixAvdManagerWrapper;
import com.github.grishberg.avdmanager.BaseTestCaseWithLogger;
import com.github.grishberg.androidemulatormanager.DisplayMode;
import com.github.grishberg.androidemulatormanager.EmulatorConfig;
import com.github.grishberg.androidemulatormanager.PreferenceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Creates and deletes AVDs with names "test1" and "test2".
 */
public class MacAvdManagerTest extends BaseTestCaseWithLogger {
    private AvdManagerWrapper avdManagerWrapper;
    private final EmulatorConfig arg1 = new EmulatorConfig("test1", DisplayMode.HDPI, 26);
    private final EmulatorConfig arg2 = new EmulatorConfig("test2", DisplayMode.HDPI, 26);

    @Before
    public void setUp() throws Exception {
        PreferenceContext context = new PreferenceContext();
        avdManagerWrapper = new UnixAvdManagerWrapper(context, getLogger());
    }

    @After
    public void tearDown() throws Exception {
        avdManagerWrapper.deleteAvd(arg1);
        avdManagerWrapper.deleteAvd(arg2);
    }

    @Test
    public void testCreateEmulator() throws Exception {
        Assert.assertTrue(avdManagerWrapper.createAvd(arg1));
        Assert.assertTrue(avdManagerWrapper.createAvd(arg2));
    }
}