package com.example.domain.network.response.tags

import com.example.domain.network.response.BaseResponse

data class GetTagsResponseModelItem(
    val commit: Commit? = null,
    val name: String? = null,
    val node_id: String? = null,
    val tarball_url: String? = null,
    val zipball_url: String? = null
) : BaseResponse() {

}

data class Commit(
    val sha: String? = null,
    val url: String? = null
):BaseResponse()