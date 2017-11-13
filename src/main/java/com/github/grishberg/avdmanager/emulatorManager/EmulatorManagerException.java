package com.github.grishberg.avdmanager.emulatorManager;

/**
 * Created by grishberg on 12.11.17.
 */
public class EmulatorManagerException extends Exception {
    public EmulatorManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmulatorManagerException(String message) {
        super(message);
    }
}
