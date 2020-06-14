package com.github.grishberg.androidemulatormanager

import com.github.grishberg.androidemulatormanager.avdmanager.AvdManagerFactory
import com.github.grishberg.androidemulatormanager.avdmanager.HardwareManager
import com.github.grishberg.androidemulatormanager.avdmanager.SdkManager
import com.github.grishberg.androidemulatormanager.emulatormanager.EmulatorManagerFactory
import com.github.grishberg.androidemulatormanager.ext.EmulatorManagerConfig
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

class EmulatorManagerPlugin implements Plugin<Project> {
    public static final String CONFIG_NAME = 'emulatorManagerConfig'
    public static final String EMULATOR_CONFIGS = 'emulatorConfigs'
    private AndroidEmulatorManager androidEmulatorManager

    @Override
    void apply(Project project) {
        project.logger.info("EmulatorManagerPlugin: apply")
        EmulatorManagerConfig config = project.extensions.create(CONFIG_NAME, EmulatorManagerConfig)

        NamedDomainObjectContainer<EmulatorConfig> emulatorConfigs = project.container(EmulatorConfig)
        project.extensions.add(EMULATOR_CONFIGS, emulatorConfigs)

        PreferenceContext context = new PreferenceContext(config, project.logger)
        final AdbFacade adbFacade = new AdbFacade(context, project.logger)

        EmulatorManagerFactory emulatorManagerFabric = new EmulatorManagerFactory(project.logger)

        HardwareManager hardwareManager = new HardwareManager(context, project.logger)

        SdkManager sdkManager = new SdkManager(context, "/tools/bin/sdkmanager", project.logger)
        AvdManagerFactory avdManagerFactory = new AvdManagerFactory(context,
                hardwareManager,
                sdkManager,
                project.logger)
        androidEmulatorManager = new AndroidEmulatorManager(context, adbFacade,
                emulatorManagerFabric, avdManagerFactory, project.logger)

        project.tasks.create(CreateAndRunEmulatorsTask.NAME, CreateAndRunEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create(StopAndDeleteEmulatorsTask.NAME, StopAndDeleteEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create(CreateEmulatorsTask.NAME, CreateEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create(StartEmulatorsTask.NAME, StartEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create(WaitForEmulatorsTask.NAME, WaitForEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create(StopEmulatorsTask.NAME, StopEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }

        project.tasks.create(DeleteEmulatorsTask.NAME, DeleteEmulatorsTask) {
            emulatorManager = androidEmulatorManager
        }
    }
}
