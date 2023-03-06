package com.example.domain.local.repos

data class OwnerModel(
     var _avatarUrl: String? = null,
     var _login: String? = null,
     var _reposUrl: String? = null,
     var _type: String? = null,
){
    val avatarUrl: String
    get() = _avatarUrl ?:""

    val login: String
    get() = _login ?:""

    val reposUrl: String
    get() = _reposUrl ?:""

    val type: String
    get() = _type ?:""
}