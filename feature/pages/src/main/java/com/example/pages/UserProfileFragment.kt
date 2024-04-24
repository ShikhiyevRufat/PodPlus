package com.example.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entities.Users
import com.example.pages.adapter.CategoryAdapter
import com.example.pages.adapter.MyPlayListAdapter
import com.example.pages.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var myPlayListAdapter: MyPlayListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    private fun isEmpty(){
        val data = listOf("")
        if (data.isEmpty()) {
            binding.myPlaylistRecycler.setVisibility(View.VISIBLE);
        } else {
            binding.myPlaylistRecycler.setVisibility(View.GONE);
        }
        myPlayListAdapter = MyPlayListAdapter(data)
        binding.myPlaylistRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.myPlaylistRecycler.adapter = myPlayListAdapter
    }




}