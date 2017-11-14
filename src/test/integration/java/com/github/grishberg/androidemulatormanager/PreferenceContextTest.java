package com.github.grishberg.androidemulatormanager;

import org.junit.Assert;
import org.junit.Test;

/**
 * Check preferences context.
 */
public class PreferenceContextTest {
    @Test
    public void checkAndroidHome() {
        PreferenceContext context = new PreferenceContext();
        Assert.assertNotNull(context.getAndroidSdkPath());
    }
}