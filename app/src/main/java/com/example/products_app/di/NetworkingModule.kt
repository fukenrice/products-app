package com.example.products_app.di

import com.example.products_app.data.ProductsRepository
import com.example.products_app.data.remote.ProductsApi
import com.example.products_app.data.remote.RemoteProductsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkingModule {
    @Binds
    abstract fun bindRemoteRepository(repository: RemoteProductsRepository) : ProductsRepository

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit() : Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val gson = GsonConverterFactory.create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .client(client)
                .addConverterFactory(gson)
                .build()
            return retrofit
        }
        @Provides
        fun provideProductsApi(retrofit: Retrofit): ProductsApi {
            return retrofit.create(ProductsApi::class.java)
        }
    }
}