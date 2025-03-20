package com.example.proc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.proc.activity.Post
import com.example.proc.repository.PostRepository
import com.example.proc.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<List<Post>> = repository.get()

    fun like(id:Long) = repository.like(id)
    fun share(id:Long) = repository.share(id)
}