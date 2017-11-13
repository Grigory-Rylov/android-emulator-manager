package com.github.grishberg.avdmanager.avdManager;

import com.github.grishberg.avdmanager.PreferenceContext;
import org.gradle.api.logging.Logger;

/**
 * Created by grishberg on 12.11.17.
 */
public class UnixAvdManagerWrapper extends AvdManagerWrapper {
    public UnixAvdManagerWrapper(PreferenceContext context, Logger logger) {
        super(context, "/tools/bin/avdmanager", logger);
    }
}
