package com.example.data.network.repository.tags

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.generic.ErrorModel
import com.example.data.service.GenericServices
import com.example.domain.network.response.tags.Commit
import com.example.domain.network.response.tags.GetTagsResponseModelItem
import com.google.gson.Gson
import retrofit2.awaitResponse
import javax.inject.Inject

class TagsRepositoryImpl  @Inject constructor(
    private val genericServices: GenericServices,
    private val gson: Gson
) : TagsRepository {


    override suspend fun getTagsList(owner: String, repo: String): List<GetTagsResponseModelItem> {

        val response = genericServices.getTagsList(
            owner = owner,
            repo = repo,
            authorization = BuildConfig.GITHUB_TOKEN
        ).awaitResponse()
        return if (response.isSuccessful) {
            val responseModel = try {
                val json = gson.toJson(response.body())

                gson.fromJson(json, Array<GetTagsResponseModelItem>::class.java).toList().apply {
                    this.first().code = response.code()
                }
            } catch (e: Exception) {
                Log.e("ParseError", e.message.toString())
                null
            }
            responseModel ?: listOf(
                GetTagsResponseModelItem(
                    Commit(null, null),
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

            listOf(GetTagsResponseModelItem(Commit(null, null), null, null, null, null).apply {
                code = errorModel.code ?: 404
                message = errorModel.message
            })
        }
    }

}