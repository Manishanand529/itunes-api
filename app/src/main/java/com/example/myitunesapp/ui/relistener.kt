package com.example.myitunesapp.ui

import com.example.myitunesapp.Data.entities.Song

interface ResListeners {
    fun onFailure(msg: String)

    fun onSuccess()
    fun processRV(songs: List<Song>, of: String)
}