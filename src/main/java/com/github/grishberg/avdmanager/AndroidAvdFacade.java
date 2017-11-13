package com.github.grishberg.avdmanager;

import com.android.ddmlib.IDevice;
import com.github.grishberg.avdmanager.avdManager.AvdManagerException;
import com.github.grishberg.avdmanager.avdManager.AvdManagerFabric;
import com.github.grishberg.avdmanager.avdManager.AvdManagerWrapper;
import com.github.grishberg.avdmanager.emulatorManager.EmulatorManagerException;
import com.github.grishberg.avdmanager.emulatorManager.EmulatorManagerFabric;
import com.github.grishberg.avdmanager.emulatorManager.EmulatorManagerWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Facade for creating, deleting and starting emulators.
 */
public class AndroidAvdFacade {
    private static final int TIMEOUT_FOR_CYCLE = 5;
    private final EmulatorManagerWrapper emulatorManager;
    private final AdbFacade adbFacade;
    private final AvdManagerWrapper avdManager;
    private final ArrayList<AndroidEmulator> startedEmulators = new ArrayList<>();

    public AndroidAvdFacade(PreferenceContext context,
                            AdbFacade adbFacade,
                            EmulatorManagerFabric emulatorManagerFabric,
                            AvdManagerFabric avdManagerFabric) {
        emulatorManager = emulatorManagerFabric.createEmulatorManagerForOs(context);
        avdManager = avdManagerFabric.createAvdManagerForOs(context);
        this.adbFacade = adbFacade;
    }

    /**
     * Creates emulators with parameters.
     *
     * @param args parameters for creating AVDs.
     * @throws InterruptedException
     * @throws AvdManagerException
     */
    public void createEmulators(EmulatorConfig[] args) throws InterruptedException, AvdManagerException {
        for (EmulatorConfig arg : args) {
            avdManager.createAvd(arg);
        }
    }

    /**
     * Deletes emulators with name arg.getName()
     *
     * @param args array of parameters, contains AVD names.
     * @throws InterruptedException
     * @throws AvdManagerException
     */
    public void deleteEmulators(EmulatorConfig[] args) throws InterruptedException, AvdManagerException {
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
            Thread.sleep(1000 * TIMEOUT_FOR_CYCLE);
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
    private boolean isDeviceOnline(EmulatorConfig arg) {
        IDevice[] devices = adbFacade.getDevices();
        for (IDevice device : devices) {
            if (device.getAvdName().equals(arg.getName())) {
                return device.isOnline();
            }
        }
        return false;
    }
}
