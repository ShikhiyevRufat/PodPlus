package com.example.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.onboarding.databinding.FragmentSignUpPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPageFragment : Fragment() {

    private lateinit var binding: FragmentSignUpPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPageBinding.inflate(layoutInflater)

        binding.login.setOnClickListener {
            val action = SignUpPageFragmentDirections.actionSignUpPageFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }

        binding.signup.setOnClickListener {
            val action = SignUpPageFragmentDirections.actionSignUpPageFragmentToNavPages()
            findNavController().navigate(action)
        }

        return binding.root
    }

}