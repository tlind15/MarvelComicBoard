package com.example.marvelboard

import androidx.lifecycle.lifecycleScope
import com.example.marvelboard.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class MainActivityPresenter {

    fun present(binding: ActivityMainBinding, viewModel: MainActivityViewModel) {
        binding.lifecycleOwner?.lifecycleScope?.launchWhenStarted {
            viewModel.comicController.fetchComic("1234").collect {
                withContext(Dispatchers.Main) {
                    binding.comic = it
                }
            }
        }
    }
}