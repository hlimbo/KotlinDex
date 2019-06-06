package com.limbo.kotlindex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.limbo.kotlindex.R
import com.limbo.kotlindex.di.DaggerPokemonComponent
import com.limbo.kotlindex.models.FlavorTextEntryModel
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import com.limbo.kotlindex.util.getViewModel
import com.limbo.kotlindex.viewmodels.PokemonInfoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_info_view.*
import javax.inject.Inject

class PokeInfoFragment : BaseFragment() {
    private val TAG = "PokeInfoFrag"
    // found in debug directory that Android Framework autogenerates for you when using Navigation library
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

        val model = getViewModel { PokemonInfoViewModel(pokeApiHttpRepository) }
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
}
