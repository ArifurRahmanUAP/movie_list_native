package com.arif.movielistnative.Home

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arif.movielistnative.MainActivity
import com.arif.movielistnative.R
import com.arif.movielistnative.ResultsItem
import com.arif.movielistnative.Home.adapter.NowShowingMovieAdapter
import com.arif.movielistnative.Home.adapter.PopularMoviesAdapter
import com.arif.movielistnative.databinding.FragmentHomeBinding
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.model.ResultsItemNowShowing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemOnClickListener {
    var thiscontext: Context? = null

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var progress: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thiscontext = view.context
        binding.drawerIcon.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }
        progress = ProgressDialog(thiscontext)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false) // disable dismiss by tapping outside of the dialog

        binding.homeFragmentId.visibility = View.GONE

        navigatinView()
        binding.homeFragmentId.visibility = View.VISIBLE


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
                    PopularMoviesAdapter(it.results as List<ResultsItem>, this)
                binding.popularRecView.adapter = popularMoviesAdapter
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

//        progress.dismiss()

    }

    override fun onClickListener(name: String, value: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", value)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

}