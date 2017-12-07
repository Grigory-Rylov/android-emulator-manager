package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by grishberg on 19.11.17.
 */
class StopEmulatorsTask extends DefaultTask {
    AndroidEmulatorManager emulatorManager

    @TaskAction
    void runTask() {
        emulatorManager.stopRunningEmulators()
    }
}
