package com.example.domain.local.readme

data class ReadmeModel(
    private val _content: String? = null,
    private val _encoding: String? = null,
    private val _git_url: String? = null,
    private val _html_url: String? = null,
    private val _name: String? = null,
    private val _path: String? = null,
    private val _sha: String? = null,
    private val _size: Int? = null,
    private val _type: String? = null,
    private val _url: String? = null
) {
    val content: String
        get() = _content ?: ""

    val encoding: String
        get() = _encoding ?: ""

    val gitUrl: String
        get() = _git_url ?: ""

    val html: String
        get() = _html_url ?: ""

    val name: String
        get() = _name ?: ""



}