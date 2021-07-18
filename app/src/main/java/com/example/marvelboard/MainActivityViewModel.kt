package com.example.marvelboard

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    val comicController = ComicController(ComicRepository())
}