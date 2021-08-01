package com.adi.githubsearch.ui.main

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adi.githubsearch.R
import com.adi.githubsearch.api.Result
import com.adi.githubsearch.api.response.Item
import com.adi.githubsearch.databinding.SearchFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: UsersAdapter
    var users: ArrayList<Item> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchInputListener()
        initRecyclerView()
        observeViewModel()
    }

    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.searchBtn.setOnClickListener { view ->
            doSearch(view)
        }
    }

    private fun clearAdapter() {
        users.clear()
        adapter.notifyDataSetChanged()
    }

    private fun observeViewModel() {
        viewModel.searchResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    setLoading(false)
                    if (result.data?.items.isNullOrEmpty()) {
                        clearAdapter()
                        setError(getString(R.string.empty_search_result, viewModel.query))
                    } else {
                        result.data?.items?.let {
                            users.clear()
                            users.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is Result.Loading -> {
                    setLoading(true)
                    setError(null)
                }
                is Result.Failure -> {
                    setLoading(false)
                    clearAdapter()
                    setError(result.errorData.message)
                }
                else -> {
                    setLoading(false)
                    clearAdapter()
                    setError(getString(R.string.general_error))
                }
            }
        })
        viewModel.loadMoreResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    setLoadMore(false)
                    result.data?.items?.let {
                        val lastPos = users.size - 1
                        users.addAll(it)
                        adapter.notifyItemRangeInserted(lastPos, it.size)
                    }
                }
                is Result.Loading -> {
                    setLoadMore(true)
                }
                is Result.Failure -> {
                    setLoadMore(false)
                    Snackbar.make(binding.loadMoreBar, result.errorData.message, Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    setLoadMore(false)
                    Snackbar.make(binding.loadMoreBar, getString(R.string.general_error), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        adapter = UsersAdapter(users)
        binding.userList.adapter = adapter
        binding.userList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 5) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        viewModel.setQuery(query)
        viewModel.searchUser()
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setLoadMore(isLoading: Boolean) {
        binding.loadMoreBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setError(errorMessage: String?) {
        binding.error.visibility = if (errorMessage.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.error.text = errorMessage
    }
}