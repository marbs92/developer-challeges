package com.example.data.generic

data class LocalResult<out T>(
    val data: T?,
    private val _status: Int = 0,
    private val _message: String? = null
) : BaseModel(_status, _message)

