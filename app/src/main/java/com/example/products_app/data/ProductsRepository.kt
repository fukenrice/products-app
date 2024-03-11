package com.example.products_app.data

import androidx.paging.PagingData
import com.example.products_app.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts() : Flow<PagingData<Product>>
}