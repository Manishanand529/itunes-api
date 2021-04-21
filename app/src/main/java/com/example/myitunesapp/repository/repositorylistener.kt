package com.example.myitunesapp.repository

import com.example.myitunesapp.Data.ApiResponse

interface RepositoryListener {
    fun onSuccess(res: ApiResponse, of: String)
}