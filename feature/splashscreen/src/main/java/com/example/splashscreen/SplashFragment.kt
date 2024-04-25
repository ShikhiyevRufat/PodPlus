package com.example.splashscreen

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.splashscreen.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        playLottie()
        return binding.root
    }

    private fun playLottie(){
        auth = FirebaseAuth.getInstance()
        binding.imageViewSplash.repeatCount = 0
        binding.imageViewSplash.playAnimation()
        binding.imageViewSplash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                if (auth.currentUser == null) {
                    val action = SplashFragmentDirections.splashToOnboarding()
                    findNavController().navigate(action)

                } else {
                    val action = SplashFragmentDirections.actionSplashFragmentToNavPages()
                    findNavController().navigate(action)

                }
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
    }

}
