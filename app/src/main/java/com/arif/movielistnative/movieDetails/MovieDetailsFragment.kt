package com.arif.movielistnative.movieDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arif.movielistnative.R
import com.arif.movielistnative.dataBase.AppTable
import com.arif.movielistnative.databinding.FragmentDetailsBinding
import com.arif.movielistnative.model.GenresItem
import com.arif.movielistnative.model.MovieDetailsResponseModel
import com.arif.movielistnative.movieDetails.adapter.GenreListAdapter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var movieDetailsResponse: MovieDetailsResponseModel? = null
    private val isClick: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId")

        if (movieId != null)
            viewModel.callMovieDetails(movieId)

        viewModel.detailesMovieLiveData.observe(viewLifecycleOwner) { data ->
            data?.let { movieResponse ->
                movieDetailsResponse = movieResponse

                //movie picture load code
                val imageFirstPart = "https://image.tmdb.org/t/p/w500"
                val imageApiPart = data.posterPath
                val image = imageFirstPart.trim() + imageApiPart

                Glide.with(binding.detailsCoverId)
                    .load(image)
                    .error(R.drawable.movie_poster)
                    .into(binding.detailsCoverId)

                binding.movieDetailsTitle.text = data.originalTitle

                binding.movieDetailsRatting.text = "${data.voteAverage}/10IMDb"

                val list: List<String> = listOf()

                val genreListAdapter =
                    GenreListAdapter(movieResponse.genres as List<GenresItem>, list)
                binding.genresListId.adapter = genreListAdapter

                binding.detailsLengthId.text = minuteToTime(data.runtime!!)

                binding.detailsLanguageId.text = data.spokenLanguages?.get(0)?.name.toString()

                binding.detailsRattingId.text = if (data.adult!!) "R" else "PG-13"

                binding.movieDetailsDescription.text = data.overview

                if (data.isBookmarked) {
                    binding.bookmarkId.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmarked))

                } else {
                    binding.bookmarkId.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmark))
                }

                binding.bookmarkId.setOnClickListener {

                    if (data.isBookmarked) movieDetailsResponse?.let {
                        viewModel.deleteBookmarks(data.id)
                        Toasty.warning(
                            this.requireContext(),
                            "${it.originalTitle} Removed from bookmark",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                        binding.bookmarkId.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmark))
                    } else movieDetailsResponse?.let {

                        val genres: StringBuilder = StringBuilder()

                        for (lists in it.genres!!) {
                            genres.append(lists!!.name + ",")
                        }

                        val appTable = AppTable(
                            id = it.id,
                            originalTitle = it.originalTitle,
                            voteAverage = it.voteAverage,
                            runtime = minuteToTime(it.runtime!!),
                            overview = it.overview,
                            posterPath = it.posterPath,
                            genres = genres.substring(0, genres.length - 1)
                        )
                        viewModel.addBookmarks(appTable)
                        Toasty.success(
                            this.requireContext(),
                            "${it.originalTitle} added to Bookmark",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()

                        binding.bookmarkId.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmarked))
                    }
                    data.isBookmarked = !data.isBookmarked
                }
            }
        }
    }

    fun minuteToTime(minute: Int): String? {
        var minutes = minute
        val hour = minute / 60
        minutes %= 60
        return (if (hour < 10) "$hour" else hour).toString() + "h " + (if (minute < 10) "0$minutes" else minutes) + "m"
    }
}