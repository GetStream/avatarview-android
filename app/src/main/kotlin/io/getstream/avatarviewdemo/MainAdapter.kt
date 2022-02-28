package io.getstream.avatarviewdemo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.getstream.avatarview.coil.loadImage
import io.getstream.avatarviewdemo.databinding.LayoutMainAdapterBinding

class MainAdapter(
    private val items: List<MainCat>
) : ListAdapter<MainCat, MainAdapter.MainViewHolder>(diffcallback) {

    init {
        submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = LayoutMainAdapterBinding.inflate(LayoutInflater.from(parent.context))
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.e("Test", "onBindViewHolder: ${position}")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class MainViewHolder(
        private val binding: LayoutMainAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mainCat: MainCat) {
            binding.avatarView.loadImage(mainCat.url)
            binding.name.text = mainCat.name
        }
    }

    companion object {
        val diffcallback = object : DiffUtil.ItemCallback<MainCat>() {
            override fun areItemsTheSame(oldItem: MainCat, newItem: MainCat): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MainCat, newItem: MainCat): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
