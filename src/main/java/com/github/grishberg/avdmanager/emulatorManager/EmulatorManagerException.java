package com.github.grishberg.avdmanager.emulatorManager;

/**
 * Exception for EmulatorManager.
 */
public class EmulatorManagerException extends Exception {
    public EmulatorManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmulatorManagerException(String message) {
        super(message);
    }
}
