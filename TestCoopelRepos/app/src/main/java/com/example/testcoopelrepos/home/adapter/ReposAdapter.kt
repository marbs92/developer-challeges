package com.example.testcoopelrepos.home.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.domain.local.repos.GetReposModelItem
import com.example.testcoopelrepos.R
import com.example.testcoopelrepos.databinding.ItemRepoBinding
import kotlin.properties.Delegates

class ReposAdapter(private val ownerName: String?, listItem: List<GetReposModelItem> = emptyList(), private val listener: (GetReposModelItem) -> Unit) :
    RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    var listItem: List<GetReposModelItem> by Delegates.observable(listItem) { _, _, _ -> // p,old,new
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
            holder.bind(ownerName ?:"", item, listener)
    }

    class ViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ownerName: String, itemData: GetReposModelItem, listener: (GetReposModelItem) -> Unit) {
            binding.apply {
                cardItem.setOnClickListener {
                    listener.invoke(itemData)
                }
                name.text = ownerName
                tittleRepo.text = itemData.name
                description.text = itemData.description
                language.text = itemData.language
                visibility.text = itemData.visibility
                number.text = itemData.stars.toString()
                imgProfile.load(itemData.owner.avatarUrl) {
                    listener(
                        onError = { _: ImageRequest, throwable: Throwable ->
                            Log.e("IMGERROR", throwable.message.toString())
                        })
                    crossfade(true)
                    placeholder(R.drawable.img_logo_github)
                    error(R.drawable.img_logo_github)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}