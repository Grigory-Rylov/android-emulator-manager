package com.github.grishberg.androidemulatormanager;

import com.github.grishberg.androidemulatormanager.utils.Logger;

public class GradleLogger implements Logger {
    private org.gradle.api.logging.Logger logger;

    public GradleLogger(org.gradle.api.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void info(String format, Object s1) {
        logger.info(format, s1);
    }

    @Override
    public void info(String format, Object o1, Object o2) {
        logger.info(format, o1, o2);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String format, Object o1, Object o2, Object o3, Object o4) {
        logger.info(format, o1, o2, o3, o4);
    }

    @Override
    public void error(String format, Object o1, Object o2) {
        logger.error(format, o1, o2);
    }

    @Override
    public void error(String errorString) {
        logger.error(errorString);
    }

    @Override
    public void error(String errorString, Throwable t) {
        logger.error(errorString, t);
    }

    @Override
    public void lifecycle(String s, Object s1) {
        logger.lifecycle(s, s1);
    }
}
