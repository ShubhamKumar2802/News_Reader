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

var linkNews: String = "Empty String for now"

class ViewHolder(v: View) {
    val tvTitle: TextView = v.findViewById(R.id.tvTitle)
    val tvPublishDate: TextView = v.findViewById(R.id.tvPublishDate)
    val tvDescription: TextView = v.findViewById(R.id.tvDescription)
    val tvLink: TextView = v.findViewById(R.id.tvLink)
}

class FeedAdapter(context: Context, val resource: Int, val applications: List<FeedEntry>) :
    ArrayAdapter<FeedEntry>(context, resource, applications) {
    private val inflater = LayoutInflater.from(context)
    private val TAG = "feedAdapter"


    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        var viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentApp = applications[position]

        viewHolder.tvTitle.text = currentApp.title
        viewHolder.tvPublishDate.text = currentApp.publishDate
        viewHolder.tvDescription.text = currentApp.description
        viewHolder.tvLink.text = currentApp.link
        Log.d(TAG, "getView: link is ${currentApp.link} **********")


        // OnClickListener for each view
        Log.d(TAG, "Above OnClickListener")
        view.setOnClickListener {
            Log.d(TAG, "OnCLickListener: called")
            val intentNews = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(currentApp.link)
            }
            Log.d(TAG, "OnClickListener: Done, Intent about to start")
            startActivity(it.context, intentNews, null)
        }

        return view
    }

}