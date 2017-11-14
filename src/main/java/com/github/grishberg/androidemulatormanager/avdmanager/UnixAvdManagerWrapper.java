package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import org.gradle.api.logging.Logger;

/**
 * Created by grishberg on 12.11.17.
 */
public class UnixAvdManagerWrapper extends AvdManagerWrapper {
    public UnixAvdManagerWrapper(PreferenceContext context, Logger logger) {
        super(context, "/tools/bin/avdmanager", logger);
    }
}
