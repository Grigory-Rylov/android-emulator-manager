package com.github.grishberg.androidemulatormanager

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction
/**
 * Created by grishberg on 09.12.17.
 */
class StopAndDeleteEmulatorsTask extends DefaultTask {
    public static final String NAME = "stopAndDeleteEmulators"
    AndroidEmulatorManager emulatorManager

    @TaskAction
    void runTask() {
        def emulatorConfigs = project.extensions.getByName(EmulatorManagerPlugin.EMULATOR_CONFIGS) as NamedDomainObjectContainer<EmulatorConfig>

        if (emulatorConfigs.size() == 0) {
            throw new GradleException("Need to setup 'emulatorConfigs' extension")
        }

        emulatorManager.stopRunningEmulators()

        emulatorManager.deleteEmulators(emulatorConfigs.asList())

    }
}
