package com.adi.githubsearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.githubsearch.api.Result
import com.adi.githubsearch.api.response.UserSearchResponse
import com.adi.githubsearch.domain.interactor.UserInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _query = ""
    val query: String
        get() = _query

    val _searchResult: MutableLiveData<Result<UserSearchResponse>> =
        savedStateHandle.getLiveData(SAVED_STATE_SEARCH_RESULT)
    val searchResult: LiveData<Result<UserSearchResponse>>
        get() = _searchResult

    val _loadMoreResult: MutableLiveData<Result<UserSearchResponse>> =
        savedStateHandle.getLiveData(SAVED_STATE_LOADMORE_RESULT)
    val loadMoreResult: LiveData<Result<UserSearchResponse>>
        get() = _loadMoreResult

    private val totalPage: Int
        get() = searchResult.value?.peekData?.total_count ?: 0

    private var currentPage = savedStateHandle.get<Int>(SAVED_STATE_CURRENT_PAGE) ?: 1

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query) {
            return
        }
        currentPage = 1
        _query = input
    }

    fun searchUser() = viewModelScope.launch {
        _searchResult.postValue(Result.Loading)
        val response = userInteractor.searchUserUseCase(query)
        _searchResult.postValue(response)
    }

    fun loadNextPage() = viewModelScope.launch {
        if ((currentPage * PER_PAGE) < totalPage && loadMoreResult.value !is Result.Loading) {
            _loadMoreResult.postValue(Result.Loading)
            val response = userInteractor.searchUserUseCase(query, ++currentPage)
            _loadMoreResult.postValue(response)
        }
    }

    companion object {
        const val SAVED_STATE_SEARCH_RESULT = "search_result"
        const val SAVED_STATE_LOADMORE_RESULT = "load_more_result"
        const val SAVED_STATE_CURRENT_PAGE = "current_page"
        const val PER_PAGE = 30
    }
}