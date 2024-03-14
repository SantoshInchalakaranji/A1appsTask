package com.prplmnstr.a1appstask.view.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import coil.load
import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.databinding.FragmentDetailsBinding
import com.prplmnstr.a1appstask.databinding.FragmentHomeBinding
import com.prplmnstr.a1appstask.viewmodel.MainViewModel
import kotlin.math.round


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.load(mainViewModel.detailScreenManga.thumb){

        }

        binding.favoriteButton.setOnClickListener {
            binding.favoriteButton.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.popup_animation
            )
        )
        }
    }

}