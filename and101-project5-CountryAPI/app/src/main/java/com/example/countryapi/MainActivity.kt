package com.example.countryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var name = ""
    var code = ""
    var flagURL = ""
    var region = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnFetch)
        button.setOnClickListener() {
            getRandomImage()
        }

    }

    private fun getRandomImage() {
        val client = AsyncHttpClient()
        val apiKey = "9MWestJqOWkmfuZ6KOECvvLl8u9Ffszm8BkmXveF"
        val url = "https://countryapi.io/api/all?apikey=$apiKey"


        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: JSON) {
                Log.d("success_url", "Get the url!")

                Log.d("success_url", "$response")
                val keysList = response.jsonObject.keys().asSequence().toList()

                val randomKey = keysList.random()
                val country = response.jsonObject.getJSONObject(randomKey)


                name = country.getString("official_name")  // optString will return an empty string if the key does not exist
                code = country.getString("alpha3Code")
                region = country.getString("subregion")

                // Accessing the flag URL
                flagURL = country.getJSONObject("flag").getString("medium")

                displayData(flagURL, name, code, region)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("URL Error", errorResponse)
            }
        }]
    }


    private fun displayData(imageUrl: String, name: String, temperament: String, lifeSpan: String) {
        val imageView: ImageView = findViewById(R.id.flagImageView)
        val nameTextView: TextView = findViewById(R.id.nameTextView)
        val regionTextView: TextView = findViewById(R.id.regionTextView)

        Glide.with(this)
            .load(flagURL)
            .into(imageView)

        nameTextView.text = "$name, $code"
        regionTextView.text = "$region"

    }

}