package com.example.chirkov_android.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chirkov_android.data.RetrofitInstance
import com.example.chirkov_android.data.module.CategoryDto
import com.example.chirkov_android.data.module.ProductDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {

    private val api = RetrofitInstance.catalogService

    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    val categories: StateFlow<List<CategoryDto>> = _categories

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    val products: StateFlow<List<ProductDto>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // сюда временно кладём title, который пришёл из Home через навигацию
    private var pendingTitle: String? = null

    init {
        loadCategories()
    }

    /** Вызывай из CatalogScreen в LaunchedEffect(initialCategoryTitle) */
    fun setInitialCategoryTitle(title: String) {
        pendingTitle = title
        applyPendingTitleIfPossible()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true

            val dbCats = api.getCategories() // Tennis/Men/Women/Outdoor
            val all = CategoryDto(id = "all", title = "Все")
            _categories.value = listOf(all) + dbCats

            // если пришёл title из Home — выберем его; иначе Outdoor; иначе Все
            val targetIndex =
                findIndexByTitle(pendingTitle)
                    ?: _categories.value.indexOfFirst { it.title.equals("Outdoor", true) }.takeIf { it >= 0 }
                    ?: 0

            pendingTitle = null
            onCategorySelected(targetIndex)

            _isLoading.value = false
        }
    }

    fun onCategorySelected(index: Int) {
        _selectedIndex.value = index

        viewModelScope.launch {
            _isLoading.value = true

            val cat = _categories.value.getOrNull(index)

            _products.value =
                if (cat == null || cat.id == "all" || cat.title.equals("Все", true)) {
                    api.getProducts()
                } else {
                    api.getProductsByCategoryId(categoryIdEq = "eq.${cat.id}")
                }

            _isLoading.value = false
        }
    }

    private fun applyPendingTitleIfPossible() {
        val title = pendingTitle ?: return
        if (_categories.value.isEmpty()) return

        val idx = findIndexByTitle(title) ?: 0
        pendingTitle = null
        onCategorySelected(idx)
    }

    private fun findIndexByTitle(title: String?): Int? {
        val t = title?.trim().orEmpty()
        if (t.isEmpty()) return null
        return _categories.value.indexOfFirst { it.title.equals(t, ignoreCase = true) }
            .takeIf { it >= 0 }
    }
}