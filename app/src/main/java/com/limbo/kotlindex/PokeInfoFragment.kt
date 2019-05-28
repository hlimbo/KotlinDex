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
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import com.limbo.kotlindex.repository.PokeApiNetworkPagingRepository
import com.limbo.kotlindex.viewmodels.PokemonInfoViewModel
import com.limbo.kotlindex.viewmodels.SearchResultPagingViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_info_view.*

class PokeInfoFragment : Fragment() {
    private val TAG = "PokeInfoFrag"
    private val args: PokeInfoFragmentArgs by navArgs()

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

        val model = getViewModel()

        pokemonName.text = args.pokemonName
        model.getFlavorText(pokemonName.text.toString()).observe(this, Observer<FlavorTextEntryModel> {
            pokemonDescText.text = it.flavorText
        })

        model.getPokemonBasicInfo(pokemonName.text.toString()).observe(this, Observer<PokemonModel> {
            height.text = "height: ${it.height}"
            weight.text = "weight: ${it.weight}"

            val builder = StringBuilder()
            Log.d(TAG, ".getPokemonBasicInfo first type: ${it.types.first()}")
            it.types.forEach { pokemonType ->
                builder.append(pokemonType.type.name).append(", ")
                Log.d(TAG, ".getPokemonBasicInfo ${pokemonType.type.name}")
            }

            // remove extra comma and space at the end
            builder
                .deleteCharAt(builder.length - 1)
                .deleteCharAt(builder.length - 1)

            types.text = builder.toString()

            Picasso.get().load(it.sprites.iconShinyPath)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(400, 400)
                .centerInside()
                .into(pokemonPic)

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
