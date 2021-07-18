package com.example.marvelboard

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull

class ComicController(private val comicRepository: ComicRepository) {

    private var _comicData: MutableStateFlow<Comic?>? = null


    suspend fun fetchComic(comicId: String): Flow<Comic> {
       if (_comicData == null) {
           _comicData = MutableStateFlow(comicRepository.fetchComicById(comicId))
       }
        return _comicData!!.filterNotNull()
    }
}