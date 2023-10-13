package com.example.fetchapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.fetchapi.databinding.ActivityMainBinding
import okhttp3.Headers
import kotlin.random.Random
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    /*
    {
  "id": 0,
  "firstName": "Daenerys",
  "lastName": "Targaryen",
  "fullName": "Daenerys Targaryen",
  "title": "Mother of Dragons",
  "family": "House Targaryen",
  "image": "daenerys.jpg",
  "imageUrl": "https://thronesapi.com/assets/images/daenerys.jpg"
  }
     */

    private lateinit var binding: ActivityMainBinding
    var image = ""
    var fullName = ""
    var family = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("pokemonImageURL", "pokemon image URL set")

        val button = binding.getPhotoButton
        val imageView = binding.characterImage
        val fullName = binding.textFullName
        val family = binding.textFamily
        getNextImage(button, imageView, fullName, family)

    }

    private fun getCharacterURL(){
        val client = AsyncHttpClient()
        var choice = Random.nextInt(53)
        var json = "https://thronesapi.com/api/v2/Characters/" + choice

        client[json, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                // Access a JSON array response with `json.jsonArray`
                // Log.d("Array", json.jsonArray.toString())
                // Access a JSON array response with `json.jsonObject`
                Log.d("Character", json.jsonObject.toString())

                image = json.jsonObject.getString("imageUrl")
                fullName = json.jsonObject.getString("fullName")
                family = json.jsonObject.getString("family")

                Log.d("check", "$image, $fullName, $family")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Error", errorResponse)
            }
        }]

    }

    private fun getNextImage(button: Button, imageView: ImageView, textView1: TextView, textView2: TextView) {
        button.setOnClickListener {
            getCharacterURL()

            Glide.with(this)
                .load(image)
                .fitCenter()
                .into(imageView)


            textView1.setText("Full Name: $fullName\n")
            textView2.setText(" Family: $family")


        }


    }


}