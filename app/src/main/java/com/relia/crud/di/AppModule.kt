package com.relia.crud.di

import com.relia.crud.data.ProductRepository
import com.relia.crud.data.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository = ProductRepositoryImpl()
}