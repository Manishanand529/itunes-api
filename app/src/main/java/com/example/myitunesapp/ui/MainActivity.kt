package com.example.myitunesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myitunesapp.R
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.example.myitunesapp.Data.db.SongDatabase
import com.example.myitunesapp.Data.entities.Song
import com.example.myitunesapp.databinding.ActivityMainBinding
import com.example.myitunesapp.networking.ApiCalls
import com.example.myitunesapp.networking.NetworkConnectionInterceptor
import com.example.myitunesapp.repository.SongRepository



class MainActivity : AppCompatActivity() ,ResListeners{

    var adapter: Adapter? = null
    var recyclerView: RecyclerView? = null
    var songsModels: ArrayList<SongViewModel> = ArrayList<SongViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val interceptor = NetworkConnectionInterceptor(this)
        val api = ApiCalls(interceptor)
        val dab = SongDatabase(this)
        val repository = SongRepository(api, dab)

        val binding: ActivityMainBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(this, songsModels)
        binding.recyclerView.adapter = adapter

        val viewModel = MainViewModel(repository)
        viewModel.listeners = this
        binding.viewmodel = viewModel
    }

    override fun onFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {

    }

    override fun processRV(
            songs: List<Song>,
            of: String
    ) {

        songsModels.clear()
        for (item in songs) {
            var songViewModel = SongViewModel()
            songViewModel.artistName = item.artistName
            songViewModel.trackName = item.trackName
            songViewModel.url = item.artworkUrl100
            songViewModel.of = of
            songsModels.add(songViewModel)
        }

        adapter!!.notifyDataSetChanged()


    }
}