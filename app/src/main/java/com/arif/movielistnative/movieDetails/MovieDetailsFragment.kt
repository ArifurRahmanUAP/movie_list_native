package com.arif.movielistnative.movieDetails

import MovieDetailsResponseModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.arif.movielistnative.R
import com.arif.movielistnative.dataBase.AppTable
import com.arif.movielistnative.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var movieDetailsResponse: MovieDetailsResponseModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

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

                binding.movieDetailsRatting.text = data.voteAverage.toString() + "/10 IMDb"

                binding.movieDetailsDescription.text = data.overview

                binding.bookmarkId.setOnClickListener {
                    movieDetailsResponse?.let {

                        val appTable =AppTable(
                            id = it.id,
                            originalTitle = it.originalTitle,
                            voteAverage = it.voteAverage,
                            runtime = it.runtime,
                            overview = it.overview,
                            posterPath = it.posterPath
                        )

                        viewModel.addBookmarks(appTable)


                    }
                }

            }
        }


    }

}