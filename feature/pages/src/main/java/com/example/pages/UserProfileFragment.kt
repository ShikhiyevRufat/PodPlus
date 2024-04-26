package com.example.pages

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pages.adapter.MyPlayListAdapter
import com.example.pages.databinding.FragmentUserProfileBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var myPlayListAdapter: MyPlayListAdapter
    private lateinit var firebaseAuth : FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private var storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                binding.ppImg.setImageURI(uri)
                uploadImgFireStore(uri) // Call the upload function here
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        usersName()
        logOut()
        editProfile()
        getUserProfilePicture()
        share()


        return binding.root
    }

    private fun isEmpty(){
        val data = listOf("")
        if (data.isEmpty()) {
            binding.myPlaylistRecycler.visibility = View.VISIBLE;
        } else {
            binding.myPlaylistRecycler.visibility = View.GONE;
        }
        myPlayListAdapter = MyPlayListAdapter(data)
        binding.myPlaylistRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.myPlaylistRecycler.adapter = myPlayListAdapter
    }

    private fun usersName(){
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        val userName = db.collection("Users").document(uid!!)
        userName.get()
            .addOnSuccessListener {
                if (it != null){
                    val uname = it.data!!["name"].toString()
                    binding.userProfileName.text = uname
                }
            }
            .addOnFailureListener {

            }
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
                                .into(binding.ppImg);
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


    private fun uploadImgFireStore(uri: Uri?) {
        val uid = firebaseAuth.currentUser?.uid
        if (uri != null) {
            val imgRef = storage.reference.child("images/" + uid + "/" + uri.lastPathSegment) // Include user ID in path
            imgRef.putFile(uri)
                .addOnSuccessListener {
                    // Get the download URL after successful upload
                    imgRef.downloadUrl.addOnSuccessListener { url ->
                        val imageUri = Uri.parse(url.toString()) // Attempt to convert URL to Uri (might not always work)
                        binding.ppImg.setImageURI(imageUri) // Set the image on ImageView (optional)
                        postToFireStore(url.toString()) // Update Firestore with the URL
                    }
                }
                .addOnFailureListener {
                    // Handle upload failure
                    Log.e("UserProfileFragment", "Image upload failed:", it)
                }
        } else {
            // Handle the case where no image is selected
            Log.d("UserProfileFragment", "No image selected for upload")
        }

    }


    private fun logOut(){
        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().finish()
        }
    }

    private fun postToFireStore(imageUrl: String) {
        val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            // Update the user's collection in Firestore with the downloaded image URL
            Firebase.firestore.collection("Users")
                .document(uid)
                .update("image", imageUrl)
                .addOnSuccessListener {
                    Log.d("UserProfileFragment", "User profile picture updated successfully")
                }
                .addOnFailureListener {
                    Log.e("UserProfileFragment", "Failed to update user profile picture:", it)
                }
        }
    }

    private fun editProfile(){
        binding.editPp.setOnClickListener {
            val view = View.inflate(requireActivity(), R.layout.edit_profile, null)
            val builder = AlertDialog.Builder(requireActivity())
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val btn: ShapeableImageView = dialog.findViewById(R.id.pp_img2)
            val btn2: AppCompatButton = dialog.findViewById(R.id.save_data)


            btn.setOnClickListener {
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            }

            btn2.setOnClickListener {
                val txt: EditText = dialog.findViewById(R.id.username_edt)
                val updateMap = mapOf(
                    "name" to txt.text.toString()
                )

                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                db.collection("Users").document(userId).update(updateMap)

                Toast.makeText(requireActivity(),"Data Updated",Toast.LENGTH_SHORT).show()


            }



        }
    }

    private fun share(){
        binding.share.setOnClickListener {
            val action = UserProfileFragmentDirections.actionUserProfileFragmentToSharePageFragment()
            findNavController().navigate(action)
        }
    }

}