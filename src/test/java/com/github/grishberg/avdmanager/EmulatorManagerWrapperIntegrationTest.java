package com.github.grishberg.avdmanager;

import com.github.grishberg.avdmanager.emulatorManager.EmulatorManagerWrapper;
import com.github.grishberg.avdmanager.emulatorManager.UnixEmulatorManagerWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Integration test for EmulatorManagerWrapper.
 */
public class EmulatorManagerWrapperIntegrationTest extends BaseTestCaseWithLogger {
    @Test
    public void returnEmulatorsList() throws Exception {
        PreferenceContext context = new PreferenceContext();
        EmulatorManagerWrapper manager = new UnixEmulatorManagerWrapper(getLogger(), context);
        String[] devices = manager.getAvdList();
        Assert.assertTrue(devices.length > 0);
    }
}