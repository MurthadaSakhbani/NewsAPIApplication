package com.example.newsapiapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.newsapiapplication.adapter.MainHeadlinesAdapter
import com.example.newsapiapplication.adapter.TopHeadlinesAdapter
import com.example.newsapiapplication.models.TopHeadline
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var topHeadlineRecyclerView: RecyclerView
    lateinit var mainRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topHeadlineRecyclerView = findViewById(R.id.headerRecyclerView)
        mainRecyclerView = findViewById(R.id.allNewsRecyclerView)

        fetchHeadline()
        fetchAllNews()
    }

    fun fetchHeadline() {
        AndroidNetworking.get(Constants.BASE_URL + "top-headlines")
            .addQueryParameter("apiKey", Constants.API_KEY)
            .addQueryParameter("country", Constants.COUNTRY_CODE)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val topHeadlines = TopHeadline.fromJSONArray(response.getJSONArray("articles"))
                    val adapter = TopHeadlinesAdapter(topHeadlines)
                    topHeadlineRecyclerView.adapter = adapter
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun fetchAllNews() {
        AndroidNetworking.get(Constants.BASE_URL + "everything")
            .addQueryParameter("apiKey", Constants.API_KEY)
            .addQueryParameter("q", "bitcoin")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val topHeadlines = TopHeadline.fromJSONArray(response.getJSONArray("articles"))
                    val adapter = MainHeadlinesAdapter(topHeadlines)
                    mainRecyclerView.adapter = adapter
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            })

    }
}