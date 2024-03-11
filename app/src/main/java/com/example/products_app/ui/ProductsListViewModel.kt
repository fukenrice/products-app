package com.example.products_app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.products_app.data.ProductsRepository
import com.example.products_app.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(repository: ProductsRepository) : ViewModel() {

    private val search = MutableLiveData("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val productsFlow: Flow<PagingData<Product>> = search
        .asFlow().
        flatMapLatest {
            return@flatMapLatest repository.getProducts().map {
                return@map it.filter { product ->
                    return@filter product.title.lowercase().contains(search.value?.lowercase() ?: "")
                }
            }
        }.cachedIn(viewModelScope)

    fun setSearch(query: String) {
        search.value = query
    }
}