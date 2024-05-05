package com.example.onboarding

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.onboarding.databinding.FragmentLoginPageBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission
import java.util.Arrays

@AndroidEntryPoint
class LoginPageFragment : Fragment() {

    private lateinit var binding: FragmentLoginPageBinding
    private lateinit var auth: FirebaseAuth
    private var callbackManager: CallbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        binding.goSignupPage.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToSignUpPageFragment()
            findNavController().navigate(action)
        }

        binding.login.setOnClickListener {
            register()
        }

        binding.resetPassword.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToForgetPasswordFragment()
            findNavController().navigate(action)
        }

        binding.switchRemember.setOnClickListener {
            Toast.makeText(context,"Password saved!", Toast.LENGTH_SHORT).show()
        }

        binding.facebookBtn.setOnClickListener {
            facebook_login()
        }

        binding.googleBtn.setOnClickListener {
            google_login()
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

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    fun google_login(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val signIntent = mGoogleSignInClient.signInIntent
        googleLauncher.launch(signIntent)
    }

    private val googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result : androidx.activity.result.ActivityResult ->
        result.data?.let {
            val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(it)
            try {
                val account = signInAccountTask.getResult(ApiException::class.java)
                // Extract email from the signed-in account
                val userEmail = account?.email
                println("User email: $userEmail")
                userEmail?.let {
                    binding.emailaddressSign.setText(it)
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    fun facebook_login(){
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            },
        )
        LoginManager.getInstance().logInWithReadPermissions(this,callbackManager, PERMISSION)
    }

    val PERMISSION = Arrays.asList("public_profile","email")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
//            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }



    


}