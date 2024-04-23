package com.example.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.onboarding.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgetPasswordBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        binding.send.setOnClickListener {
            val editPassword = binding.emailaddressresetSign.text

            auth.sendPasswordResetEmail(editPassword.toString())
                .addOnSuccessListener {
                    val action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToLoginPageFragment()
                    findNavController().navigate(action)
                    Toast.makeText(requireActivity(),"Please check your Email!",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireActivity(),"The operation was not successful!",Toast.LENGTH_SHORT).show()
                }
        }

        return binding.root
    }

}