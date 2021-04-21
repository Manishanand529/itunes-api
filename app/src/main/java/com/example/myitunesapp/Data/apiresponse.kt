package com.example.myitunesapp.Data

import com.example.myitunesapp.Data.entities.Song

data class ApiResponse(
    var resultCount: Int,
    var results: List<Song>

)

