package com.example.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.entities.Categories
import com.example.entities.Podcasts
import com.example.entities.Users
import com.example.pages.adapter.CategoryAdapter
import com.example.pages.adapter.CategoryAdapter2
import com.example.pages.adapter.PodcastsAdapter
import com.example.pages.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryAdapter2: CategoryAdapter2
    private lateinit var podcastsAdapter: PodcastsAdapter
    private lateinit var users: Users
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = Firebase.firestore
    private var progressBar: ProgressBar? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchView: SearchView
    private lateinit var podcastsList: List<Podcasts>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        progressBar = binding.progressBar
        swipeRefreshLayout = binding.swipeRefresh
        searchView = binding.searchBar

        // Show progress bar for 3 seconds initially
        progressBar?.visibility = View.VISIBLE
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            progressBar?.visibility = View.GONE
            fetchData()
        }, 3000)

        // Setup swipe refresh listener
        swipeRefreshLayout?.setOnRefreshListener {
            fetchData()
            swipeRefreshLayout?.isRefreshing = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterPodcasts(newText)
                return true
            }
        })

        return binding.root
    }

    private fun fetchData() {
        usersName()
        categories()
        categories2()
        podcasts()
        getUserProfilePicture()
    }

    private fun categories(){
        FirebaseFirestore.getInstance().collection("MostPopular")
            .get().addOnSuccessListener {
                val categoryList = it.toObjects(Categories :: class.java)
                categoryToRecycleView(categoryList)
            }
    }

    private fun categoryToRecycleView(categoryLists : List<Categories>){
        categoryAdapter = CategoryAdapter(categoryLists)
        binding.mostpopularrecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.mostpopularrecycler.adapter = categoryAdapter
    }

    private fun categories2(){
        FirebaseFirestore.getInstance().collection("TheNewest")
            .get().addOnSuccessListener {
                val categoryList2 = it.toObjects(Categories :: class.java)
                categoryToRecycleView2(categoryList2)
            }
    }

    private fun categoryToRecycleView2(categoryLists2: List<Categories>){
        categoryAdapter2 = CategoryAdapter2(categoryLists2)
        binding.thenewestrecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.thenewestrecycler.adapter = categoryAdapter2
    }

    private fun podcasts(){
        FirebaseFirestore.getInstance().collection("Podcasts")
            .get().addOnSuccessListener {
                podcastsList = it.toObjects(Podcasts::class.java)
                podcastsRecycleView(podcastsList)
            }
    }

    private fun filterPodcasts(query: String) {
        if (::podcastsList.isInitialized) {
            val filteredPodcasts = podcastsList.filter { podcast ->
                podcast.name.contains(query, ignoreCase = true) // You can change this condition based on your search criteria
            }
            podcastsAdapter.setData(filteredPodcasts)
        } else {
            Log.e("HomeFragment", "Podcasts list not initialized")
        }
    }

    private fun podcastsRecycleView(podcastsLists: List<Podcasts>) {
        podcastsAdapter = PodcastsAdapter(podcastsLists)
        binding.podcastRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.podcastRecycler.adapter = podcastsAdapter
    }

    private fun usersName() {
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        val userName = db.collection("Users").document(uid!!)
        userName.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val uname = documentSnapshot.data?.get("name").toString()
                    binding.usernameHome.text = uname
                } else {
                    Log.d("HomeFragment", "User document does not exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error getting user document", exception)
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
                                .into(binding.ppImg3);
                        } else {
                            Log.d("UserProfileFragment", "No image URL found in user profile")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("UserProfileFragment", "Error getting user profile picture:", exception)
                }
        }
    }



}

