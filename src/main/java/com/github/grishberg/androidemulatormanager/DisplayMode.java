package com.github.grishberg.androidemulatormanager;

/**
 * Created by grishberg on 12.11.17.
 */
public class DisplayMode {
    private final int width;
    private final int height;
    private final int density;

    DisplayMode(int width, int height, int density) {
        this.width = width;
        this.height = height;
        this.density = density;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDensity() {
        return density;
    }

    public static DisplayMode getPhoneHdpi() {
        return new DisplayMode(768, 1280, 320);
    }

    public static DisplayMode getTabletXhdpi() {
        return new DisplayMode(1200, 1920, 160);
    }
}
