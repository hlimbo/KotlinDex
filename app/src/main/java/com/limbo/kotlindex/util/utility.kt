package com.limbo.kotlindex.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

// how to rewrite this function such that it can be reused to retrieve a ViewModel of any kind?
// goal: create a viewModel getter that returns the requested viewModel based on ViewModelType passed in parameters list
// my attempt of getting a generic function does not work because it returns a PlaceHolderViewModel which could be any kind of PlaceHolderViewModel
// failed to use generics here due to above reason ^
//fun getViewModel(fragment: Fragment, repo: AbstractRepository): PlaceHolderViewModel {
//    val viewModelProvider = ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//            // this code may be unsafe due to Type Erasure
//            @Suppress("UNCHECKED_CAST")
//            return PlaceHolderViewModel(repo) as T
//        }
//    })
//
//    return viewModelProvider[PlaceHolderViewModel::class.java]
//}

// Source: https://proandroiddev.com/view-model-creation-in-android-android-architecture-components-kotlin-ce9f6b93a46b
// An extension function for Fragment class to use where it takes in a lambda function named creator that is responsible
// for creating the parameters required to instantiate the ViewModel of type T
inline fun <reified T: ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if(creator == null) // use the no-arg ViewModel constructor
        ViewModelProviders.of(this)[T::class.java]
    else // use a lambda function named creator to explicitly instantiate the ViewModel class with constructor parameters
        ViewModelProviders.of(this, BaseViewModelFactory(creator))[T::class.java]
}
