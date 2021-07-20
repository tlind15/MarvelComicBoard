package com.example.marvelboard.utils

import javax.inject.Inject

class Clock @Inject constructor() {

    fun millis() = System.currentTimeMillis()
}