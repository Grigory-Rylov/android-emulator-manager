package com.github.grishberg.avdmanager.avdManager;

import com.github.grishberg.avdmanager.PreferenceContext;

/**
 * Created by grishberg on 12.11.17.
 */
public class MacAvdManagerWrapper extends AvdManagerWrapper {
    public MacAvdManagerWrapper(PreferenceContext context) {
        super(context, "/tools/bin/avdmanager");
    }
}
