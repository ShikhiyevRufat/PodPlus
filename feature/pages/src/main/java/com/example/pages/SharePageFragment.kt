package com.example.pages

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pages.databinding.FragmentSharePageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

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
        binding.fullCard.viewTreeObserver.addOnGlobalLayoutListener {
            // Remove the listener to avoid multiple callbacks
            binding.fullCard.viewTreeObserver.removeOnGlobalLayoutListener {}

            // Call the share function after the layout is complete
            share()
        }

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

    private fun share() {
        val view = binding.fullCard  // Reference the fullCard view

        // Check if the view is inflated and has valid dimensions
        if (view.width > 0 && view.height > 0) {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)

            // ... (Optional) Request storage permission ...

            val context = requireContext()
            val contentResolver = context.contentResolver

            // Option 1: Share using MediaStore (with permission handling)
            val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Image description", null)
            val uri = Uri.parse(path)

            // Option 2: Share using a temporary file (recommended)
            val capturedImageUri = getCapturedImageUri(context, bitmap)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, capturedImageUri) // Using temporary file

            // Start activity for result
            startActivityForResult(Intent.createChooser(shareIntent, "Share image"), SHARE_REQUEST_CODE)
        } else {
            Log.w("SharePageFragment", "fullCard view has no dimensions yet")
            // Handle case where the view doesn't have dimensions (optional)
        }
    }

    // Handle the result of the share activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SHARE_REQUEST_CODE) {
            // Handle navigation here after sharing is complete
            val action = SharePageFragmentDirections.actionSharePageFragmentToUserProfileFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val SHARE_REQUEST_CODE = 123 // Define your request code
    }



    // Function to get a Uri for the captured bitmap (using a temporary file)
    private fun getCapturedImageUri(context: Context, bitmap: Bitmap): Uri? {
        val file = File(context.cacheDir, "shared_image.png")
        val contentValues = contentValuesOf(
            MediaStore.MediaColumns.DISPLAY_NAME to file.name
        )
        val resolver = context.contentResolver
        val contentUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            val outputStream = resolver.openOutputStream(contentUri!!)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            outputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return contentUri
    }

}