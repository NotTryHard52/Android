package com.example.chirkov_android.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chirkov_android.data.AuthStore
import com.example.chirkov_android.data.RetrofitInstance
import com.example.chirkov_android.data.module.CategoryDto
import com.example.chirkov_android.data.module.FavouriteDto
import com.example.chirkov_android.data.module.ProductDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class CatalogViewModel(
    private val authStore: AuthStore
) : ViewModel() {

    private val api = RetrofitInstance.catalogService
    private val favouriteApi = RetrofitInstance.favouriteService

    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    val categories: StateFlow<List<CategoryDto>> = _categories

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    val products: StateFlow<List<ProductDto>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // текущий пользователь
    private var userId: String? = null

    // набор product_id в избранном
    private val _favouriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favouriteIds: StateFlow<Set<String>> = _favouriteIds

    // продукты по избранному (для FavoriteScreen)
    private val _favoriteProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val favoriteProducts: StateFlow<List<ProductDto>> = _favoriteProducts

    private var pendingTitle: String? = null

    init {
        viewModelScope.launch {
            authStore.userId.collect { id ->
                userId = id
                if (id != null) {
                    loadFavourites()
                }
            }
        }
        loadCategories()
    }

    fun setInitialCategoryTitle(title: String) {
        pendingTitle = title
        applyPendingTitleIfPossible()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val dbCats = withContext(Dispatchers.IO) {
                    api.getCategories()
                }
                val all = CategoryDto(id = "all", title = "Все")
                _categories.value = listOf(all) + dbCats

                val targetIndex =
                    findIndexByTitle(pendingTitle)
                        ?: _categories.value.indexOfFirst { it.title.equals("Outdoor", true) }
                            .takeIf { it >= 0 }
                        ?: 0

                pendingTitle = null
                onCategorySelected(targetIndex)
            } catch (e: SocketTimeoutException) {
                _error.value = "Превышен таймаут. Проверьте интернет."
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onCategorySelected(index: Int) {
        _selectedIndex.value = index

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val cat = _categories.value.getOrNull(index)
                _products.value = withContext(Dispatchers.IO) {
                    if (cat == null || cat.id == "all" || cat.title.equals("Все", true)) {
                        api.getProducts()
                    } else {
                        api.getProductsByCategoryId(categoryIdEq = "eq.${cat.id}")
                    }
                }
            } catch (e: SocketTimeoutException) {
                _error.value = "Превышен таймаут. Попробуйте еще раз."
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки продуктов: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retry() {
        loadCategories()
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

    // ---------- Избранное: id ----------

    private fun loadFavourites() {
        val uid = userId ?: return
        viewModelScope.launch {
            try {
                val favs = withContext(Dispatchers.IO) {
                    favouriteApi.getFavouritesByUser(
                        userIdEq = "eq.$uid"
                    )
                }
                _favouriteIds.value = favs.mapNotNull { it.product_id }.toSet()
            } catch (_: Exception) {
                // лог по желанию
            }
        }
    }

    fun toggleFavourite(productId: String) {
        val uid = userId ?: return
        val currentlyFav = _favouriteIds.value.contains(productId)

        // оптимистично обновляем ids
        _favouriteIds.value =
            if (currentlyFav) _favouriteIds.value - productId
            else _favouriteIds.value + productId

        // сразу обновляем список favoriteProducts
        _favoriteProducts.value =
            if (currentlyFav) {
                _favoriteProducts.value.filterNot { it.id == productId }
            } else {
                val product = _products.value.firstOrNull { it.id == productId }
                if (product != null && _favoriteProducts.value.none { it.id == productId }) {
                    _favoriteProducts.value + product
                } else {
                    _favoriteProducts.value
                }
            }

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (currentlyFav) {
                        favouriteApi.deleteFavourite(
                            productIdEq = "eq.$productId",
                            userIdEq = "eq.$uid"
                        )
                    } else {
                        favouriteApi.addFavourite(
                            FavouriteDto(
                                product_id = productId,
                                user_id = uid
                            )
                        )
                    }
                }
            } catch (_: Exception) {
                // откатим ids и favoriteProducts, если запрос не прошёл
                _favouriteIds.value =
                    if (currentlyFav) _favouriteIds.value + productId
                    else _favouriteIds.value - productId

                _favoriteProducts.value =
                    if (currentlyFav) {
                        val product = _products.value.firstOrNull { it.id == productId }
                        if (product != null && _favoriteProducts.value.none { it.id == productId }) {
                            _favoriteProducts.value + product
                        } else {
                            _favoriteProducts.value
                        }
                    } else {
                        _favoriteProducts.value.filterNot { it.id == productId }
                    }
            }
        }
    }

    // ---------- Избранное: полные продукты для FavoriteScreen ----------

    fun loadFavoriteProducts() {
        val uid = userId ?: return
        viewModelScope.launch {
            try {
                val favs = withContext(Dispatchers.IO) {
                    favouriteApi.getFavouritesByUser(userIdEq = "eq.$uid")
                }
                val favIds = favs.mapNotNull { it.product_id }
                _favouriteIds.value = favIds.toSet()

                if (favIds.isEmpty()) {
                    _favoriteProducts.value = emptyList()
                    return@launch
                }

                val inParam = "in.(${favIds.joinToString(",")})"
                val favProducts = withContext(Dispatchers.IO) {
                    api.getProductsByIds(idIn = inParam)
                }
                _favoriteProducts.value = favProducts
            } catch (_: Exception) {
                // обработать, если нужно
            }
        }
    }
}