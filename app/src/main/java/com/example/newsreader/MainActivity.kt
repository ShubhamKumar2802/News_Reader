package com.example.newsreader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_adapter_layout.*
import java.net.URL
import kotlin.properties.Delegates

var feedUrl = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms"


class FeedEntry {
    var title = ""
    var description = ""
    var link = ""
    var publishDate = ""

    override fun toString(): String {
        return """
            Title: $title
            Description: $description
            Link: $link
            Publish date: $publishDate
            """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {

    private val TAG = "Main_Activity"
    private var downloadData: DownloadData? = null

    //KEYS
    private var feedCashedUrl = "INVALIDATED"
    private val STATE_URL = "feedUrl"
    private var STATE_HEADER = "PageHeader"
    private var TOGGLE_STATE = "ToggleState"
    private var toggle = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: called")


        // FAB
        fabNewsVidToggle.setOnClickListener {
            if (toggle) {
                feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3812907.cms"
                tvPageNAme.text = "Videos"
                fabNewsVidToggle.setImageResource(R.drawable.ic_news)
                toggle = false
                Log.d(TAG, "Toggle is $toggle")
            } else {
                feedUrl = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms"
                tvPageNAme.text = "Top Stories"
                fabNewsVidToggle.setImageResource(R.drawable.ic_video)
                toggle = true
                Log.d(TAG, "Toggle is $toggle")
            }
            downloadUrl(feedUrl)
        }

        // Refresh button
        ivRefresh.setOnClickListener {
            feedCashedUrl = "INVALIDATED"
            downloadUrl(feedUrl)
            val snakBar = Snackbar.make(it, "Feed Refreshed", Snackbar.LENGTH_LONG)
            snakBar.show()
            Log.d(TAG, "Refresh button clicked. feedCashedUrl is now $feedCashedUrl")
        }

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(STATE_URL).toString()
            tvPageNAme.text = savedInstanceState.getString(STATE_HEADER)
            toggle = savedInstanceState.getBoolean(TOGGLE_STATE)
            if (toggle) {
                fabNewsVidToggle.setImageResource(R.drawable.ic_video)
            } else {
                fabNewsVidToggle.setImageResource(R.drawable.ic_news)

            }
        }

        if (savedInstanceState == null) {
            tvPageNAme.text = "Top Stories"
            fabNewsVidToggle.setImageResource(R.drawable.ic_video)
        }
        downloadUrl(feedUrl)


//         OnClickListener for ListView
//        if(feedUrl == "https://timesofindia.indiatimes.com/rssfeedstopstories.cms") {
//        lvMain.setOnItemClickListener { parent, view, position, id ->
//            Log.d(TAG, "setOnItemClickListener: called")
//            parent = AdapterView<FeedAdapter().context>
//            if(position == 0) {
//                val intentNews = Intent().apply {
//                    action = Intent.ACTION_VIEW
//                    putExtra("position", position)
//                    data = Uri.parse("https://www.google.com")
//                    Log.d(TAG, "IntentNews done")
//                }
//                startActivity(intentNews)
//            }
//        }

//        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart: called")
        Log.d(TAG, "Link is ${tvLink?.text}")

        lvMain.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                TODO("Not yet implemented")
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://www.google.com")
                }
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_HEADER, tvPageNAme.text.toString())
        outState.putString(STATE_URL, feedUrl)
        outState.putBoolean(TOGGLE_STATE, toggle)


    }

    private fun downloadUrl(feedUrl: String) {

        if (feedUrl != feedCashedUrl) {
            Log.d(TAG, "downloadUrl starting AsyncTask")

            downloadData = DownloadData(this, lvMain)
            downloadData?.execute(feedUrl)
            feedCashedUrl = feedUrl
            Log.d(TAG, "downloadUrl: done. feedCashedUrl is now $feedCashedUrl")
        } else {
            Log.d(TAG, "downloadUrl: URL unchanged")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) :
            AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground called: starts with ${url[0]}")

                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty())
                    Log.e(TAG, "doInBackground: Error downloading")
                return rssFeed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parseApplication = ParseApplications()
                parseApplication.parse(result)

                if (feedUrl == "https://timesofindia.indiatimes.com/rssfeedstopstories.cms") {
                    val feedAdapter = FeedAdapter(
                        propContext,
                        R.layout.news_adapter_layout,
                        parseApplication.applicationsList
                    )
                    propListView.adapter = feedAdapter
                } else {
                    val feedAdapter = FeedAdapterVid(
                        propContext,
                        R.layout.video_adapter_layout,
                        parseApplication.applicationsList
                    )
                    propListView.adapter = feedAdapter
                }

                Log.d(TAG, "onPostExecute: linkNews is $linkNews")

            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
//                return URL(urlPath).readBytes().toString(Charsets.UTF_8).removePrefix("\uFEFF")
            }
        }
    }
}