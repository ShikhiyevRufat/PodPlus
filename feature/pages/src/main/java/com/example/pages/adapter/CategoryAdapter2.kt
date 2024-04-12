package com.example.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.entities.Categories
import com.example.pages.databinding.CategoryItemDesignBinding

class CategoryAdapter2(private val categoryList: List<Categories>): RecyclerView.Adapter<CategoryAdapter2.MyViewHolder>() {

    class MyViewHolder(private val binding: CategoryItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bindData(category: Categories){
                binding.podcastname.text = category.name
                binding.podcastauth.text = category.auth
                Glide.with(binding.categoryItemImg)
                    .load(category.imageurl)
                    .apply(RequestOptions().transform(RoundedCorners(32)))
                    .into(binding.categoryItemImg)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CategoryItemDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(categoryList[position])
    }
}