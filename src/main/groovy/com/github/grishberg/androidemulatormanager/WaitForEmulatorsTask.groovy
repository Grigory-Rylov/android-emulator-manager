package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 19.11.17.
 */
class WaitForEmulatorsTask extends DefaultTask {
    AndroidEmulatorManager emulatorManager
    EmulatorManagerConfig extConfig

    @TaskAction
    void runTask() {
        emulatorManager.waitForEmulatorStarts(extConfig.emulatorArgs, extConfig.waitingTimeout)
    }
}
