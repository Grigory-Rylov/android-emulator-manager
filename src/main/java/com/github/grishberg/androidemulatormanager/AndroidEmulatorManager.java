package com.github.grishberg.androidemulatormanager;

import com.android.ddmlib.IDevice;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerException;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFabric;
import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerWrapper;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerException;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFabric;
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerWrapper;
import org.gradle.api.logging.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Facade for creating, deleting and starting emulators.
 */
public class AndroidEmulatorManager {
    private static final int TIMEOUT_FOR_CYCLE = 5;
    private static final String STOPPED = "stopped";
    private static final String PROPERTY_BOOTANIM = "init.svc.bootanim";
    private final EmulatorManagerWrapper emulatorManager;
    private final AdbFacade adbFacade;
    private final AvdManagerWrapper avdManager;
    private final ArrayList<AndroidEmulator> startedEmulators = new ArrayList<>();
    private final Logger logger;

    public AndroidEmulatorManager(PreferenceContext context,
                                  AdbFacade adbFacade,
                                  EmulatorManagerFabric emulatorManagerFabric,
                                  AvdManagerFabric avdManagerFabric,
                                  Logger logger) {
        this.logger = logger;
        emulatorManager = emulatorManagerFabric.createEmulatorManagerForOs(context);
        avdManager = avdManagerFabric.createAvdManagerForOs();
        this.adbFacade = adbFacade;
    }

    /**
     * Creates emulators with parameters.
     *
     * @param args parameters for creating AVDs.
     * @throws InterruptedException
     * @throws AvdManagerException
     */
    public void createEmulators(EmulatorConfig[] args) throws InterruptedException,
            AvdManagerException {
        for (EmulatorConfig arg : args) {
            avdManager.createAvd(arg);
        }
    }

    /**
     * Deletes emulators with name arg.getName()
     *
     * @param args array of parameters, contains AVD names.
     * @throws AvdManagerException
     */
    public void deleteEmulators(EmulatorConfig[] args) throws AvdManagerException {
        for (EmulatorConfig arg : args) {
            avdManager.deleteAvd(arg);
        }
    }

    /**
     * Starts emulators with names arg.getName().
     *
     * @param args args that emulators name.
     * @throws EmulatorManagerException
     */
    public AndroidEmulator[] startEmulators(EmulatorConfig[] args) throws EmulatorManagerException {
        ArrayList<AndroidEmulator> result = new ArrayList<>();
        for (EmulatorConfig arg : args) {
            result.add(emulatorManager.startEmulator(arg));
        }
        startedEmulators.addAll(result);
        return result.toArray(new AndroidEmulator[result.size()]);
    }

    /**
     * Stops launched emulators.
     */
    public void stopRunningEmulators() {
        Iterator<AndroidEmulator> it = startedEmulators.iterator();
        while (it.hasNext()) {
            it.next().stopProcess();
            it.remove();
        }
    }

    /**
     * Waits until all emulators are launched.
     *
     * @param args    emulator args
     * @param timeout timeout for waiting emulators in milliseconds.
     */
    public void waitForEmulatorStarts(EmulatorConfig[] args, long timeout)
            throws InterruptedException, AvdFacadeException {
        HashSet<String> onlineDevices = new HashSet<>();
        long timeoutTime = System.currentTimeMillis() + timeout;
        boolean allEmulatorsAreOnline = false;

        while (!allEmulatorsAreOnline && System.currentTimeMillis() < timeoutTime) {
            Thread.sleep(1000L * TIMEOUT_FOR_CYCLE);
            for (EmulatorConfig arg : args) {
                if (isDeviceOnline(arg)) {
                    onlineDevices.add(arg.getName());
                }
            }
            allEmulatorsAreOnline = onlineDevices.size() == args.length;
        }
        if (!allEmulatorsAreOnline) {
            throw new AvdFacadeException();
        }
    }

    //TODO: update AndroidEmulator model with IDevice reference
    private boolean isDeviceOnline(EmulatorConfig arg) throws InterruptedException {
        IDevice[] devices = adbFacade.getDevices();
        for (IDevice device : devices) {
            if (arg.getName().equals(device.getAvdName())) {
                return device.isOnline() && isDeviceReady(device);
            }
        }
        return false;
    }

    private boolean isDeviceReady(IDevice device) throws InterruptedException {
        try {
            return STOPPED.equals(device.getSystemProperty(PROPERTY_BOOTANIM).get());
        } catch (ExecutionException e) {
            logger.error("GetSystemProperty exception:", e);
            return false;
        }
    }
}
