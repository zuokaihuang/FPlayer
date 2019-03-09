package com.fplayer.free.fplayer


import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.fplayer.free.fplayer.FPlayerApplication.FPlayerApplication
import com.fplayer.free.fplayer.Utils.MediaItem
import com.fplayer.free.fplayer.Utils.MediaItemAdapter
import com.fplayer.free.fplayer.Utils.MyHandler
import com.fplayer.free.fplayer.VideoPlayer.SysVideoPlayer
import com.master.permissionhelper.PermissionHelper
import kotlinx.android.synthetic.main.activity_file_browser.*


class FileBrowserActivity : AppCompatActivity() {

    companion object {
        val MAX_LIST_NUMBER : Int = 10
        val TAG:String = "FileBrowserActivity"
    }

    private var permissionHelper:PermissionHelper? = null

    private var media_file_list:MutableList<MediaItem>? = mutableListOf()
    private var media_item_adapter: MediaItemAdapter? = null

    var m_video_list_view:ListView? = null
    //var media_item_recycler_adapter: MediaItemRecyclerAdapter? = null

    private class ScanerHandler(cls: FileBrowserActivity) : MyHandler<FileBrowserActivity>(cls) {

        private var activity:FileBrowserActivity = cls

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

            // notify ui to show media list
            print("notify ui to show media list")
            Log.e("FPlayer", "notify ui to show media list => " + activity.media_file_list?.size )

            if (activity.media_file_list?.size != 0) {
                //activity.media_item_adapter = MediaItemAdapter(activity, R.layout.media_item, activity.media_file_list!!)
                //activity.mVideoView?.adapter = activity.media_item_adapter
                //activity.media_item_recycler_adapter?.notifyDataSetChanged()

            }
        }
    }

    private var scaner_handler:ScanerHandler = ScanerHandler(this)

    private fun initView() {
        this.setContentView(R.layout.activity_file_browser)
        m_video_list_view = video_list_view
        m_video_list_view!!.setOnItemClickListener(MediaOnItemClickListener())
        media_item_adapter = MediaItemAdapter(this, R.layout.media_item, media_file_list!!)
        Log.e("FPlayer", "notify ui to show media list 11=> " + media_file_list?.size )
        m_video_list_view?.adapter = media_item_adapter
    }

    private fun createEmptyData() {
        var media_item1:MediaItem = MediaItem("1.mp4", 0, 0, "/sdcard/Movies/big_buck_bunny_480p_h264.mov")
        media_file_list?.add(media_item1)
        Log.e(TAG, "add one")
    }

    private fun initData() {
        // get video file from local sdcard or mediascaner
        scaner_handler.run {
            val resolver:ContentResolver = FPlayerApplication.getContext()!!.contentResolver

            val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

            val objs:Array<String> = arrayOf(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DATA)

            val result:Cursor? =  resolver.query(uri, objs, null, null, null)

            while (result!!.moveToNext()) {
                var file_name:String = result.getString(0)
                var duration:Long = result.getLong(1)
                var sz:Long = result.getLong(2)
                var path:String = result.getString(3)

                var mediaItem:MediaItem = MediaItem(file_name, duration, sz, path)

                media_file_list?.add(mediaItem)
                Log.e(TAG, "add one ???")
            }

            result.close()

            scaner_handler.sendEmptyMessage(0)

        }

    }

    private fun requestPermission() {

        permissionHelper = PermissionHelper(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET),
                100)

        permissionHelper?.denied {
            if (it) {
                Log.d(TAG, "Permission denied by system")
                permissionHelper?.openAppDetailsActivity()
            } else {
                Log.d(TAG, "Permission denied")
            }
        }

        //Request individual permission
        permissionHelper?.requestIndividual {
            Log.d(TAG, "Individual Permission Granted")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "File BrowserActivity onCreate")
        createEmptyData()
        initView()
        requestPermission()
        initData()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)

        if (100 == requestCode) {
            if (Manifest.permission.READ_EXTERNAL_STORAGE == permissions[0]) {
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    Log.e(TAG, "we can init data now")

                }
            }
        }

    }

    inner class MediaOnItemClickListener :  AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            var media_item: MediaItem? = media_file_list?.get(p2)

            var intent: Intent = Intent()

            // 1. just test
            //intent.setDataAndType(Uri.parse(media_item!!.path), "video/*")

            // 2. fplayer
            intent.setClass(FPlayerApplication.getContext(), SysVideoPlayer::class.java)
            intent.setDataAndType(Uri.parse(media_item!!.path), "video/*")
            FPlayerApplication.getContext()!!.startActivity(intent)

        }

    }

}

/**
companion object {
private var player:MediaPlayer? = null
private var surfaceview:SurfaceView? = null
}

player = MediaPlayer()
player!!.setDataSource("http://ips.ifeng.com/video19.ifeng.com/video09/2017/05/24/4664192-102-008-1012.mp4")

surfaceview = SurfaceView(this)
this.setContentView(surfaceview!!)

player!!.prepareAsync()

player!!.setOnPreparedListener(MediaPlayer.OnPreparedListener {
player!!.setDisplay(surfaceview!!.holder!!)
player!!.start()
this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
})

player!!.reset()
player!!.release()
 **/