package com.example.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.onboarding.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding.login.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }

        binding.signup.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpPageFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}