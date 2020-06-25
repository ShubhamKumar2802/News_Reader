package com.example.newsreader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

class ViewHolderVid(v: View) {
    val tvTitleVid: TextView = v.findViewById(R.id.tvTitleVid)
    val tvPublishDate: TextView = v.findViewById(R.id.tvPublishDateVid)
    val tvLinkVid: TextView = v.findViewById(R.id.tvLinkVid)
}

class FeedAdapterVid(context: Context, val resourceVid: Int, val applicationVid: List<FeedEntry>) :
    ArrayAdapter<FeedEntry>(context, resourceVid, applicationVid) {
    private val inflaterVid = LayoutInflater.from(context)
    private val TAG = "FeedAdapterVid"

    override fun getCount(): Int {
        return applicationVid.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView called")
        val view: View
        val viewHolder: ViewHolderVid
        if (convertView == null) {
            view = inflaterVid.inflate(resourceVid, parent, false)
            viewHolder = ViewHolderVid(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolderVid
        }

        val currentVid = applicationVid[position]

        viewHolder.tvTitleVid.text = currentVid.title
        viewHolder.tvPublishDate.text = currentVid.publishDate
        viewHolder.tvLinkVid.text = currentVid.link
        Log.d(TAG, "FeedAdapterVideo: link is ${currentVid.link}")

        //OnclickListener for Video Feeds
        view.setOnClickListener {
            val intentVid = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(currentVid.link)
            }
            startActivity(it.context, intentVid, null)
        }

        return view
    }
}