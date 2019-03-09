package com.fplayer.free.fplayer.Utils

import android.util.Log

class LogUtils {

    companion object {
        val tag: String = "FPlayer"
        var disable_log:Boolean = false

        fun debug(value:String) {
            if (disable_log) {
                return
            }
            var tag: String = getTag(getCallerStackTraceElement())
            Log.d(tag, value)
        }

        private  fun getTag(element : StackTraceElement) : String {
            var className : String = element.className
            className = className.substring(className.lastIndexOf(".") + 1)
            return "$tag:$className.${element.methodName}(${element.lineNumber})"
        }

        private fun getCallerStackTraceElement(): StackTraceElement = Thread.currentThread().getStackTrace()[5]
    }


}