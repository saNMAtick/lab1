package com.example.proc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practuhv3.R
import com.example.practuhv3.databinding.CardPostBinding
import com.example.proc.activity.Post


typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            textTitle.text = post.author
            textDate.text = post.published
            textContent.text = post.content
            likeCount.text = when {
                post.likes >= 1_000_000 -> "${post.likes / 1_000_000}M"
                post.likes >= 1_000 -> "${post.likes / 1_000}K"
                else -> post.likes.toString()
            }
            sharecount.text = when {
                post.shares >= 1_000_000 -> "${post.shares / 1_000_000}M"
                post.shares >= 1_000 -> "${post.shares / 1_000}K"
                else -> post.shares.toString()
            }
            like.setImageResource(
                if (post.likedByMe) R.drawable.icon_like_add else R.drawable.icon_like
            )

            like.setOnClickListener {
                onLikeListener(post)
            }
            share.setOnClickListener {
                onShareListener(post)
            }
        }
    }
}
