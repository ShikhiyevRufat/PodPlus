package com.example.pages

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entities.Users
import com.example.pages.adapter.CategoryAdapter
import com.example.pages.adapter.MyPlayListAdapter
import com.example.pages.databinding.FragmentUserProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var myPlayListAdapter: MyPlayListAdapter
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var currentUserId: String
    private lateinit var profileUserId: String
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        usersName()
        logOut()
        editProfile()

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

    private fun logOut(){
        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().finish()
        }
    }

    private fun editProfile(){
        val buttonAddImg = binding.root.findViewById<View>(R.id.pp_img2)
        val buttonSave = binding.root.findViewById<View>(R.id.save_data)
        binding.editPp.setOnClickListener {
            val view = View.inflate(requireActivity(),R.layout.edit_profile,null)
            val builder = AlertDialog.Builder(requireActivity())
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            buttonSave?.setOnClickListener {
//////                photoUpload()
           }

            currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!

            buttonAddImg?.setOnClickListener {
                val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: $uri")
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                println("***")
            }

        }

    }

}