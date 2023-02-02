package com.arif.movielistnative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arif.movielistnative.adapter.NowShowingMovieAdapter
import com.arif.movielistnative.adapter.PopularMoviesAdapter
import com.arif.movielistnative.databinding.FragmentHomeBinding
import com.arif.movielistnative.listener.ItemOnClickListener
import com.arif.movielistnative.model.ResultsItemNowShowing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemOnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawerIcon.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }
        navigatinView()
    }

    private fun navigatinView() {


        viewModel.callNowShowingMovieList()

        viewModel.nowShowingMovieListLiveData.observe(viewLifecycleOwner) { data ->
            data?.let { it ->
                val nowShowingMovieAdapter =
                    NowShowingMovieAdapter(it.results as List<ResultsItemNowShowing>, this)
                binding.nowShowingRecyclerView.adapter = nowShowingMovieAdapter
            }
        }


        viewModel.callPopularMovieList()

        viewModel.popularMovieList.observe(viewLifecycleOwner) { data ->
            data?.let { it ->
                val popularMoviesAdapter =
                    PopularMoviesAdapter(it.results as List<ResultsItem>)
                binding.popularRecView.adapter = popularMoviesAdapter
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