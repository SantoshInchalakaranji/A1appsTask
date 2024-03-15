package com.prplmnstr.a1appstask.paging


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import coil.load

import coil.request.CachePolicy

import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.databinding.GridItemBinding
import com.prplmnstr.a1appstask.model.Manga


class MangaPagingAdapter(
    private val selectListener: (Manga) -> Unit,
): PagingDataAdapter<Manga, MangaPagingAdapter.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), selectListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: GridItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.grid_item, parent, false)
        return ViewHolder(binding)
    }




 class ViewHolder(val binding: GridItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

     fun bind(
         manga: Manga?,
         selectListener: (Manga) -> Unit,

         ) {



         if(manga!=null) {


             binding.imageView.load(manga.thumb){
               memoryCachePolicy(CachePolicy.ENABLED)
               diskCachePolicy(CachePolicy.ENABLED)
               crossfade(true)
           }

             binding.imageView.setOnClickListener {
                 selectListener(manga)
             }
         }
     }
 }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Manga>() {
            override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean {
                return oldItem == newItem
            }

        }
    }


}