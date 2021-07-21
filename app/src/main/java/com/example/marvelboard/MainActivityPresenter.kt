package com.example.marvelboard

import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.marvelboard.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityPresenter @Inject constructor() {

    fun present(binding: ActivityMainBinding, viewModel: MainActivityViewModel) {
        binding.lifecycleOwner?.lifecycleScope?.launchWhenStarted {
            viewModel.comicController.fetchComic("333").catch {
                it.printStackTrace()
                Toast.makeText(binding.root.context, "Error fetching comic. Try again later!", Toast.LENGTH_LONG).show()
            }.collect {
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
