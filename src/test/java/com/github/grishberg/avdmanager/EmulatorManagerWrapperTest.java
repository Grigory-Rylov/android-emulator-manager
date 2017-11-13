package com.github.grishberg.avdmanager;

import com.github.grishberg.avdmanager.emulatorManager.EmulatorManagerWrapper;
import com.github.grishberg.avdmanager.emulatorManager.MacEmulatorManagerWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by grishberg on 12.11.17.
 */
public class EmulatorManagerWrapperTest {
    @Test
    public void returnEmulatorsList() throws Exception {
        PreferenceContext context = new PreferenceContext();
        EmulatorManagerWrapper manager = new MacEmulatorManagerWrapper(context);
        String[] devices = manager.getAvdList();
        Assert.assertTrue(devices.length > 0);
    }
}