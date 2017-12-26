package com.github.grishberg.androidemulatormanager

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFabric
import com.github.grishberg.androidemulatormanager.avdmanager.HardwareManager
import com.github.grishberg.androidemulatormanager.avdmanager.SdkManager
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFabric
import com.github.grishberg.androidemulatormanager.utils.SysUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class EmulatorManagerPlugin implements Plugin<Project> {
    public static final String CONFIG_NAME = 'emulatorManagerConfig'
    private AndroidEmulatorManager androidEmulatorManager

    @Override
    void apply(Project project) {
        EmulatorManagerConfig config = project.extensions.create(CONFIG_NAME, EmulatorManagerConfig)

        PreferenceContext context = new PreferenceContext()
        final AdbFacade adbFacade = new AdbFacade(project.logger)
        EmulatorManagerFabric emulatorManagerFabric = new EmulatorManagerFabric(project.logger)
        HardwareManager hardwareManager = new HardwareManager(SysUtils.getAvdHomeDir(),
                project.logger)
        SdkManager sdkManager = new SdkManager(context, "/tools/bin/sdkmanager", project.logger)
        AvdManagerFabric avdManagerFabric = new AvdManagerFabric(context,
                hardwareManager,
                sdkManager,
                project.logger)
        androidEmulatorManager = new AndroidEmulatorManager(context, adbFacade,
                emulatorManagerFabric, avdManagerFabric, project.logger)

        adbFacade.init()

        project.tasks.create(CreateAndRunEmulatorsTask.NAME, CreateAndRunEmulatorsTask) {
            emulatorManager = androidEmulatorManager
            extConfig = config
        }

        project.tasks.create(StopAndDeleteEmulatorsTask.NAME, StopAndDeleteEmulatorsTask) {
            emulatorManager = androidEmulatorManager
            extConfig = config
        }

        project.tasks.create('createEmulators', CreateEmulatorsTask) {
            emulatorManager = androidEmulatorManager
            extConfig = config
        }

        project.tasks.create(StartEmulatorsTask.NAME, StartEmulatorsTask) {
            emulatorManager = androidEmulatorManager
            extConfig = config
        }

        project.tasks.create(WaitForEmulatorsTask.NAME, WaitForEmulatorsTask) {
            emulatorManager = androidEmulatorManager
            extConfig = config
        }

        project.tasks.create(StopEmulatorsTask.NAME, StopEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create('deleteEmulators', DeleteEmulatorsTask) {
            emulatorManager = androidEmulatorManager
            extConfig = config
        }
    }
}
