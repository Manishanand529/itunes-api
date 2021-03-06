package com.example.myitunesapp.repository

import com.example.myitunesapp.Data.ApiResponse
import com.example.myitunesapp.Data.db.SongDatabase
import com.example.myitunesapp.Data.entities.Song
import com.example.myitunesapp.networking.ApiCalls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongRepository(
    val api: ApiCalls,
    val db: SongDatabase

    ) {

    var listener: RepositoryListener? = null

    fun getSongs(name: String): ApiResponse? {
        var _response: ApiResponse? = null
        api.getSongs(name, 10)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    _response = response.body()
                    if (_response != null) {
                        db.getSongDao().upsert(_response!!.results)
                        listener?.onSuccess(_response!!, "Online")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                    var songs: List<Song> = getSongsFromDatabase(name)
                    _response = ApiResponse(songs.size, songs)
                    listener?.onSuccess(_response!!, "Offline")
                }

            })

        return _response
    }

    fun getSongsFromDatabase(name: String): List<Song> {

        return db.getSongDao().getSongs("%$name%")
    }

}