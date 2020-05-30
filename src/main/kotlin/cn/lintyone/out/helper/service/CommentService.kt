package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Comment
import cn.lintyone.out.helper.model.Post
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.CommentInput
import org.springframework.data.domain.Page

interface CommentService {

    fun create(user: User, post: Post, commentInput: CommentInput): Comment

    fun delete(id: Int)

    fun get(id: Int): Comment

    fun paginate(page: Int, filter: String): Page<Comment>

    fun listByUser(user: User, page: Int): List<Comment>

    fun listByPost(post: Post, page: Int): List<Comment>

}