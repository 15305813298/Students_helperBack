package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.Comment
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class CommentQuery : BaseQuery() {

    fun commentByUser(page: Int): List<Comment> {
        return commentService.listByUser(getUser(), page)
    }

    fun commentByPost(id: Int, page: Int): List<Comment> {
        val post = postService.get(id)
        return commentService.listByPost(post, page)
    }


}