package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.*
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.RequestParam

interface FeedbackService {

    fun create(user: User, content: String) : Feedback

    fun delete(id:Int)

    fun listByPost(post: Post, page: Int): List<Feedback>

    fun listByUser(user: User, page: Int): List<Feedback>

    fun paginate(page: Int, filter: String): Page<Feedback>
}