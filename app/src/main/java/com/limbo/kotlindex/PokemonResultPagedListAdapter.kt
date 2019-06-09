package com.limbo.kotlindex

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.limbo.kotlindex.fragments.MainFragmentDirections
import com.limbo.kotlindex.models.SearchResultModel
import com.squareup.picasso.Picasso

class PokemonResultPagedListAdapter : PagedListAdapter<SearchResultModel, RecyclerView.ViewHolder>(POST_COMPARATOR){
    private val TAG = "SearchResultPLA"

    private val pokePicUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    private val PNG = ".png"

    // called whenever ui requests a data holder for ui to render (can be cached internally by Android Framework since creating and destroying view objects during runtime is expensive process
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, ".onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_view, parent, false)
        return PokemonResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, ".onBindViewHolder called on position: $position")
        val searchResultRecord = getItem(position)
        (holder as PokemonResultViewHolder).pokemonName.text = searchResultRecord?.name

        // e.g. https://pokeapi.co/api/v2/pokemon/7/ (need to cache pokemon number somewhere..
        val pokemonNumber = if(searchResultRecord != null) {
            searchResultRecord.url.split("/")[6]
        } else {
            "0"
        }
        holder.pokemonNumber.text = "#".plus(pokemonNumber)

        Picasso.get().load("$pokePicUrl$pokemonNumber$PNG")
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_foreground)
            .resize(120, 120)
            .centerInside()
            .into(holder.pokemonIcon)

        holder.view.setOnClickListener {
            Log.d(TAG, "I clicked on ${searchResultRecord?.name}")
            val action = MainFragmentDirections.actionMainFragmentToPokeInfoFragment(searchResultRecord?.name ?: "ditto")
            // one thing I don't like about this code is that findNavController as a dependency on the view
            holder.view.findNavController().navigate(action)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<SearchResultModel>() {
            override fun areItemsTheSame(oldItem: SearchResultModel, newItem: SearchResultModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchResultModel, newItem: SearchResultModel): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}