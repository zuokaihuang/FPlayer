package com.fplayer.free.fplayer.VideoPlayer

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.MediaController
import com.fplayer.free.fplayer.R
import com.fplayer.free.fplayer.Utils.LogUtils
import kotlinx.android.synthetic.main.video_player_view.*

class SysVideoPlayer : AppCompatActivity() {

    var mUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.video_player_view)
        var _intent = intent

        mUri = _intent.data

        if (mUri != null) {
            LogUtils.debug(mUri.toString())
            video_view.setVideoURI(mUri)
        }

        video_view.setOnPreparedListener {
            video_view.setMediaController(MediaController(this, true))
            video_view.start()
        }

        video_view.setOnCompletionListener {
            //video_view.stopPlayback()
        }

        video_view.setOnErrorListener { mediaPlayer, i, j ->
            LogUtils.debug("player error " + i + " " + j)
            this.finish()
            true
        }



    }

    override fun onStart() {
        super.onStart()
        LogUtils.debug("onStar")
    }

}