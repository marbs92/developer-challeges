package com.example.data.network.repository.tags

import com.example.domain.network.response.tags.GetTagsResponseModelItem

interface TagsRepository {

    suspend fun getTagsList(owner: String, repo: String): List<GetTagsResponseModelItem>
}