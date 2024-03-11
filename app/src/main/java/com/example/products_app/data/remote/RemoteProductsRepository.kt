package com.example.products_app.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.products_app.data.ProductsRepository
import com.example.products_app.data.model.Product
import com.example.products_app.data.paging.PAGE_SIZE
import com.example.products_app.data.paging.ProductsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteProductsRepository @Inject constructor(private val api: ProductsApi) :
    ProductsRepository {
    override fun getProducts(): Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE),
        pagingSourceFactory = { ProductsPagingSource(api) }).flow

}