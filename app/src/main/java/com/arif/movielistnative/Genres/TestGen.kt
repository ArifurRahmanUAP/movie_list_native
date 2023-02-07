package com.arif.movielistnative.Genres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.R

class TestGen (
    private val list: List<String>
) :
    RecyclerView.Adapter<TestGen.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genresName: TextView = itemView.findViewById(R.id.genresId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_genres_list, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.genresName.text = list[position]
    }

    override fun getItemCount(): Int {
      return list.size
    }
}