package com.github.grishberg.avdmanager.avdManager;

/**
 * Created by grishberg on 12.11.17.
 */
public class AvdManagerException extends Exception {
    public AvdManagerException(String message, Throwable e) {
        super(message, e);
    }

    public AvdManagerException(String errorString) {
        super(errorString);
    }
}
