package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerWrapper;
import com.github.grishberg.androidemulatormanager.emulatormanager.UnixEmulatorManagerWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Integration test for EmulatorManagerWrapper.
 */
public class EmulatorManagerWrapperIntegrationTest extends BaseTestCaseWithLogger {
    @Test
    public void returnEmulatorsList() throws Exception {
        PreferenceContext context = new PreferenceContext();
        EmulatorManagerWrapper manager = new UnixEmulatorManagerWrapper(context, getLogger());
        String[] devices = manager.getAvdList();
        Assert.assertTrue(devices.length > 0);
    }
}