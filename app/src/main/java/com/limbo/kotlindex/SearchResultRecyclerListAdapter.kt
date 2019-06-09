package com.limbo.kotlindex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.limbo.kotlindex.fragments.MainFragmentDirections
import com.limbo.kotlindex.models.SearchResultModel
import com.squareup.picasso.Picasso

class SearchResultRecyclerListAdapter: RecyclerView.Adapter<PokemonResultViewHolder>() {
    private var itemCount: Int  = 0
    private var searchResults = ArrayList<SearchResultModel>()
    private val pokePicUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    private val PNG = ".png"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_view, parent, false)
        return PokemonResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun onBindViewHolder(holder: PokemonResultViewHolder, position: Int) {
        val searchResultRecord = searchResults[position]
        holder.pokemonName.text = searchResultRecord?.name
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
            val action = MainFragmentDirections.actionMainFragmentToPokeInfoFragment(searchResultRecord.name)
            holder.view.findNavController().navigate(action)
        }
    }

    fun submitData(listData: List<SearchResultModel>) {
        searchResults = ArrayList(listData)
        itemCount = searchResults.size
        notifyDataSetChanged()
    }
}