package com.example.marvelboard.utils

import com.example.marvelboard.BuildConfig
import javax.inject.Inject

class MarvelKeyHandler @Inject constructor() {
    val publicKey = BuildConfig.PUBLIC_KEY
    val privateKey = BuildConfig.PRIVATE_KEY
}