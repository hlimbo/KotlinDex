package com.limbo.kotlindex

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import com.limbo.kotlindex.di.DaggerServicesComponent
import com.limbo.kotlindex.di.ServicesComponent
import com.limbo.kotlindex.repository.PokeApiHttpService
import javax.inject.Inject

// Note if module not used (e.g. not using @Inject annotation to inject some dependency) it will do have some code in the autogenerated file deprecated
// https://stackoverflow.com/questions/36521302/dagger-2-2-component-builder-module-method-deprecated
class MainApplication : Application(){
    private val TAG = "MainApplication"

//     @Inject lateinit var pokeApiHttpService: PokeApiHttpService

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate called")
        // injecting the dependencies in MainApplication
        // for other classes such as Activities and Fragments to see the injected values
        // you must inject the values here in MainApplication class first
        // then you access the injected value from activity or fragment by obtaining
        // a reference to their application.
//        DaggerServicesComponent.create().inject(this)
//        Log.d(TAG, "injected value: $pokeApiHttpService")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged called")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(TAG, "onLowMemory called")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate called")
    }
}