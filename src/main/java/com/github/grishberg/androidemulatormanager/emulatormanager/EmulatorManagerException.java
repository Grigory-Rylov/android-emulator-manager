package com.github.grishberg.androidemulatormanager.emulatormanager;

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
