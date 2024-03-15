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

//        val imageLoader = ImageLoader.Builder(context)
//            .memoryCache {
//                MemoryCache.Builder(context)
//                    .maxSizePercent(0.1)
//                    .strongReferencesEnabled(true)
//                    .build()
//            }
//            .diskCache {
//                DiskCache.Builder()
//                    .directory(context.cacheDir)
//                    .maxSizePercent(0.01)
//                    .build()
//            }
//            .logger(DebugLogger())
//            .build()



         if(manga!=null) {
             //Picasso.get().load(manga.thumb).into(binding.imageView)

             binding.imageView.load(manga.thumb){
               memoryCachePolicy(CachePolicy.ENABLED)
               diskCachePolicy(CachePolicy.ENABLED)
               crossfade(true)
           }

             binding.imageView.setOnClickListener {
                 selectListener(manga)
             }
         }else{
             Log.e("TAG", "bind: manga nulllll ", )
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