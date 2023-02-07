package com.arif.movielistnative.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.Genres.TestGen
import com.arif.movielistnative.R
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.dataBase.GenresTable
import com.arif.movielistnative.model.ResultsPopular
import com.bumptech.glide.Glide

class PopularMoviesAdapter(
    private val listener: ItemOnClickListener
) :
    RecyclerView.Adapter<PopularMoviesAdapter.MyViewHolder>() {
    private var data: MutableList<ResultsPopular> = mutableListOf()
    private var genre: MutableList<GenresTable> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_popular_movie_showing, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val s: MutableList<String> = mutableListOf()

        val imageFirstPart = "https://image.tmdb.org/t/p/w500"

        Glide.with(holder.image).load(imageFirstPart.trim() + data[position].posterPath)
            .error(R.drawable.movie_poster).into(holder.image)
        holder.title.text = data[position].originalTitle
//        holder.ratting.text = data[position].voteAverage.toString() + "/IMDb"
        holder.layout.setOnClickListener {
            listener.onClickListener("movieid", data[position].id!!)
        }

        for (datas in data[position].genreIds!!) {
            for (gen in genre) {
                if (datas == gen.id) {
                    s.add(gen.name.toString())
                }
            }
        }

        val genreListAdapter = TestGen(s)
        holder.genresReview.adapter = genreListAdapter
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun initLoad(list: List<ResultsPopular>, list1: MutableList<GenresTable>) {
        genre.clear()
        genre.addAll(list1)
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun pagingLoad(list: List<ResultsPopular>, list1: MutableList<GenresTable>) {
        data.addAll(list)
        genre.clear()
        genre.addAll(list1)
        notifyItemRangeInserted(itemCount, list.size)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: CardView = itemView.findViewById(R.id.popular_movie_layout)
        val image: ImageView = itemView.findViewById(R.id.popularImage)
        val title: TextView = itemView.findViewById(R.id.popularMovieTitle)
        val genresReview: RecyclerView = itemView.findViewById(R.id.genresPopularRecycler)
    }
}