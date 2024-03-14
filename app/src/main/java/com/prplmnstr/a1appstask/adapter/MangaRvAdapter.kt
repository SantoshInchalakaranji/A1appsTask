package com.prplmnstr.a1appstask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.databinding.GridItemBinding
import com.prplmnstr.a1appstask.model.Manga
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext

class MangaRvAdapter(
    private val selectListener: (Manga) -> Unit,

) : RecyclerView.Adapter<MangaRvAdapter.ViewHolder>() {

    private val mangaList = ArrayList<Manga>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: GridItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.grid_item, parent, false)
        return ViewHolder(binding,context)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mangaList[position], selectListener)
    }

    fun setList(items: List<Manga>,contextt: Context) {
        context = contextt
        mangaList.clear()
        mangaList.addAll(items)
        notifyDataSetChanged()


    }

    inner class ViewHolder(val binding: GridItemBinding,context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
           manga: Manga,
            selectListener: (Manga) -> Unit,

        ) {

            val imageLoader = ImageLoader.Builder(context)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25)
                        .strongReferencesEnabled(true)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir)
                        .maxSizePercent(0.02)
                        .build()
                }
                .logger(DebugLogger())
                .build()

           binding.imageView.load(manga.thumb){
               memoryCachePolicy(CachePolicy.ENABLED)
               diskCachePolicy(CachePolicy.ENABLED)
               crossfade(true)
           }

         //   Picasso.get().load(manga.thumb).into(binding.imageView)

            binding.imageView.setOnClickListener {
                selectListener(manga)
            }
        }


    }


}