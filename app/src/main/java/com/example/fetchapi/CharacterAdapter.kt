package com.example.fetchapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide



class CharacterAdapter(private val characterList: List<Characters>): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val characterImage: ImageView
        val fullName: TextView
        val family: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            characterImage = view.findViewById(R.id.characterImage)
            fullName = view.findViewById(R.id.textFullName)
            family = view.findViewById(R.id.textFamily)


        }




    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.characters, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView)  //bind the photo url to the catImage
            .load(characterList[position].imageUrl)// load the content of the url of the photo, position is the index
            .centerCrop()// make photo centered
            .into(holder.characterImage)//passing imageview with the photo into

        holder.fullName.text = characterList[position].fullName
        holder.family.text = characterList[position].family

        holder.itemView.setOnClickListener {
            // Handle item click here, for example, show a Toast
            val fullName = characterList[position].fullName
            Toast.makeText(holder.itemView.context, "Clicked: $fullName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }



}
