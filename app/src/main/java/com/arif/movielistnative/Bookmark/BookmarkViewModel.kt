package com.arif.movielistnative.Bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arif.movielistnative.AppRepository
import com.arif.movielistnative.dataBase.AppTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val apiRepository: AppRepository) :
    ViewModel() {

    private val _bookmarkLiveData = MutableLiveData<List<AppTable>>()
    val bookmarkLiveData: LiveData<List<AppTable>> get() = _bookmarkLiveData


    fun getBookmarkData() {
        viewModelScope.launch {
            val res = apiRepository.getBookmarksMovie()
            _bookmarkLiveData.value = res
        }
    }

    fun deleteBookmark(id: Int?) {
        viewModelScope.launch {
            apiRepository.deleteFromBookmark(id)
        }
    }
}