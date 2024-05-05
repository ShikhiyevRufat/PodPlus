package com.example.podplus

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.podplus.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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

//        printHashKey(baseContext)


        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == com.example.pages.R.id.homeFragment ||
                destination.id == com.example.pages.R.id.reelsPageFragment ||
                destination.id == com.example.pages.R.id.userProfileFragment){
                binding.bottomNav.visibility = View.VISIBLE
            }
            else{
                binding.bottomNav.visibility = View.GONE
            }

            }

    }

//    fun printHashKey(pContext: Context) {
//        try {
//            val info: PackageInfo = pContext.getPackageManager()
//                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey: String = String(android.util.Base64.encode(md.digest(), 0))
//                Log.i("printHashKey", "printHashKey() Hash Key: $hashKey")
//            }
//        } catch (e: NoSuchAlgorithmException) {
//            Log.e("TAG", "printHashKey()", e)
//        } catch (e: Exception) {
//            Log.e("TAG", "printHashKey()", e)
//        }
//    }


}