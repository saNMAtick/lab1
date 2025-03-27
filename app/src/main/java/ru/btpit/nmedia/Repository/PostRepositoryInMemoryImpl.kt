package ru.btpit.nmedia.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.btpit.nmedia.activity.Post


class PostRepositoryInMemoryImpl: PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = 2,
            author = "Adidas",
            content = "Just Do It!\uD83D\uDC47",
            published = "18 сентября в 10:12",
            shareByMe = false
        ),
        Post(
            id = 1,
            author = "Adidas",
            content = "adidas — производитель спортивной одежды и обуви, основанный в 1949 году в Германии. «Бренд с тремя полосками» является одной из крупнейших спортивных компаний в истории. Гений инноваций, легенда теннисных кортов и всевозможных кроссовок, adidas оказал огромное влияние на индустрию спортивных товаров, моду, культуру и музыку.",
            published = "21 мая в 18:36",
            likedByMe = false,
            shareByMe = false
        ),
        )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id : Long)
    {
         posts = posts.map{
             if (it.id == id) {
                 val likedByMe = !it.likedByMe
                 val newLikesCount = if (likedByMe) it.likes + 1 else it.likes - 1
                 it.copy(likedByMe = likedByMe, likes = newLikesCount)
             } else {
             it
             }
         }
         data.value = posts

    }

    override fun shareById(id: Long)
    {

        posts = posts.map{
            if (it.id == id) {
                val shareByMe = !it.shareByMe
                val newshareCount = it.share + 1
                it.copy(shareByMe = shareByMe, share = newshareCount)
            } else {
                it
            }
        }
        data.value = posts
    }

    override fun save(post: Post)
    {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun removeById(id: Long)
    {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

}