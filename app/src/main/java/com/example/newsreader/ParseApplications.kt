package com.example.newsreader

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    private val TAG = "ParseApplication"
    val applicationsList = ArrayList<FeedEntry>()

    fun parse(xmlData: String): Boolean {
        Log.d(TAG, "Parse called with xml $xmlData")
        var status = true
        var inItem = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()

            // ______________________________________________________________

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name?.toLowerCase()

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "parse: Starting tag for $tagName")
                        if (tagName == "item")
                            inItem = true
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
                        if (inItem) {
                            when (tagName) {
                                "item" -> {
                                    applicationsList.add(currentRecord)
                                    inItem = false
                                    currentRecord = FeedEntry()     // create a new object
                                }

                                "title" -> currentRecord.title = textValue
                                "description" -> currentRecord.description = textValue
                                "link" -> currentRecord.link = textValue
                                "pubdate" -> currentRecord.publishDate = textValue

                            }
                        }
                    }
                }
                eventType = xpp.next()
            }

            // for debugging
            for (app in applicationsList) {
                Log.d(TAG, "**************************************")
                Log.d(TAG, app.toString())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}