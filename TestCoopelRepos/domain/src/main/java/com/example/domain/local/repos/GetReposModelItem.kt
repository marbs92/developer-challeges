package com.example.domain.local.repos

import androidx.recyclerview.widget.DiffUtil

data class GetReposModelItem(
    private var _createdAt: String? = null,
    private var _defaultBranch: String? = null,
    private var _description: String? = null,
    private var _disabled: Boolean? = null,
    private var _fullName: String? = null,
    private var _homepage: String? = null,
    private var _language: String? = null,
    private var _name: String? = null,
    private var _owner: OwnerModel? = null,
    private var _private: Boolean? = null,
    private var _size: Int? = null,
    private var _stargazersCount: Int? = null,
    private var _updatedAt: String? = null,
    private var _visibility: String? = null
) {

    val createdAt: String
        get() = _createdAt ?: ""

    val defaultBranch: String
        get() = _defaultBranch ?: ""

    val description: String
        get() = _description ?: ""

    val disabled: Boolean
        get() = _disabled ?: true

    val fullName: String
        get() = _fullName ?: ""

    val homepage: String
        get() = _homepage ?: ""

    val language: String
        get() = _language ?: ""

    val name: String
        get() = _name ?: ""

    val owner: OwnerModel
        get() = _owner ?: OwnerModel()

    val private: Boolean
        get() = _private ?: false

    val size: Int
        get() = _size ?: 0

    val stars: Int
        get() = _stargazersCount ?: 0

    val updatedAt: String
        get() = _updatedAt ?: ""

    val visibility: String
        get() = _visibility ?: ""

    class Comparator : DiffUtil.ItemCallback<GetReposModelItem>() {
        override fun areItemsTheSame(
            oldItem: GetReposModelItem,
            newItem: GetReposModelItem
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.visibility == newItem.visibility
        }

        override fun areContentsTheSame(
            oldItem: GetReposModelItem,
            newItem: GetReposModelItem
        ): Boolean = oldItem == newItem
    }
}