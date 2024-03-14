package com.prplmnstr.a1appstask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.prplmnstr.a1appstask.R
import com.prplmnstr.a1appstask.databinding.ActivityMainBinding
import com.prplmnstr.a1appstask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragment)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(navController)


//        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
//            if (it.itemId == R.id.home) {
//                navController.popBackStack(R.id.home, false)
//                true
//            }
//            else
//                NavigationUI.onNavDestinationSelected(it , navController)
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}