package com.github.grishberg.avdmanager.avdManager;

/**
 * Exceptions for AvdManager.
 */
public class AvdManagerException extends Exception {
    public AvdManagerException(String message, Throwable e) {
        super(message, e);
    }

    public AvdManagerException(String errorString) {
        super(errorString);
    }
}
