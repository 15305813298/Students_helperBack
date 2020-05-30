package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Post
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.PostInput
import org.springframework.data.domain.Page

interface PostService {

    fun create(user: User, postInput: PostInput): Post

    fun addCountComment(post: Post)

    fun addCountView(post: Post)

    fun get(id: Int): Post

    fun update(user: User, postInput: PostInput)

    fun list(page: Int, filter: String, users: List<User>): List<Post>

    fun listByUser(user: User, page: Int): List<Post>

    fun paginate(page: Int, filter: String): Page<Post>

    fun delete(id : Int)
}