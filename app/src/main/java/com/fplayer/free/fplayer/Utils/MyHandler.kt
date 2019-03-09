package com.fplayer.free.fplayer.Utils

import android.os.Handler
import java.lang.ref.WeakReference

/**
 * Created by Zuokai.huang on 2019/3/2.
 */
open class MyHandler<T>(cls: T) : Handler() {
    protected var ref:WeakReference<T>? = null   // 问号表示可以为空值

    init {
        ref = WeakReference(cls)
    }

    fun get(): T? {
        return if (ref == null) null else ref!!.get()
    }
}