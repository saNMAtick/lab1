package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    data class Post(
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var shares: Int=0,
        var likes: Int = 9999999,
        var likedByMe: Boolean = false

    )
    private lateinit var binding: ActivityMainBinding
    private var post = Post(
        id = 1,
        author = "Adidas",
        content = "adidas — производитель спортивной одежды и обуви, основанный в 1949 году в Германии. «Бренд с тремя полосками» является одной из крупнейших спортивных компаний в истории. Гений инноваций, легенда теннисных кортов и всевозможных кроссовок, adidas оказал огромное влияние на индустрию спортивных товаров, моду, культуру и музыку.",
        published = "21 мая в 18:36",
        likedByMe = false ,
        likes = 10100,
        shares = 999,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            updateLikeButton()
            likeCount!!.text = formatCount(post.likes)
            shareCount!!.text = formatCount(post.shares)

            root.setOnClickListener {
                Log.d("MainActivity", "Post clicked")
            }

            avatar.setOnClickListener {
                Log.d("MainActivity", "Avatar clicked")
            }

            like!!.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    post.likes++
                } else {
                    post.likes--
                }
                updateLikeButton()
                likeCount.text = formatCount(post.likes)
            }

            share!!.setOnClickListener {
                post.shares++
                shareCount.text = formatCount(post.shares)
                Log.d("MainActivity", "Shared post")
            }
        }
    }

    private fun updateLikeButton() {
        binding.like!!.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> String.format("%.1fM", count / 1_000_000.0).replace(",00", "")
            count >= 1_000 -> "${count / 1_000}K"
            else -> count.toString()
        }
    }
}







