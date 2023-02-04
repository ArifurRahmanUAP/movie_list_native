package com.arif.movielistnative.Home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.R
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.model.ResultsItemNowShowing
import com.bumptech.glide.Glide

class NowShowingMovieAdapter(private val data: List<ResultsItemNowShowing>, private val listener: ItemOnClickListener) :
    RecyclerView.Adapter<NowShowingMovieAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_now_showing_movie, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageFirstPart = "https://image.tmdb.org/t/p/w500"

        Glide.with(holder.image).load(imageFirstPart.trim() + data[position].posterPath)
            .error(R.drawable.movie_poster).into(holder.image)
        holder.title.text = data[position].originalTitle

        holder.ratting.text = data[position].voteAverage.toString() + "/IMDb"

        holder.nowShowingView.setOnClickListener {
            listener.onClickListener("movieid", data[position].id!!)
        }

    }


    override fun getItemCount(): Int {
        return data.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nowShowingView: LinearLayout = itemView.findViewById(R.id.nowShowingView)
        val image: ImageView = itemView.findViewById(R.id.nowShowingImage)
        val title: TextView = itemView.findViewById(R.id.nowShowingTitle)
        val ratting: TextView = itemView.findViewById(R.id.nowShowingRatting)

    }
}