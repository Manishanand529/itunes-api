package com.example.myitunesapp.ui

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.myitunesapp.Data.ApiResponse
import com.example.myitunesapp.repository.RepositoryListener
import com.example.myitunesapp.repository.SongRepository

class MainViewModel(
    val repository: SongRepository

    ) : ViewModel() {

    var artist: String? = null
    var listeners: ResListeners? = null
    var _res: ApiResponse? = null


    fun onSearchClick(view: View) {

        repository.listener = object : RepositoryListener {
            override fun onSuccess(res: ApiResponse, of: String) {
                _res = res
                listeners?.processRV(res.results, of)

            }
        }
        if (artist.isNullOrEmpty()) {
            listeners?.onFailure("No results found :(")
            return
        }

        repository.getSongs(artist!!)

    }
}
