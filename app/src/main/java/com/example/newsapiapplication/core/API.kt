package com.example.newsapiapplication.core

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.example.newsapiapplication.Constants
import org.json.JSONException
import org.json.JSONObject

open class API {
    companion object {
        fun handleErrorResponse(context: Context, error: ANError) {
            when (error.errorCode) {
                0 -> Toast.makeText(context, "Unable to connect to the internet. Please check your internet connectivity.", Toast.LENGTH_LONG).show()
                429 -> Toast.makeText(context, "Mohon tunggu 1 menit sebelum meminta kode verifikasi lagi", Toast.LENGTH_LONG).show()
                else -> {
                    try {
                        val jsonData = JSONObject(error.errorBody)
                        val errorCode = if (jsonData.has("error_code")) "${jsonData.getString("error_code")}: " else ""
                        if (jsonData.has("error_message"))
                            Toast.makeText(context, "${errorCode}${jsonData.getString("error_message")}", Toast.LENGTH_LONG).show()
                        else if (jsonData.has("detail"))
                            Toast.makeText(context, "${errorCode}${jsonData.getString("detail")}", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(context, "Sorry, we had trouble to processing your request (${error.errorCode}), please try again later.", Toast.LENGTH_LONG).show()
                    } catch (_: JSONException) {
                        Toast.makeText(context, "Unable to parse server response (${error.errorCode}). Please try again later.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        fun createPostRequest(context: Context, url: String, body: JSONObject?): ANRequest<out ANRequest<*>> {
            val json = addAuthorizationToBody(context, body)
            return AndroidNetworking.post("${Constants.BASE_URL}$url")
                .addJSONObjectBody(json)
                .setPriority(Priority.HIGH)
                .build()
        }

        fun createGetRequest(context: Context, url: String, body: JSONObject? = null): ANRequest<out ANRequest<*>> {
            val json = addAuthorizationToBody(context, body)
            var requestUrl = "${Constants.BASE_URL}$url?"
            for ((index, key) in json.keys().withIndex()) {
                if (index+1 == json.length()) {
                    requestUrl += "$key=${json[key]}"
                } else {
                    requestUrl += "$key=${json[key]}&"
                }
            }

            return AndroidNetworking.get(requestUrl)
                .setPriority(Priority.HIGH)
                .build()
        }

        fun addAuthorizationToBody(context: Context, body: JSONObject?): JSONObject {
            var json = body
            if (json == null)
                json = JSONObject()

            json.put("apiKey", Constants.API_KEY)

            return json
        }
    }
}
