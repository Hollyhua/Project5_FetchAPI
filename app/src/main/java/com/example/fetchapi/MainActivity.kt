package com.example.fetchapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.fetchapi.databinding.ActivityMainBinding
import okhttp3.Headers
import kotlin.random.Random


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
    private lateinit var characterList: MutableList<Characters>
    private lateinit var rvCharacters: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("characterURL", "character image URL set")

        rvCharacters = binding.characterList
        characterList = mutableListOf()

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources , R.drawable.divider , null)?.let{
            dividerItemDecoration.setDrawable((it))
        }
        rvCharacters.addItemDecoration(dividerItemDecoration)

        getCharacterURL()
    }

    private fun getCharacterURL(){
        val client = AsyncHttpClient()
        var json = "https://thronesapi.com/api/v2/Characters/"

        client[json, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                // Access a JSON array response with `json.jsonArray`
                Log.d("Array", json.jsonArray.toString())
                // Access a JSON array response with `json.jsonObject`
                // Log.d("Character", json.jsonObject.toString())

                val characterArray = json.jsonArray
                for (i in 0 until characterArray.length()) {
                    val characterJson = characterArray.getJSONObject(i)
                    val character = Characters(
                        characterJson.getInt("id"),
                        characterJson.getString("firstName"),
                        characterJson.getString("lastName"),
                        characterJson.getString("fullName"),
                        characterJson.getString("title"),
                        characterJson.getString("family"),
                        characterJson.getString("image"),
                        characterJson.getString("imageUrl")
                    )
                    characterList.add(character)
                }

                val adapter = CharacterAdapter(characterList)
                rvCharacters.adapter = adapter
                rvCharacters.layoutManager = LinearLayoutManager(this@MainActivity)
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




}