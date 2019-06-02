package com.limbo.kotlindex.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory<T>(val creator: () -> T): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // may not be safe due to Type Erasure
        @Suppress("UNCHECKED_CAST")
        return creator() as T
    }
}