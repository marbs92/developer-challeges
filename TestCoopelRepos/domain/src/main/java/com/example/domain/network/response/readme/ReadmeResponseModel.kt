package com.example.domain.network.response.readme

import com.example.domain.network.response.BaseResponse

data class ReadmeResponseModel(
    val content: String? = null,
    val encoding: String? = null,
    val git_url: String? = null,
    val html_url: String? = null,
    val name: String? = null,
    val path: String? = null,
    val sha: String? = null,
    val size: Int? = null,
    val type: String? = null,
    val url: String? = null
): BaseResponse()
