package com.limbo.kotlindex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.limbo.kotlindex.models.PokemonModel
import com.squareup.picasso.Picasso

class SearchResultAdapter(private var searchResultsList: MutableList<PokemonModel>): RecyclerView.Adapter<SearchResultViewHolder>() {
    val TAG = "SearchResultAdapt"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        Log.d(TAG, ".onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_view, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchResultsList.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        Log.d(TAG, ".onBindViewHolder called on position: $position")
        // this method when called binds the view holder to a view that can be reused!
        val searchResult = searchResultsList[position]

        holder.pokemonName.text = searchResult.name

        Picasso.get().load(searchResult.sprites.iconPath)
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_foreground)
            .resize(120, 120)
            .centerInside()
            .into(holder.pokemonIcon)
    }

    fun insertTheData(theData: PokemonModel) {
        searchResultsList.add(theData)
        notifyDataSetChanged()
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        Log.d(TAG, ".registerAdapterDataObserver called")
        super.registerAdapterDataObserver(observer)
    }
}