package com.limbo.kotlindex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.limbo.kotlindex.di.DaggerPokemonComponent
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import javax.inject.Inject

open class BaseFragment: Fragment() {
    @Inject lateinit var pokeApiHttpRepository: PokeApiHttpRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerPokemonComponent.create().inject(this)
        Log.d("BaseFrag","pokeApiHttpRepository string: $pokeApiHttpRepository")
    }
}