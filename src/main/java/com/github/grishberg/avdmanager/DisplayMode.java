package com.github.grishberg.avdmanager;

/**
 * Created by grishberg on 12.11.17.
 */
public enum DisplayMode {
    HDPI(768, 1280, 320);
    private final int width;
    private final int height;
    private final int density;

    DisplayMode(int width, int height, int density) {
        this.width = width;
        this.height = height;
        this.density = density;
    }
}
