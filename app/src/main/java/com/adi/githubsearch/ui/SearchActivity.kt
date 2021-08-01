package com.adi.githubsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adi.githubsearch.databinding.SearchActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: SearchActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}