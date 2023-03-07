package com.example.data.service

import com.example.domain.network.response.readme.ReadmeResponseModel
import com.example.domain.network.response.repos.GetReposResponseModelItem
import com.example.domain.network.response.tags.GetTagsResponseModelItem
import retrofit2.Call
import retrofit2.http.*

interface GenericServices {


    @GET("/repos/{owner}/{repo}/tags")
    fun getTagsList(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Header("Authorization") authorization: String,
    ): Call<List<GetTagsResponseModelItem>>

    @GET("/repos/{owner}/{repo}/readme")
    fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Header("Authorization") authorization: String,
      //  @Header("Accept") accept: String,
    ): Call<ReadmeResponseModel>


    @GET("users/{username}/repos")
    fun getRepos(
        @Path("username") username: String,
        @Header("Authorization") authorization: String
    ): Call<List<GetReposResponseModelItem>>

}