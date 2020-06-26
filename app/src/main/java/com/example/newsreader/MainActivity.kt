package com.example.newsreader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

var feedUrl = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms"
var index: Int = 1
var toggle = true

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
    private var INDEX = "Index"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: called")

        tvPageNAme.text = "TOP STORIES"

        // FAB
        fabNewsVidToggle.setOnClickListener {
//            if (toggle) {
//                feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3812907.cms"
//                tvPageNAme.text = "Videos"
//                fabNewsVidToggle.setImageResource(R.drawable.ic_news)
//                toggle = false
//                Log.d(TAG, "Toggle is $toggle")
//            } else {
//                feedUrl = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms"
//                tvPageNAme.text = "Top Stories"
//                fabNewsVidToggle.setImageResource(R.drawable.ic_video)
//                toggle = true
//                Log.d(TAG, "Toggle is $toggle")
//            }
//            downloadUrl(feedUrl)

            if (toggle) {
                when (index) {
                    1 -> { // News
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3812907.cms"
                        val snakbar =
                            Snackbar.make(it, "News Video Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    2 -> { // Entertainment
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3812908.cms"
                        val snakbar = Snackbar.make(
                            it,
                            "Entertainment Video Feed Loaded",
                            Snackbar.LENGTH_SHORT
                        )
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    3 -> { // Sports
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3813456.cms"
                        val snakbar =
                            Snackbar.make(it, "Sports Video Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    4 -> { // Lifestyle
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3813443.cms"
                        val snakbar =
                            Snackbar.make(it, "Lifestyle Video Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    5 -> { // Business
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3813458.cms"
                        val snakbar =
                            Snackbar.make(it, "Business Video Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    6 -> { // Auto
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedsvideo/3813454.cms"
                        val snakbar =
                            Snackbar.make(it, "Auto Video Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                }

                fabNewsVidToggle.setImageResource(R.drawable.ic_news)
                toggle = false

            } else {
                when (index) {
                    1 -> { // News
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms"
                        val snakbar =
                            Snackbar.make(it, "News Article Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    2 -> { // Entertainment
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/1081479906.cms"
                        val snakbar = Snackbar.make(
                            it,
                            "Entertainment Article Feed Loaded",
                            Snackbar.LENGTH_SHORT
                        )
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    3 -> { // Sports
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/4719148.cms"
                        val snakbar =
                            Snackbar.make(it, "Sports Article Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    4 -> { // Lifestyle
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/2886704.cms"
                        val snakbar = Snackbar.make(
                            it,
                            "Lifestyle Article Feed Loaded",
                            Snackbar.LENGTH_SHORT
                        )
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    5 -> { // Business
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/1898055.cms"
                        val snakbar =
                            Snackbar.make(it, "Business Article Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                    6 -> { // Auto
                        feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/74317216.cms"
                        val snakbar =
                            Snackbar.make(it, "Auto Article Feed Loaded", Snackbar.LENGTH_SHORT)
                        snakbar.setAnchorView(R.id.fabNewsVidToggle)
                        snakbar.show()
                    }
                }


                fabNewsVidToggle.setImageResource(R.drawable.ic_video)
                toggle = true
            }
            downloadUrl(feedUrl)
        }

        // Refresh button
        ivRefresh.setOnClickListener {
            feedCashedUrl = "INVALIDATED"
            downloadUrl(feedUrl)
            val snakBar = Snackbar.make(it, "Feed Refreshed", Snackbar.LENGTH_LONG)
            snakBar.setAnchorView(R.id.fabNewsVidToggle)
            snakBar.show()
            Log.d(TAG, "Refresh button clicked. feedCashedUrl is now $feedCashedUrl")
        }

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(STATE_URL).toString()
            tvPageNAme.text = savedInstanceState.getString(STATE_HEADER)

            toggle = savedInstanceState.getBoolean(TOGGLE_STATE)
            if (toggle)
                fabNewsVidToggle.setImageResource(R.drawable.ic_video)
            else
                fabNewsVidToggle.setImageResource(R.drawable.ic_news)

        }

        if (savedInstanceState == null) {
            tvPageNAme.text = "Top Stories"
            fabNewsVidToggle.setImageResource(R.drawable.ic_video)
        }
        downloadUrl(feedUrl)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_HEADER, tvPageNAme.text.toString())
        outState.putString(STATE_URL, feedUrl)
        outState.putBoolean(TOGGLE_STATE, toggle)
        outState.putInt(INDEX, index)
    }


    // Bottom menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.topStories -> { // News
                feedUrl = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms"
                tvPageNAme.text = "Top Stories"
                index = 1
            }
            R.id.entertainment -> { // Entertainment
                feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/1081479906.cms"
                tvPageNAme.text = "Entertainment"
                index = 2
            }
            R.id.sports -> { // Sports
                feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/4719148.cms"
                tvPageNAme.text = "Sports"
                index = 3
            }
            R.id.lifestyle -> { // Lifestyle
                feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/2886704.cms"
                tvPageNAme.text = "Lifestyle"
                index = 4
            }
            R.id.business -> { // Business
                feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/1898055.cms"
                tvPageNAme.text = "Business"
                index = 5
            }
            R.id.auto -> { // Auto
                feedUrl = "https://timesofindia.indiatimes.com/rssfeeds/74317216.cms"
                tvPageNAme.text = "Auto"
                index = 6
            }
        }
        toggle = true
        downloadUrl(feedUrl)
        return super.onOptionsItemSelected(item)
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