package com.example.myapplication.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.Item
import com.example.myapplication.databinding.SearchItemBinding
import java.text.SimpleDateFormat

class SearchListAdapter : ListAdapter<Item, SearchListAdapter.ViewHolder>(ItemDiffCallback()) {

    // 롱클릭 리스너 인터페이스 정의
    interface OnItemLongClickListener {
        fun onItemLongClick(item: Item)
    }

    private var onItemLongClickListener: OnItemLongClickListener? = null

    // 롱클릭 리스너 설정 메서드
    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: SearchItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            with(binding) {
                textSiteName.text = item.display_sitename

                val dateTime = item.datetime
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                textViewDate.text = dateFormat.format(dateTime)

                Glide.with(itemView.context)
                    .load(item.image_url)
                    .into(imageView)

                if (item.isFavorite) {
                    bookmarkIcon.visibility = View.VISIBLE
                } else {
                    bookmarkIcon.visibility = View.INVISIBLE
                }

                itemView.setOnLongClickListener {
                    onItemLongClickListener?.onItemLongClick(item)
                    bookmarkIcon.visibility = View.VISIBLE
                    true
                }
            }
        }
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.image_url == newItem.image_url // 아이템의 고유 식별자를 비교
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem // 아이템의 내용을 비교
    }
}
