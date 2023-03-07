package com.example.data.network.repository.readme

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.generic.ErrorModel
import com.example.data.service.GenericServices
import com.example.domain.network.response.readme.ReadmeResponseModel
import com.google.gson.Gson
import retrofit2.awaitResponse
import javax.inject.Inject

class ReadmeRepositoryImpl @Inject constructor(
    private val genericServices: GenericServices,
    private val gson: Gson
) : ReadmeRepository {

    override suspend fun getReadme(owner: String, repo: String): ReadmeResponseModel {
        val response = genericServices.getReadme(
            owner = owner,
            repo = repo,
            authorization = BuildConfig.GITHUB_TOKEN,
            //   accept = "application/vnd.github.VERSION.raw"
        ).awaitResponse()
        return if (response.isSuccessful) {
            val responseModel = try {
                val json = gson.toJson(response.body())
                gson.fromJson(json, ReadmeResponseModel::class.java).apply {
                    this?.code = response.code()
                }
            } catch (e: Exception) {
                Log.e("ParseError", e.message.toString())
                null
            }
            responseModel ?: ReadmeResponseModel("", "", "", "", "", "", "", 0, "", "").apply {
                code = response.code()
                message = response.message()
            }
        } else {
            val errorBody = response.errorBody()?.source()
            val json = gson.toJson(errorBody)
            val errorModel = gson.fromJson(json, ErrorModel::class.java)

            ReadmeResponseModel("", "", "", "", "", "", "", 0, "", "").apply {
                code = errorModel.code ?: 404
                message = errorModel.message
            }
        }
    }
}