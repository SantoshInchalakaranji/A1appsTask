package com.prplmnstr.a1appstask.view.homeScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.adapter.MangaRvAdapter
import com.prplmnstr.a1appstask.data.remote.response.toMangaList
import com.prplmnstr.a1appstask.databinding.FragmentHomeBinding
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.utils.Resource
import com.prplmnstr.a1appstask.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mangaRvAdapter: MangaRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView: HomeScreen ", )
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()
        loadManga()

//        if(mainViewModel.homeFragmentState!=null){
//            binding.recycler.layoutManager?.onRestoreInstanceState(mainViewModel.homeFragmentState)
//        }










    }

    private fun loadManga() {

        mainViewModel.fetchManga(page = 1, nsfw = true, type = "all")

        mainViewModel.mangaList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val mangaList = resource.data!!.toMangaList()
                    mangaRvAdapter.setList(mangaList,requireContext().applicationContext)
                }
                is Resource.Error -> {

                    val errorMessage = resource.message

                }
            }
        })

    }

    private fun initializeRecycler() {
        binding.recycler.layoutManager = GridLayoutManager(requireContext(),3)
        mangaRvAdapter =
            MangaRvAdapter {mangaClicked: Manga -> infoClick(mangaClicked)  }
        binding.recycler.adapter = mangaRvAdapter

    }

    private fun infoClick(mangaClicked: Manga) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("TAG", "onDestroyView: HomeScreen ", )
        mainViewModel.homeFragmentState = binding.recycler.layoutManager?.onSaveInstanceState()
    }

}