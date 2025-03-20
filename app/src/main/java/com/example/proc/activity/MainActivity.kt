package com.example.proc.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practuhv3.databinding.ActivityMainBinding
import com.example.proc.adapter.PostsAdapter
import com.example.proc.viewmodel.PostViewModel

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 999,
    var shares: Int = 0,
    var likedByMe: Boolean = false
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PostsAdapter(
            onLikeListener = { post ->
                Log.d("MainActivity", "Like clicked for post ${post.id}")
                viewModel.like(post.id)
            },
            onShareListener = { post ->
                Log.d("MainActivity", "Share clicked for post ${post.id}")
                viewModel.share(post.id)
            }
        )


        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            Log.d("MainActivity", "Data updated: ${posts.size} posts")
            adapter.list = posts
        }
    }
}
