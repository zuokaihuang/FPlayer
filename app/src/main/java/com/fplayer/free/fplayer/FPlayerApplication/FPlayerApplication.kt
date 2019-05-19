package com.fplayer.free.fplayer.FPlayerApplication


import android.app.Application
import android.content.Context

class FPlayerApplication : Application() {

    companion object {
        var mContext: Context? = null

        fun getContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this.applicationContext
    }




}
