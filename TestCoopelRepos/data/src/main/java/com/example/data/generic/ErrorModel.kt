package com.example.data.generic

import com.google.gson.annotations.SerializedName

open class ErrorModel(
  @SerializedName( "detail") val detail: String?,
  @SerializedName( "code") val code: Int?,
  @SerializedName( "message") val message: String?
)