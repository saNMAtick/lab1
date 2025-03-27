package ru.btpit.nmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.CardPostBinding
import ru.btpit.nmedia.activity.Post

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}

    fun onShare(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,

) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,

) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceType", "SetTextI18n")
    fun bind(post: Post) {
        binding.apply {
            textView1.text = post.author
            textView2.text = post.published
            textView3.text = post.content
            /*imageView10.setImageResource(
                if (post.likedByMe) R.drawable.heart_svgrepo_com else R.drawable.hearth_svgrepo_com
            )*/
            like.isChecked = post.likedByMe
            like.text = "${post.likes}"


            imageView15.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            textView9.text = when {
                post.likes >= 1_000_000 -> "${post.likes / 1_000_000}.${post.likes % 1_000_000 / 100_000}М"
                post.likes >= 1_000 -> "${post.likes / 1_000}.${post.likes % 1_000 / 100}К"
                else -> post.likes.toString()
            }

            imageView10.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            textView10.text = when {
                post.share >= 1_000_000 -> "${post.share / 1_000_000}.${post.share % 1_000_000 / 100_000}М"
                post.share >= 1_000 -> "${post.share / 1_000}.${post.share % 1_000 / 100}К"
                else -> post.share.toString()
            }
            imageView11.setOnClickListener {
                onInteractionListener.onShare(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

