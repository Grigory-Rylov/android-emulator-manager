package com.github.grishberg.androidemulatormanager;

import org.junit.Assert;
import org.junit.Test;

/**
 * Check preferences context.
 */
public class PreferenceContextTest extends BaseTestCaseWithLogger {
    @Test
    public void checkAndroidHome() {
        EmulatorManagerConfig config = new EmulatorManagerConfig();
        PreferenceContext context = new PreferenceContext(config, getLogger());
        Assert.assertNotNull(context.getAndroidSdkPath());
    }
}