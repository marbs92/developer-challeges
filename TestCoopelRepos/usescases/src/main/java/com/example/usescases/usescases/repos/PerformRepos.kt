package com.example.usescases.usescases.repos

import com.example.data.generic.LocalResult
import com.example.data.network.repository.repos.ReposRepository
import com.example.domain.local.repos.GetReposModelItem
import com.example.domain.local.repos.OwnerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PerformRepos @Inject constructor(
    private val getReposRepository: ReposRepository
) {
    suspend operator fun invoke(
        username: String
    ): LocalResult<List<GetReposModelItem>> {
        return withContext(Dispatchers.IO) {
            val response = getReposRepository.getReposList(username)
            val reposList = response.map {
                GetReposModelItem(
                    it.created_at,
                    it.default_branch,
                    it.description,
                    it.disabled,
                    it.full_name,
                    it.homepage,
                    it.language,
                    it.name,
                    OwnerModel(
                        it.owner?.avatar_url,
                        it.owner?.login,
                        it.owner?.repos_url,
                        it.owner?.type
                    ),
                    it.private,
                    it.size,
                    it.stargazers_count,
                    it.updated_at,
                    it.visibility
                )
            }
            if (response.first().code == 200) {
                LocalResult(reposList, 200)
            } else {
                LocalResult(reposList, response.first().code, response.first().message)
            }
        }
    }
}