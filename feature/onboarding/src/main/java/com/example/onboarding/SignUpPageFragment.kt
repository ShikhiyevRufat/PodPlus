package com.example.onboarding

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.entities.Users
import com.example.onboarding.databinding.FragmentSignUpPageBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import java.nio.file.attribute.UserPrincipalLookupService

@AndroidEntryPoint
class SignUpPageFragment : Fragment() {

    private lateinit var binding: FragmentSignUpPageBinding
    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private lateinit var users: Users
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPageBinding.inflate(inflater)

        binding.login.setOnClickListener {
            val action = SignUpPageFragmentDirections.actionSignUpPageFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }

        helperText()

        binding.signup.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.emailaddressSign.text.toString()
            val password = binding.passwordSign.text.toString()
            register(username,email,password)
        }



        return binding.root
    }


    fun register(username: String,email: String, password: String){
        val firebaseAuth = Firebase.auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {authResult ->
                val user = authResult.user
                addExtraUserInfo(firebaseAuth.currentUser?.uid ?: "", username, email)
                openpage()
            }.addOnFailureListener { exception ->
                (exception as? FirebaseAuthException)?.errorCode?.let { errorCode ->
                    Toast.makeText(context,"Account not been successful!",Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun openpage(){
        val action = SignUpPageFragmentDirections.actionSignUpPageFragmentToNavPages()
        findNavController().navigate(action)
    }

    private fun addExtraUserInfo(userId: String, usernames: String, emails:String) {
        val userData = Users(userId,usernames,emails)

        val userDocument = firestore.collection("Users").document(userId)
        userDocument.set(userData)
            .addOnSuccessListener {
                // User data saved successfully
            }
            .addOnFailureListener {
                // Error occurred while saving user data
            }
    }

    private fun helperText(){
        binding.emailaddressSign.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.emailaddressSignLayout.helperText = valid_email()

            }
        }

        binding.passwordSign.setOnFocusChangeListener{_, focused->
            if(!focused){
                binding.passwordSignLayout.helperText = valid_password()
            }
        }

        binding.confirmPassword.setOnFocusChangeListener{_, focused->
            if(!focused){
                binding.confirmPasswordLayout.helperText = valid_confirm_password()
            }
        }

    }

    private fun valid_password() : String? {

        val passwordtext = binding.passwordSign.text.toString()

        return when {
            passwordtext.length < 8 -> "* * Minimum character 8!"
            else -> ""
        }

    }

    private fun valid_confirm_password() : String? {

        val confirmpasswordtext = binding.confirmPassword.text.toString()

        if (confirmpasswordtext != binding.passwordSign.text.toString()){
            return "* Password is valid!"
        }
        return ""
    }
    private fun valid_email() : String? {

        val emailText = binding.emailaddressSign.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "* Invalid Email!"
        }
        return ""
    }

}