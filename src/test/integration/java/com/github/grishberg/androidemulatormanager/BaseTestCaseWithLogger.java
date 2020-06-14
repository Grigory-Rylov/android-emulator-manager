package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.utils.Logger;
import org.gradle.api.Project;
import org.gradle.api.logging.LogLevel;
import org.gradle.internal.logging.sink.OutputEventRenderer;
import org.gradle.internal.logging.slf4j.OutputEventListenerBackedLoggerContext;
import org.gradle.testfixtures.ProjectBuilder;
import org.slf4j.LoggerFactory;

/**
 * Created by grishberg on 14.11.17.
 */
public class BaseTestCaseWithLogger {
    private final Project project;

    public BaseTestCaseWithLogger() {
        initLogger();
        project = ProjectBuilder.builder().build();
    }

    private void initLogger() {
        OutputEventListenerBackedLoggerContext loggerFactory = (OutputEventListenerBackedLoggerContext) LoggerFactory.getILoggerFactory();
        loggerFactory.setLevel(LogLevel.INFO);
        OutputEventRenderer outputEventListener = (OutputEventRenderer) loggerFactory.getOutputEventListener();
        outputEventListener.configure(LogLevel.INFO);
    }

    protected Logger getLogger() {
        return new GradleLogger(project.getLogger());
    }

    protected Project getProject() {
        return project;
    }
}
