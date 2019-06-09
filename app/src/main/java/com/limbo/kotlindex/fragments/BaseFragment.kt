package com.limbo.kotlindex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.limbo.kotlindex.di.DaggerPokemonComponent
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import com.limbo.kotlindex.repository.PokeApiHttpService
import javax.inject.Inject

open class BaseFragment: Fragment() {
    // this annotation can only be called when the component implementation calls the inject() method here directly
    // limitation: can't declare this injected field in the classes that derive from this class as it will throw a runtime exception
    // stating this instance of pokeApiHttpRepository won't be found
    @Inject protected lateinit var pokeApiHttpRepository: PokeApiHttpRepository
    @Inject protected lateinit var pokeApiHttpService: PokeApiHttpService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerPokemonComponent.create().inject(this)
        Log.d("BaseFrag","pokeApiHttpRepository string: $pokeApiHttpRepository")
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        // example of obtaining a dependency injected from MainApplication class
////        Log.d("BaseFrag", "onAttach called with pokeApiHttpRepository string: ${(requireActivity().application as MainApplication).pokeApiHttpService}")
//     }
}