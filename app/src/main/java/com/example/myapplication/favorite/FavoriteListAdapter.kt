package com.example.myapplication.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.Item
import com.example.myapplication.databinding.SearchItemBinding

class FavoriteListAdapter(private val data: MutableList<Item>) :
    RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    // 롱클릭 리스너 인터페이스 정의
    interface OnItemLongClickListener {
        fun onItemLongClick(item: Item)
    }

    // 롱클릭 리스너 객체 선언
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

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun getItem(position: Int): Item {
        return data[position]
    }

    inner class ViewHolder(
        val binding: SearchItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) = with(binding) {
            textSiteName.text = item.display_sitename
            textViewDate.text = item.datetime.toString()

            Glide.with(itemView.context)
                .load(item.image_url)
                .into(imageView)

            itemView.setOnLongClickListener {
                onItemLongClickListener?.onItemLongClick(item)
                true
            }
        }
    }
}