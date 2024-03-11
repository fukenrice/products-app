package com.example.products_app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.products_app.data.model.Product
import com.example.products_app.data.remote.ProductsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val PAGE_SIZE = 20

class ProductsPagingSource @Inject constructor(private val api: ProductsApi, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> = withContext(dispatcher) {
        val skip = params.key ?: 0
        return@withContext try {
            val products = api.getProducts(skip, PAGE_SIZE).products
            LoadResult.Page(
                data = products,
                prevKey = if (skip == 0) null else skip - PAGE_SIZE,
                nextKey = if (products.size == PAGE_SIZE) skip + PAGE_SIZE else null
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}