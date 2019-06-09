package com.limbo.kotlindex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.limbo.kotlindex.dagger2example.DaggerMagicBox
import com.limbo.kotlindex.dagger2example.Info
import com.limbo.kotlindex.di.ApplicationModule
import com.limbo.kotlindex.di.DaggerPokemonComponent
import com.limbo.kotlindex.di.DaggerServicesComponent
import com.limbo.kotlindex.repository.PokeApiHttpService
import javax.inject.Inject

// implement pagination - DONE
// convert network calls to coroutines ~ DONE
// load sprite image of pokemon - DONE
// implement Pokemon information page - DONE
/*
    * name
    * id
    * abilities
    * front sprite (I wish I could use a bigger picture... maybe find a different API for this)
    * types
 */
// refactor getViewModel to be reusable for any ViewModel subtype ~ DONE
// use Dagger2 for Dependency Injection (use it so that my Fragment classes don't have DIRECT dependencies required for Repository class) ~ DONE
    // refactor code so that I don't need to explicitly inject repo dependency for viewModel class (instead do this through Dagger2 Convention)
// TODO: implement search bar (exact bar search)
// TODO: save data requested over the network on a local db on phone
// TODO: save image url paths on a local db
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    @Inject lateinit var info: Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, ".onCreate called")

        // This is class is autogenerated by Dagger2 (DaggerMagicBox)
        DaggerMagicBox.create().poke(this)
        Log.d(TAG, "The info injected: ${info.text}")
    }
}
