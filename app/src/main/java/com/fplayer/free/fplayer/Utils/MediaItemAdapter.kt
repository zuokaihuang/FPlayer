package com.fplayer.free.fplayer.Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fplayer.free.fplayer.FPlayerApplication.FPlayerApplication
import com.fplayer.free.fplayer.R
import org.w3c.dom.Text

/**
 * Created by Zuokai.huang on 2019/3/5.
 */
class MediaItemAdapter(private var context:Context, private var id: Int, private var list: MutableList<MediaItem>) : BaseAdapter() {
    override fun getItem(p0: Int): Any {
        return list[p0] as MediaItem
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    init {

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var viewHolder:ViewHolder? = null
        var view:View? = null

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(id, parent, false)
            viewHolder = ViewHolder()
            viewHolder.text_view = view.findViewById(R.id.file_name)
            viewHolder.image_view = view.findViewById(R.id.media_icon)
            view.tag = viewHolder
        }else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var _media_item:MediaItem = getItem(position) as MediaItem
        viewHolder.text_view?.setText(_media_item.file_name)

        // we don't nee set image id here now

        return view as View
    }

    private class ViewHolder{
        var image_view: ImageView? = null
        var text_view: TextView? = null
    }
}

