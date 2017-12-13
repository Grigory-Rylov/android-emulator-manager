package com.github.grishberg.androidemulatormanager.avdmanager;

/**
 * Created by grishberg on 12.12.17.
 */
public class NoEmulatorImageException extends AvdManagerException {

    public NoEmulatorImageException(String errorString) {
        super(errorString);
    }
}
