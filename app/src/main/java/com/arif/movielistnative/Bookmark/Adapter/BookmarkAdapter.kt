package com.arif.movielistnative.Bookmark.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.R
import com.arif.movielistnative.Utill.listener.DeleteListener
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.dataBase.AppTable
import com.bumptech.glide.Glide

class BookmarkAdapter(
    private val arrayList: ArrayList<AppTable>,
    private val deleteListener: DeleteListener, private val clickListener:  ItemOnClickListener
) :
    RecyclerView.Adapter<BookmarkAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: CardView = itemView.findViewById(R.id.bookmark_layout_id)
        val imageView: ImageView = itemView.findViewById(R.id.bookmark_delete_id)
        val poster: ImageView = itemView.findViewById(R.id.bookmark_poster_id)
        val title: TextView = itemView.findViewById(R.id.bookmark_title_id)
        val ratting: TextView = itemView.findViewById(R.id.bookmark_rating_id)
        val runtime: TextView = itemView.findViewById(R.id.bookmark_runTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_bookmark, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = arrayList[position]

        holder.title.text = item.originalTitle
        holder.ratting.text = item.voteAverage.toString()
        holder.runtime.text = item.runtime?.let { item.runtime }

        val imageFirstPart = "https://image.tmdb.org/t/p/w500"
        val imageApiPart = item.posterPath.toString()
        val image = imageFirstPart.trim() + imageApiPart.trim()

        Glide.with(holder.poster.context)
            .load(image)
            .error(R.drawable.movie_poster)
            .into(holder.poster)

        holder.imageView.setOnClickListener {
            deleteListener.onDelete(item)
        }

        holder.layout.setOnClickListener {
            clickListener.onClickListener("movieId", item.id!!)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}