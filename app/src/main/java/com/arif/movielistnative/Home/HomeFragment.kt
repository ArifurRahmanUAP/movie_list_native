package com.arif.movielistnative.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.*
import com.arif.movielistnative.Home.adapter.NowShowingMovieAdapter
import com.arif.movielistnative.Home.adapter.PopularMoviesAdapter
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.dataBase.GenresTable
import com.arif.movielistnative.databinding.FragmentHomeBinding
import com.arif.movielistnative.model.Results
import com.arif.movielistnative.model.ResultsPopular
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemOnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    lateinit var popularMoviesAdapter: PopularMoviesAdapter
    lateinit var nowShowingMovieAdapter: NowShowingMovieAdapter
    private var pageNumPopularMovies = 1
    var list: MutableList<GenresTable> = mutableListOf()
    private var pageNumNowShowingMovies = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawerIcon.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }
        viewModel.callNowShowingMovieList(1)
        viewModel.callGenresList()
        viewModel.getGenresNameById()
        viewModel.callPopularMovieList(1)
        initViews()
        getMovies()
    }

    private fun initViews() {

        nowShowingMovieAdapter = NowShowingMovieAdapter(this)
        binding.nowShowingRecyclerView.adapter = nowShowingMovieAdapter

        popularMoviesAdapter = PopularMoviesAdapter(this)
        binding.popularRecView.adapter = popularMoviesAdapter
    }

    private fun getMovies() {

        binding.nowShowingRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollHorizontally(1)) {
                    pageNumNowShowingMovies++
                    viewModel.callNowShowingMovieList(pageNumNowShowingMovies)
                }
            }
        })

        viewModel.genresNameById.observe(viewLifecycleOwner) { data ->
            list.clear()
            list.addAll(data as List<GenresTable>)
        }

        viewModel.nowShowingList.observe(viewLifecycleOwner) { data ->

            if (pageNumNowShowingMovies == 1) {
                nowShowingMovieAdapter.initLoad(data?.results as List<Results>)
            } else {
                nowShowingMovieAdapter.pagingLoad(data?.results as List<Results>)
            }
        }

        binding.popularRecView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    pageNumPopularMovies++
                    viewModel.callPopularMovieList(pageNumPopularMovies)
                }
            }
        })

        viewModel.popularMovieList.observe(viewLifecycleOwner) { data ->

            if (pageNumPopularMovies == 1) {
                popularMoviesAdapter.initLoad(data?.results as List<ResultsPopular>, list)
            } else {
                popularMoviesAdapter.pagingLoad(data?.results as List<ResultsPopular>, list)
            }
        }

        viewModel.genresListLiveData.observe(viewLifecycleOwner) { data ->
            var genresTable: GenresTable
            for (s in data?.genres!!) {
                genresTable = GenresTable(
                    id = s?.id, name = s?.name
                )
                viewModel.addGenres(genresTable)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onClickListener(name: String, value: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", value)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }
}


