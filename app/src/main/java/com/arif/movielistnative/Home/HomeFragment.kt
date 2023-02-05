package com.arif.movielistnative.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arif.movielistnative.*
import com.arif.movielistnative.Home.adapter.NowShowingMovieAdapter
import com.arif.movielistnative.Home.adapter.PopularMoviesAdapter
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.databinding.FragmentHomeBinding
import com.arif.movielistnative.model.ResultsItemNowShowing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemOnClickListener {
    val resultsTotalPopular = mutableListOf<ResultsItem>()
    val resultsTotalNowShowing = mutableListOf<ResultsItemNowShowing>()
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var isLoading: Boolean = false

    lateinit var popularMoviesAdapter: PopularMoviesAdapter
    lateinit var nowShowingMovieAdapter: NowShowingMovieAdapter
    private var pageNumPopularMovies = 1
    private var pageNumNowShowingMovies = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(this.context)
        binding.drawerIcon.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }
        getMovies()

        binding.popularRecView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    pageNumPopularMovies++
                    getPaginationPopularMovie()
                }
            }
        })


        binding.nowShowingRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollHorizontally(1)) {
                    pageNumNowShowingMovies++
                    getPaginationNowShowingMovie()
                }
            }

        })


    }

    private fun getMovies() {

        viewModel.callNowShowingMovieList(pageNumNowShowingMovies)

        viewModel.nowShowingList.observe(viewLifecycleOwner) { data ->
            nowShowingMovieAdapter =
                NowShowingMovieAdapter(data?.results as List<ResultsItemNowShowing>, this)
            binding.nowShowingRecyclerView.adapter = nowShowingMovieAdapter

        }

        viewModel.callPopularMovieList(pageNumPopularMovies)

        viewModel.popularMovieList.observe(viewLifecycleOwner) { data ->
            resultsTotalPopular.addAll(data?.results!!)
            popularMoviesAdapter =
                PopularMoviesAdapter(data?.results as List<ResultsItem>, this)
            binding.popularRecView.adapter = popularMoviesAdapter

        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

    }


    private fun getPaginationPopularMovie() {

        viewModel.callPopularMovieList(pageNumPopularMovies)

        viewModel.popularMovieList.observe(viewLifecycleOwner) { data ->

            resultsTotalPopular.addAll(data?.results!!)

            popularMoviesAdapter =
                PopularMoviesAdapter(resultsTotalPopular, this)
            binding.popularRecView.adapter = popularMoviesAdapter
            popularMoviesAdapter.notifyDataSetChanged()

        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun getPaginationNowShowingMovie() {

        viewModel.callNowShowingMovieList(pageNumNowShowingMovies)

        viewModel.nowShowingList.observe(viewLifecycleOwner) { data ->
            resultsTotalNowShowing.addAll(data?.results as List<ResultsItemNowShowing>)
            nowShowingMovieAdapter =
                NowShowingMovieAdapter(resultsTotalNowShowing, this)
            binding.nowShowingRecyclerView.adapter = nowShowingMovieAdapter
            nowShowingMovieAdapter.notifyDataSetChanged()

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


