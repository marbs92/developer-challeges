package com.example.usescases.usescases.readme

import com.example.data.generic.LocalResult
import com.example.data.network.repository.readme.ReadmeRepository
import com.example.domain.local.readme.ReadmeModel
import com.example.domain.network.response.readme.ReadmeResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PerformReadme @Inject constructor(
    private val readmeRepository: ReadmeRepository
) {

    suspend operator fun invoke(
        owner: String,
        repo: String
    ): LocalResult<ReadmeResponseModel> {
        return withContext(Dispatchers.IO) {
            val response = readmeRepository.getReadme(owner, repo)

            val result = ReadmeModel(
                _content = response.content,
                _git_url = response.git_url,
                _encoding = response.encoding,
                _html_url = response.html_url,
                _name = response.name
            )
            LocalResult(result, response.code, response.message)
            if (response.code == 200) {
                LocalResult(response, 200)
            } else {
                LocalResult(response, response.code, response.message)
            }
        }
    }
}