package com.github.grishberg.androidemulatormanager.avdmanager;

import com.github.grishberg.androidemulatormanager.PreferenceContext;
import com.github.grishberg.androidemulatormanager.utils.Logger;

/**
 * Created by grishberg on 12.11.17.
 */
public class UnixAvdManagerWrapper extends AvdManagerWrapper {
    public UnixAvdManagerWrapper(PreferenceContext context,
                                 HardwareManager hardwareManager,
                                 SdkManager sdkManager,
                                 Logger logger) {
        super(context, "/tools/bin/avdmanager",
                hardwareManager, sdkManager, logger);
    }
}
