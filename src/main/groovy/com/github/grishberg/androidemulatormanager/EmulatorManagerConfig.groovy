package com.github.grishberg.androidemulatormanager

/**
 * Extension object for configuring Android emulator manager.
 */
class EmulatorManagerConfig {
    EmulatorConfig[] emulatorArgs
    long waitingTimeout = 60 * 1000
}
