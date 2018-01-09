package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerWrapper;
import com.github.grishberg.androidemulatormanager.emulatormanager.UnixEmulatorManagerWrapper;
import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Integration test for EmulatorManagerWrapper.
 */
public class EmulatorManagerWrapperIntegrationTest extends BaseTestCaseWithLogger {
    @Test
    public void returnEmulatorsList() throws Exception {
        EmulatorManagerConfig config = new EmulatorManagerConfig();
        PreferenceContext context = new PreferenceContext(config, getLogger());
        EmulatorManagerWrapper manager = new UnixEmulatorManagerWrapper(context, getLogger());
        String[] devices = manager.getAvdList();
        Assert.assertTrue(devices.length > 0);
    }
}