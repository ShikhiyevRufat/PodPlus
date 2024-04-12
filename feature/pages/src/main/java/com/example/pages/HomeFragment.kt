package com.example.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entities.Categories
import com.example.entities.Podcasts
import com.example.pages.adapter.CategoryAdapter
import com.example.pages.adapter.CategoryAdapter2
import com.example.pages.adapter.PodcastsAdapter
import com.example.pages.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryAdapter2: CategoryAdapter2
    private lateinit var podcastsAdapter: PodcastsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
//        requireActivity().onBackPressedDispatcher.addCallback(this){
//            handleOnBackPressed()
//        }
        categories()
        categories2()
        podcasts()

        return binding.root
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
                val podcastsList = it.toObjects(Podcasts :: class.java)
                podcastsRecycleView(podcastsList)
            }
    }

    private fun podcastsRecycleView(podcastsLists: List<Podcasts>){
        podcastsAdapter = PodcastsAdapter(podcastsLists)
        binding.podcastRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.podcastRecycler.adapter = podcastsAdapter
    }




}