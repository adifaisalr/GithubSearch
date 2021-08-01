package com.adi.githubsearch.ui.main

import androidx.lifecycle.ViewModel
import com.adi.githubsearch.domain.interactor.UserInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : ViewModel() {

    fun getString() = "test"
}