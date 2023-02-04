package com.arif.movielistnative.movieDetails.adapter

import GenresItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.R

class GenreListAdapter(
    private val arrayList: List<GenresItem>
) :
    RecyclerView.Adapter<GenreListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genresName: TextView = itemView.findViewById(R.id.genresId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_genres_list, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = arrayList[position]

        holder.genresName.text = item.name
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}