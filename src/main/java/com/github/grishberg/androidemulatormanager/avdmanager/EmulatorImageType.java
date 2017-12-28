package com.github.grishberg.androidemulatormanager.avdmanager;

/**
 * Created by grishberg on 13.12.17.
 */
public enum EmulatorImageType {
    DEFAULT("default"),
    GOOGLE_APIS("google_apis"),
    NONE("");
    private final String imageName;

    EmulatorImageType(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}