package com.example.chirkov_android.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chirkov_android.data.AuthStore

class CatalogViewModelFactory(
    private val authStore: AuthStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(authStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}