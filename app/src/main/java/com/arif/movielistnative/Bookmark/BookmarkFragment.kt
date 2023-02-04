package com.arif.movielistnative.Bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arif.movielistnative.Bookmark.Adapter.BookmarkAdapter
import com.arif.movielistnative.R
import com.arif.movielistnative.Utill.listener.DeleteListener
import com.arif.movielistnative.Utill.listener.ItemOnClickListener
import com.arif.movielistnative.dataBase.AppTable
import com.arif.movielistnative.databinding.FragmentBookmarkBinding
import com.arif.movielistnative.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(), DeleteListener, ItemOnClickListener {

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModels()
    private  val list= arrayListOf<AppTable>()
    private lateinit var programAdapter: BookmarkAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBookmarkData()
        viewModel.bookmarkLiveData.observe(viewLifecycleOwner){
            list.clear()
            list.addAll(it)
            programAdapter= BookmarkAdapter(list,this, this)
            binding.bookmarkRecyclerId.adapter=programAdapter

        }

    }

    override fun onDelete(appTable: AppTable) {
        viewModel.deleteBookmark(appTable.id)
        list.remove(appTable)
        programAdapter.notifyDataSetChanged()    }

    override fun onClickListener(name: String, value: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", value)
        findNavController().navigate(R.id.action_bookmarkFragment_to_detailsFragment, bundle)
    }

}