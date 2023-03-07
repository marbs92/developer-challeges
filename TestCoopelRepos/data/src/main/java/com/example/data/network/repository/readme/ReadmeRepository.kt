package com.example.data.network.repository.readme

import com.example.domain.network.response.readme.ReadmeResponseModel

interface ReadmeRepository {
    suspend fun getReadme(owner: String, repo: String): ReadmeResponseModel
}