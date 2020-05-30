package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.Post
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class PostQuery : BaseQuery() {

    fun post(id: Int): Post {
        val post = postService.get(id)
        postService.addCountView(post)
        return post
    }

    fun posts(page: Int, filter: String): List<Post> {
        val users = userService.findLike(filter)
        return postService.list(page, filter, users)
    }

    fun postsByUser(page: Int): List<Post> {
        return postService.listByUser(getUser(), page)
    }
}