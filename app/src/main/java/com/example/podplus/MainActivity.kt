package com.example.podplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.podplus.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationApp) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)


        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == com.example.onboarding.R.id.loginPageFragment ||
                destination.id == com.example.onboarding.R.id.signUpPageFragment ||
                destination.id == com.example.splashscreen.R.id.splashFragment ||
                destination.id == com.example.onboarding.R.id.signUpFragment){
                binding.bottomNav.visibility = View.GONE
            }
            else{
                binding.bottomNav.visibility = View.VISIBLE
            }

            }

    }

}