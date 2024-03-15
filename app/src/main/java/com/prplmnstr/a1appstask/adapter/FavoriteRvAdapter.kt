package com.prplmnstr.a1appstask.adapter

import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.databinding.FavoriteItemBinding
import com.prplmnstr.a1appstask.model.Favorite


class FavoriteRvAdapter(
    val requireActivity: FragmentActivity,

    private val removeMultipleFromFavoriteClickListener: (List<Favorite>) -> Unit,
    private val clickListener: (Favorite) -> Unit,


    ) : RecyclerView.Adapter<FavoriteRvAdapter.ViewHolder>(), ActionMode.Callback {


    private var multiSelection = false

    private lateinit var mActionMode: ActionMode

    private var selectedManga = mutableListOf<Favorite>()

    private var myViewHolders = arrayListOf<ViewHolder>()

    private val mangas = ArrayList<Favorite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FavoriteItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.favorite_item, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return mangas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        myViewHolders.add(holder)
        holder.bind(
            mangas[position],

            )


        /**  Long click listener for initiating multi-selection mode **/
        holder.binding.parentCard.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, mangas[position])
                true
            } else {
                multiSelection = false
                false
            }

        }

        // Click listener for handling item selection in multi-selection mode
        holder.binding.parentCard.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, mangas[position])
            } else {
                clickListener(mangas[position])
            }
        }
    }

    fun closeActionMode() {
        if (::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

    fun setList(items: List<Favorite>) {
        mangas.clear()
        mangas.addAll(items)
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: FavoriteItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            favoriteItem: Favorite,
        ) {

            binding.imageView.load(favoriteItem.thumb) {
                memoryCachePolicy(CachePolicy.ENABLED)
                diskCachePolicy(CachePolicy.ENABLED)
                crossfade(true)
            }

            binding.nameText.text = favoriteItem.title
            binding.authorText.text = favoriteItem.authors


        }


    }

    private fun applySelection(holder: ViewHolder, favoriteItem: Favorite) {
        if (selectedManga.contains(favoriteItem)) {

            if (isDarkTheme(requireActivity)) {
                changeItemStyle(holder, R.color.black, R.color.white)
            } else {
                changeItemStyle(holder, R.color.white, R.color.black)
            }
            selectedManga.remove(favoriteItem)
        } else {
            selectedManga.add(favoriteItem)
            changeItemStyle(holder, R.color.card_selection_color, R.color.orange)
        }
        applyActionModeTitle()
    }

    private fun changeItemStyle(holder: ViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.parentCard.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.parentCard.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedManga.size) {
            0 -> {
                mActionMode.finish()
            }

            1 -> {

                mActionMode.title = "${selectedManga.size} item selected"
            }

            else -> {
                mActionMode.title = "${selectedManga.size} items selected"
            }
        }

    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {


        mode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        mActionMode = mode!!
        addColorToStatusBar(R.color.orange)
        return true
    }

    private fun addColorToStatusBar(color: Int) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
        }, 200)

    }


    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_item) {
            removeMultipleFromFavoriteClickListener(selectedManga)
            multiSelection = false
            selectedManga.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        multiSelection = false
        selectedManga.clear()
        myViewHolders.forEach { holder ->
            if (isDarkTheme(requireActivity)) {
                changeItemStyle(holder, R.color.black, R.color.white)
            } else {
                changeItemStyle(holder, R.color.white, R.color.black)
            }

        }

        if (isDarkTheme(requireActivity)) {
            addColorToStatusBar(R.color.primary_dark)
        } else {
            addColorToStatusBar(R.color.primary_light)
        }


    }

    fun isDarkTheme(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }


}