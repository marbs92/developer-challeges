package com.example.data.di

import com.example.data.network.repository.readme.ReadmeRepository
import com.example.data.network.repository.readme.ReadmeRepositoryImpl
import com.example.data.network.repository.repos.ReposRepository
import com.example.data.network.repository.repos.ReposRepositoryImpl
import com.example.data.network.repository.tags.TagsRepository
import com.example.data.network.repository.tags.TagsRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ServicesModule {


    @Provides
    fun providesReposRepository(impl: ReposRepositoryImpl): ReposRepository = impl

    @Provides
    fun providesTagsRepository(impl: TagsRepositoryImpl): TagsRepository = impl

    @Provides
    fun providesReadmeRepository(impl: ReadmeRepositoryImpl): ReadmeRepository = impl
}