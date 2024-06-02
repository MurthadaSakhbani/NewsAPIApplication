package com.example.newsapiapplication.models

import org.json.JSONArray
import org.json.JSONObject

class TopHeadline {
    var author: String = ""
    var title: String = ""
    var description: String = ""
    var url: String = ""
    var urlToImage: String = ""
    var publishedAt: String = ""

    companion object {
        fun fromJSONArray(response: JSONArray): List<TopHeadline> {
            val list = mutableListOf<TopHeadline>()

            for (i in 0 until response.length()) {
                val item = response.getJSONObject(i)
                list.add(fromJSON(item))
            }
            return list
        }

        fun fromJSON(response: JSONObject): TopHeadline {
            return TopHeadline().apply {
                author = response.getJSONObject("source").getString("name")
                title = response.getString("title")
                description = response.getString("description")
                url = response.getString("url")
                urlToImage = response.getString("urlToImage")
                publishedAt = response.getString("publishedAt")
            }
        }
    }
}