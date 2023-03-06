package com.example.domain.network.response.repos

import com.example.domain.network.response.BaseResponse

data class GetReposResponseModelItem(
    val created_at: String? = null,
    val default_branch: String? = null,
    val description: String? = null,
    val disabled: Boolean? = null,
    val full_name: String? = null,
    val homepage: String? = null,
    val language: String? = null,
    val name: String? = null,
    val owner: Owner? = null,
    val private: Boolean? = null,
    val size: Int? = null,
    val stargazers_count: Int? = null,
    val updated_at: String? = null,
    val visibility: String? = null
): BaseResponse()
