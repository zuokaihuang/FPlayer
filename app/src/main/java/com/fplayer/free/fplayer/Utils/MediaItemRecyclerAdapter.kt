package com.fplayer.free.fplayer.Utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.fplayer.free.fplayer.R

class MediaItemRecyclerAdapter(private var ctx:Context, private var list:MutableList<MediaItem>, private val resource_id: Int) : RecyclerView.Adapter<MediaItemRecyclerAdapter.MediaItemRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MediaItemRecyclerViewHolder {
        return MediaItemRecyclerViewHolder(LayoutInflater.from(ctx).inflate(R.layout.media_item_v2, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MediaItemRecyclerViewHolder?, position: Int) {
        val _media_item = list[position]

        holder?.textView?.text = _media_item.file_name
        holder?.textView?.setOnClickListener {
            Toast.makeText(ctx, "click", Toast.LENGTH_LONG).show()
        }

    }

    inner class MediaItemRecyclerViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        var imageView: ImageView = view.findViewById(R.id.media_icon_v2)
        var textView : TextView = view.findViewById(R.id.file_name_v2)

    }

}