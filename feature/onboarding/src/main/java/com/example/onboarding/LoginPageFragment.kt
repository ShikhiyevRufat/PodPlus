package com.example.onboarding

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.onboarding.databinding.FragmentLoginPageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPageFragment : Fragment() {

    private lateinit var binding: FragmentLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPageBinding.inflate(layoutInflater)

        binding.goSignupPage.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToSignUpPageFragment()
            findNavController().navigate(action)
        }

        binding.login.setOnClickListener {
            register()
        }

        return binding.root
    }

    private fun register() {
        val email = binding.emailaddressSign.text.toString()
        val password = binding.passwordSign.text.toString()

        // Check if any of the fields is empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context,"Fields is empty!", Toast.LENGTH_SHORT).show()
            return
        }
        

        val firebaseAuth = Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val action = LoginPageFragmentDirections.actionLoginPageFragmentToNavPages()
                findNavController().navigate(action)
            }.addOnFailureListener { exception ->
                (exception as? FirebaseAuthException)?.errorCode?.let { errorCode ->
                    Toast.makeText(context,"This account does not exist!",Toast.LENGTH_SHORT).show()
                }
            }
    }

    


}