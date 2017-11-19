package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerException;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFabric;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerWrapper;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFabric;
import org.gradle.api.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by grishberg on 14.11.17.
 */
public class AndroidEmulatorManagerTest {
    private AndroidEmulatorManager emulatorManager;
    @Mock
    PreferenceContext preferenceContext;
    @Mock
    AdbFacade adbFacade;
    @Mock
    EmulatorManagerFabric emulatorManagerFabric;
    @Mock
    AvdManagerFabric avdManagerFabric;
    @Mock
    AvdManagerWrapper avdManager;
    @Mock
    Logger logger;

    @Before
    public void setUp() {
        initMocks(this);
        when(avdManagerFabric.createAvdManagerForOs()).thenReturn(avdManager);

        emulatorManager = new AndroidEmulatorManager(preferenceContext,
                adbFacade,
                emulatorManagerFabric,
                avdManagerFabric,
                logger);
    }

    @Test
    public void testCreateEmulators() throws AvdManagerException, InterruptedException {
        EmulatorConfig emulatorConfig = new EmulatorConfig("test",
                DisplayMode.PHONE_HDPI, 26);
        emulatorManager.createEmulators(new EmulatorConfig[]{emulatorConfig});

        verify(avdManager).createAvd(emulatorConfig);
    }
}
