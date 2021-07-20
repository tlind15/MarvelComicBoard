package com.example.marvelboard

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.marvelboard.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityPresenter @Inject constructor() {

    fun present(binding: ActivityMainBinding, viewModel: MainActivityViewModel) {
        binding.lifecycleOwner?.lifecycleScope?.launchWhenStarted {
            viewModel.comicController.fetchComic("333").collect {
                withContext(Dispatchers.Main) {
                    binding.comic = it
                }
            }
        }
    }
}

@BindingAdapter("imageUrl")
fun fetchImage(view: ImageView, url: String?) {
    url?.let { Glide.with(view).load(url).into(view) }
}
