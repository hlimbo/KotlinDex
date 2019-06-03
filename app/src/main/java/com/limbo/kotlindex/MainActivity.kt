package com.limbo.kotlindex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

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
// TODO: implement search bar (exact bar search)
// TODO: save data requested over the network on a local db on phone
// TODO: save image url paths on a local db
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, ".onCreate called")
    }
}
