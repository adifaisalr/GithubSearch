package com.adi.githubsearch.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adi.githubsearch.MainCoroutineRule
import com.adi.githubsearch.api.Result
import com.adi.githubsearch.api.response.UserSearchResponse
import com.adi.githubsearch.domain.interactor.UserInteractor
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [SearchViewModel]
 */
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private val userInteractor = mockk<UserInteractor>(relaxed = true)

    // Subject under test
    private lateinit var viewModel: SearchViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // observers
    private lateinit var searchObserver: Observer<Result<UserSearchResponse>>

    @Before
    fun setupViewModel() {
        viewModel = spyk(
            SearchViewModel(
                userInteractor,
                SavedStateHandle()
            )
        )
    }

    @Test
    fun loadAllTasksFromRepository_loadingTogglesAndDataLoaded() {
        runBlockingTest {
            // given
            observeSearch()

            val mockData = UserSearchResponse(
                false,
                listOf(),
                0
            )
            val mockResult = Result.Success(mockData)
            coEvery {
                userInteractor.searchUserUseCase.invoke(any())
            } answers {
                mockResult
            }

            // when
            viewModel.searchUser()

            // then
            verifyOrder {
                searchObserver.onChanged(Result.Loading)
                searchObserver.onChanged(mockResult)
            }
        }
    }

    /** Observers */
    private fun observeSearch() {
        searchObserver = mockk(relaxed = true)
        viewModel.searchResult.observeForever(searchObserver)
    }
}
