package com.prplmnstr.a1appstask.view.favoriteScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prplmnstr.a1appstask.adapter.FavoriteRvAdapter
import com.prplmnstr.a1appstask.databinding.FragmentFavoriteBinding
import com.prplmnstr.a1appstask.model.Favorite
import com.prplmnstr.a1appstask.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var favoriteRvAdapter: FavoriteRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()
        loadFavorites()
    }

    private fun loadFavorites() {
        mainViewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            favoriteRvAdapter.setList(it)
        })
    }

    private fun initializeRecycler() {
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.setHasFixedSize(true)
        favoriteRvAdapter = FavoriteRvAdapter(
            requireActivity(),
            { selectedMangas: List<Favorite> ->
                removeMultipleFromFavorites(
                    selectedMangas
                )
            },
            { selectedManga: Favorite -> itemClick(selectedManga) },
        )
        binding.recycler.adapter = favoriteRvAdapter

    }

    private fun itemClick(selectedManga: Favorite) {

    }

    private fun removeMultipleFromFavorites(selectedMangas: List<Favorite>) {

        for (item in selectedMangas) {
            mainViewModel.deleteFavorite(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        favoriteRvAdapter.closeActionMode()
    }

}