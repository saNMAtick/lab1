package com.example.proc.repository

import androidx.lifecycle.LiveData
import com.example.proc.activity.Post

interface PostRepository
{
    fun get(): LiveData<List<Post>>
    fun like(id: Long)
    fun share(id: Long)
}