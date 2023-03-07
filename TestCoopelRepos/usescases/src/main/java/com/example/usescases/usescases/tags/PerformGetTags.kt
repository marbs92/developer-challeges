package com.example.usescases.usescases.tags

import com.example.data.generic.LocalResult
import com.example.data.network.repository.tags.TagsRepository
import com.example.domain.network.response.tags.GetTagsResponseModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PerformGetTags @Inject constructor(
    private val tagsRepository: TagsRepository
) {
    suspend operator fun invoke(
        owner: String,
        repo: String
    ): LocalResult<List<GetTagsResponseModelItem>> {
        return withContext(Dispatchers.IO) {
            val response = tagsRepository.getTagsList(owner, repo)
            if (response.first().code == 200) {
                LocalResult(response, 200)
            } else {
                LocalResult(response, response.first().code, response.first().message)
            }
        }
    }
}