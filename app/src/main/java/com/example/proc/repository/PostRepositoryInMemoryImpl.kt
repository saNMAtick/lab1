package com.example.proc.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proc.activity.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var posts = listOf(
        Post(
            id = 2,
            author = "Adidas",
            content = "Just Do It!\uD83D\uDC47",
            published = "18 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = 1,
            author = "Adidas",
            content = "adidas — производитель спортивной одежды и обуви, основанный в 1949 году в Германии. «Бренд с тремя полосками» является одной из крупнейших спортивных компаний в истории. Гений инноваций, легенда теннисных кортов и всевозможных кроссовок, adidas оказал огромное влияние на индустрию спортивных товаров, моду, культуру и музыку.",
            published = "21 мая в 18:36",
            likedByMe = false ,
        ),
    )
    private val data = MutableLiveData(posts)
    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }
}

