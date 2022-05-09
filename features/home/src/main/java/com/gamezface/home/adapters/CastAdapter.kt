package com.gamezface.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamezface.domain.cast.entity.Cast
import com.gamezface.uicommon.databinding.ItemCastBinding
import com.gamezface.uicommon.extensions.loadImage
import com.gamezface.uicommon.extensions.show

class CastAdapter(
    private val onClick: (Cast) -> Unit
) : ListAdapter<Cast, CastAdapter.CastViewHolder>(TaskDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(
            ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindData(getItem(position), onClick)
    }

    inner class CastViewHolder(
        private val binding: ItemCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Cast, onClick: (Cast) -> Unit) {
            with(binding) {
                root.setOnClickListener { onClick.invoke(item) }
                imageViewAvatar.loadImage(root, item.getImageUrl())
                textViewName.text = item.person?.name
                item.character?.name.takeUnless { it.isNullOrEmpty() }.run {
                    textViewCharacterName.text = this
                    textViewCharacterName.show()
                }
            }
        }
    }

    private class TaskDiffCallBack : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.person?.id == newItem.person?.id && oldItem.character?.id == newItem.character?.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.person == newItem.person && oldItem.character == newItem.character
        }
    }
}