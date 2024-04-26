package com.example.pages

import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pages.databinding.FragmentSharePageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharePageFragment : Fragment() {

    private lateinit var binding : FragmentSharePageBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSharePageBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        getUserProfilePicture()
        usersName()

        return binding.root
    }

    private fun getUserProfilePicture() {
        val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            db.collection("Users").document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        val imageUrl = documentSnapshot.getString("image")
                        if (imageUrl != null) {
                            // Load the image using Glide with caching disabled
                            Glide.with(requireContext())
                                .load(imageUrl)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.user_picture) // Optional placeholder image
                                .error(R.drawable.user_picture) // Optional error image
                                .into(binding.ppImg4);
                        } else {
                            Log.d("UserProfileFragment", "No image URL found in user profile")
                        }
                    } else {
                        Log.d("UserProfileFragment", "User document not found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("UserProfileFragment", "Error getting user profile picture:", exception)
                }
        }
    }
    private fun usersName(){
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        val userName = db.collection("Users").document(uid!!)
        userName.get()
            .addOnSuccessListener {
                if (it != null){
                    val uname = it.data!!["name"].toString()
                    binding.usernameShare.text = uname
                }
            }
            .addOnFailureListener {

            }
    }

    private fun share(){
        val image = binding.fullCard.drawableState
        val mBitmap = BitmapFactory.decodeResource(resources,R.drawable.logo_share)
        val intent = Intent()


    }

}