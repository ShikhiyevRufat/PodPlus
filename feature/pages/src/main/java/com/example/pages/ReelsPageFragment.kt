package com.example.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entities.Reels
import com.example.pages.adapter.ReelsAdapter
import com.example.pages.databinding.FragmentReelsPageBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReelsPageFragment : Fragment() {

    private lateinit var binding: FragmentReelsPageBinding
    private lateinit var adapter: ReelsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReelsPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        fetchReels()
    }

    private fun initRecyclerView() {
        adapter = ReelsAdapter()
        binding.recyclerReels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerReels.adapter = adapter
    }

    private fun fetchReels() {
        FirebaseFirestore.getInstance().collection("Reels")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val reelsList = mutableListOf<Reels>()
                for (document in querySnapshot) {
                    val reels = document.toObject(Reels::class.java)
                    reelsList.add(reels)
                }
                adapter.submitList(reelsList)
            }
            .addOnFailureListener { exception ->
                // Handle any errors here
            }
    }
}
