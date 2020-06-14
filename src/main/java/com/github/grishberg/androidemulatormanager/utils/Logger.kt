package com.github.grishberg.androidemulatormanager.utils

interface Logger {
    fun isInfoEnabled(): Boolean
    fun debug(msg: String)
    fun info(msg: String)
    fun info(format: String, s1: Any)
    fun info(format: String, o1: Any, o2: Any)
    fun info(format: String, o1: Any, o2: Any, o3: Any, o4: Any)
    fun error(errorString: String)
    fun error(errorString: String, t: Throwable)
    fun lifecycle(s: String, s1: Any)
    fun error(format: String, o1: Any, o2: Any)
}
