package com.limbo.kotlindex

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.limbo.kotlindex.models.FlavorTextEntryModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import com.limbo.kotlindex.repository.PokeApiNetworkPagingRepository
import com.limbo.kotlindex.viewmodels.PokemonInfoViewModel
import com.limbo.kotlindex.viewmodels.SearchResultPagingViewModel
import kotlinx.android.synthetic.main.pokemon_info_view.*

class PokeInfoFragment : Fragment() {
    private val TAG = "PokeInfoFrag"

    val args: PokeInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pokemon_info_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, ".onViewCreated")

        if(savedInstanceState == null) {
            Log.d(TAG, "savedInstanceState for this fragment is null")
        }

        pokemonName.text = args.pokemonName

        val model = getViewModel()
        model.getFlavorText(pokemonName.text.toString()).observe(this, Observer<FlavorTextEntryModel> {
            pokemonDescText.text = it.flavorText
        })
    }

    // TODO: should move this out into its own generic utility function for other viewModels to use
    private fun getViewModel(): PokemonInfoViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = PokeApiHttpRepository()

                @Suppress("UNCHECKED_CAST")
                return PokemonInfoViewModel(repo) as T
            }
        })[PokemonInfoViewModel::class.java]
    }
}
