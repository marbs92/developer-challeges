package com.example.data.network.repository.repos

import com.example.domain.network.response.repos.GetReposResponseModelItem

interface ReposRepository {

    suspend fun getReposList(
        username: String
    ): List<GetReposResponseModelItem>
}