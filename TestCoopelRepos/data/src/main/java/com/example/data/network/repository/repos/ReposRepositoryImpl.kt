package com.example.data.network.repository.repos

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.generic.ErrorModel
import com.example.data.service.GenericServices
import com.example.domain.network.response.repos.GetReposResponseModelItem
import com.example.domain.network.response.tags.Commit
import com.google.gson.Gson
import retrofit2.awaitResponse
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val genericServices: GenericServices,
    private val gson: Gson
) : ReposRepository {

    override suspend fun getReposList(
        username: String
    ): List<GetReposResponseModelItem> {

        val response = genericServices.getRepos(
            username = username,
            authorization = BuildConfig.GITHUB_TOKEN
        ).awaitResponse()

        return if (response.isSuccessful) {
            val responseModel = try {
                val json = gson.toJson(response.body())

                gson.fromJson(json, Array<GetReposResponseModelItem>::class.java).toList().apply {
                    this.first().code = response.code()
                }
            } catch (e: Exception) {
                Log.e("ParseError", e.message.toString())
                null
            }
            responseModel ?: listOf(
                GetReposResponseModelItem(
                    null, null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ).apply {
                    code = response.code()
                    message = "An error has ocurred parsing value"
                })
        } else {
            val errorBody = response.errorBody()?.source()
            val json = gson.toJson(errorBody)
            val errorModel = gson.fromJson(json, ErrorModel::class.java)

            listOf(
                GetReposResponseModelItem(
                    null, null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ).apply {
                    code = errorModel.code ?: 404
                    message = errorModel.message
                })
        }
    }

}