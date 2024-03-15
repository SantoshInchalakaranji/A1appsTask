package com.prplmnstr.a1appstask.view.homeScreen

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.prplmnstr.a1appstask.R

import com.prplmnstr.a1appstask.data.remote.response.toMangaList
import com.prplmnstr.a1appstask.databinding.FragmentHomeBinding
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.paging.LoaderAdapter
import com.prplmnstr.a1appstask.paging.MangaPagingAdapter
import com.prplmnstr.a1appstask.utils.Resource
import com.prplmnstr.a1appstask.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mangaRvAdapter: MangaPagingAdapter

    lateinit var loadingDialog: Dialog



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


    }

    private fun loadManga() {

        mainViewModel.mangaList.observe(viewLifecycleOwner, Observer {
            mangaRvAdapter.submitData(lifecycle, it)
        })


    }

    private fun showLoadingDialog() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false) // Optional: prevent user from dismissing by tapping outside
        progressDialog.show()
    }



    private fun AskToLoadLocalData(errorMessage:String?) {

        val snackbar = Snackbar.make(
            requireView(),
            errorMessage ?: "An error occurred",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Load local data") {

                dismiss()
            }

        }

        snackbar.show()
    }

    private fun initializeRecycler() {
        binding.recycler.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recycler.setHasFixedSize(true)
        mangaRvAdapter =
            MangaPagingAdapter {mangaClicked: Manga -> onMangaClick(mangaClicked)  }
        binding.recycler.adapter = mangaRvAdapter.withLoadStateFooter(
            footer = LoaderAdapter()
        )

    }

    private fun onMangaClick(mangaClicked: Manga) {
        mainViewModel.detailScreenManga = mangaClicked
        findNavController().navigate(R.id.action_homeFragment2_to_detailsFragment2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("TAG", "onDestroyView: HomeScreen ", )

        mainViewModel.homeFragmentState = binding.recycler.layoutManager?.onSaveInstanceState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}