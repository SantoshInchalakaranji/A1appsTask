package com.prplmnstr.a1appstask.view.homeScreen

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.databinding.FragmentHomeBinding
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.paging.LoaderAdapter
import com.prplmnstr.a1appstask.paging.MangaPagingAdapter
import com.prplmnstr.a1appstask.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mangaRvAdapter: MangaPagingAdapter

    lateinit var loadingDialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility =
            View.VISIBLE
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!isConnected()) {
            askToTurnOnInternet()
        }

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


    private fun askToTurnOnInternet() {
        val rootView = activity?.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(
            rootView!!,
            "You are not connected to the internet. Please connect.",
            Snackbar.LENGTH_LONG
        ).apply {
            setAction("Connect") {
                val settingsIntent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
                startActivity(settingsIntent)
                dismiss()
            }

        }

        snackbar.show()
    }

    private fun initializeRecycler() {
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recycler.setHasFixedSize(true)
        mangaRvAdapter =
            MangaPagingAdapter { mangaClicked: Manga -> onMangaClick(mangaClicked) }
        binding.recycler.adapter = mangaRvAdapter.withLoadStateFooter(
            footer = LoaderAdapter()
        )

    }

    private fun onMangaClick(mangaClicked: Manga) {
        mainViewModel.detailScreenManga = mangaClicked
        findNavController().navigate(R.id.action_homeFragment2_to_detailsFragment2)
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}