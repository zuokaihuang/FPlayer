package com.fplayer.free.fplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import com.fplayer.free.fplayer.Utils.MyHandler


class FPlayerSplashActivity : AppCompatActivity() {

    private class UISplashHandler(cls : FPlayerSplashActivity) : MyHandler<FPlayerSplashActivity>(cls) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val activity = ref?.get()
            if (null != activity) {
                if (activity.isFinishing)
                    return

                when(msg?.what) {

                    MSG_LAUNCH -> {
                        // do something here
                        startFileBrowserActivity(activity)
                        activity.finish()
                    }

                }
            }
        }

        private fun startFileBrowserActivity(cls : FPlayerSplashActivity?) {
            var intent = Intent(cls, FileBrowserActivity::class.java)
            cls!!.startActivity(intent)
        }


    }

    private val mUiHandler = UISplashHandler(this)

    private val runnable = Runnable {
        kotlin.run {
            val msg = mUiHandler.obtainMessage(MSG_LAUNCH)
            mUiHandler.sendMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fplayer_splash)

        // Example of a call to a native method
        //sample_text.text = stringFromJNI()
        Log.e("FPlayerSplash", stringFromJNI())
    }

    override fun onResume() {
        super.onResume()

        val start = System.currentTimeMillis()

        var diff = System.currentTimeMillis() - start

        var left = SLEEP_TIME - diff

        mUiHandler.postDelayed(runnable, if (left > 0) left else 0)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        val MSG_LAUNCH: Int = 0
        val SLEEP_TIME = 3000

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
