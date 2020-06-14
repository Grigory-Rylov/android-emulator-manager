package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerException;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFactory;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerWrapper;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFactory;
import com.github.grishberg.androidemulatormanager.utils.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;

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
    EmulatorManagerFactory emulatorManagerFabric;
    @Mock
    AvdManagerFactory avdManagerFabric;
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
                DisplayMode.getPhoneHdpi(), 26);
        emulatorManager.createEmulators(Collections.singletonList(emulatorConfig), true);

        verify(avdManager).createAvd(emulatorConfig, true);
    }
}
