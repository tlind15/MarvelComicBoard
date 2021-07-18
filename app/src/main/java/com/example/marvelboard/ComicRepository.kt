package com.example.marvelboard

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ComicRepository {

    suspend fun fetchComicById(comicId: String): Comic? {
        return suspendCoroutine<Comic> {
            it.resume(Comic("Gun Theory (2003) #4",
                "The phone rings, and killer-for-hire Harvey embarks on another hit. " +
                        "But nothing's going right this job. There's little room for error in the " +
                        "business of killing - so what happens when one occurs?\r\n32 PGS./ " +
                        "PARENTAL ADVISORY ...$2.50",
                "http://i.annihil.us/u/prod/marvel/i/mg/c/60/4bc69f11baf75"))
        }
    }
}