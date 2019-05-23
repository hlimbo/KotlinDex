package com.limbo.kotlindex

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var pokemonName = view.findViewById<TextView>(R.id.pokemonName)
    var pokemonIcon = view.findViewById<ImageView>(R.id.pokemonIcon)
    var pokemonNumber = view.findViewById<TextView>(R.id.pokemonNumber)
}