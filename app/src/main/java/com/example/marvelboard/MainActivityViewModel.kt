package com.example.marvelboard

import androidx.lifecycle.ViewModel
import com.example.marvelboard.utils.Clock
import com.example.marvelboard.utils.ComicApiFactory
import com.example.marvelboard.utils.MarvelKeyHandler
import com.example.marvelboard.utils.MarvelRestHandler

class MainActivityViewModel: ViewModel() {

    val comicController = ComicController(ComicRepository(ComicApiFactory().createApi("https://gateway.marvel.com/v1/public/"),
        MarvelRestHandler(), MarvelKeyHandler(), Clock()
    ))
}