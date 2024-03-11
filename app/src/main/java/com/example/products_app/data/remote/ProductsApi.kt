package com.example.products_app.data.remote

import com.example.products_app.data.model.Products
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts(@Query("skip") skip: Int, @Query("limit") limit: Int) : Products
}