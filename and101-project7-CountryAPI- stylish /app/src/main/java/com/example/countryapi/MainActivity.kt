package com.example.countryapi

import CountryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class MainActivity : AppCompatActivity() {

    private lateinit var countryList: MutableList<CountryData>
    private lateinit var rvCountries: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCountries = findViewById(R.id.country_list)
        countryList = mutableListOf()

        getRandomCountry()
    }

    private fun getRandomCountry() {
        val client = AsyncHttpClient()
        val apiKey = "9MWestJqOWkmfuZ6KOECvvLl8u9Ffszm8BkmXveF"
        val url = "https://countryapi.io/api/all?apikey=$apiKey"


        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: JSON) {
                Log.d("success_url", "$response")

                val keysList = response.jsonObject.keys().asSequence().toList()

                for (key in keysList) {
                    val country = response.jsonObject.getJSONObject(key)

                    val name = country.getString("official_name")  // optString will return an empty string if the key does not exist
                    val code = country.getString("alpha3Code")
                    val region = country.getString("subregion")

                    // Accessing the flag URL
                    val flagURL = country.getJSONObject("flag").getString("medium")

                    // create a CountryData object & add it to the list
                    val countryData = CountryData(flagURL, name, code, region)
                    countryList.add(countryData)
                }
                val adapter = CountryAdapter(countryList)
                rvCountries.adapter = adapter
                rvCountries.layoutManager = LinearLayoutManager(this@MainActivity)

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


}