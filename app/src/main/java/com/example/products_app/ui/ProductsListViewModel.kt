package com.example.products_app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.products_app.data.ProductsRepository
import com.example.products_app.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(repository: ProductsRepository) : ViewModel() {

    private val search = MutableLiveData<String>()

    val productsFlow: Flow<PagingData<Product>> = repository.getProducts()
        .cachedIn(viewModelScope)

    fun setSearch(query: String) {
        if (query == search.value) {
            return
        }
        search.value = query
    }

}