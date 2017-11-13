package com.github.grishberg.avdmanager;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by grishberg on 12.11.17.
 */
public class PreferenceContextTest {
    @Test
    public void checkAndroidHome() {
        PreferenceContext context = new PreferenceContext();
        Assert.assertNotNull(context.getAndroidSdkPath());
    }
}