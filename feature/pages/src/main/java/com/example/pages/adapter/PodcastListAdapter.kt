package com.example.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.entities.Podcasts
import com.example.pages.ExoPlayer.MyExoplayer
import com.example.pages.PodcastListFragment
import com.example.pages.PodcastListFragmentDirections
import com.example.pages.databinding.PodcastListDesignBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class PodcastListAdapter(private var audioList: List<String>):
    RecyclerView.Adapter<PodcastListAdapter.MyViewHolder>(){

//    fun updateList(newList: List<Podcasts>) {
//        audioList = newList
//        notifyDataSetChanged()
//    }


        class MyViewHolder(private val binding: PodcastListDesignBinding): RecyclerView.ViewHolder(binding.root){
            fun bindData(audioId: String){
                FirebaseFirestore.getInstance().collection("Podcasts")
                    .document(audioId).get()
                    .addOnSuccessListener{
                        val audios = it.toObject(Podcasts :: class.java)
                        audios?.apply {
                            binding.podcastName.text = name
                            Glide.with(binding.podcastImages)
                                .load(imageurl)
                                .apply(RequestOptions().transform(RoundedCorners(32)))
                                .into(binding.podcastImages)

                            binding.root.setOnClickListener{
                                MyExoplayer.startPlayer(binding.root.context,audios)
                                val action = PodcastListFragmentDirections.actionPodcastListFragmentToPodcastPlayerFragment()
                                it.findNavController().navigate(action)

                            }
                        }

                    }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PodcastListDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(audioList[position])
    }
}