package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.common.defaultPage
import cn.lintyone.out.helper.event.MessageEvent
import cn.lintyone.out.helper.model.Comment
import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.Post
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.CommentInput
import cn.lintyone.out.helper.repository.CommentRepository
import cn.lintyone.out.helper.service.CommentService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl : CommentService {

    @Autowired
    lateinit var commentRepository: CommentRepository
    @Autowired
    lateinit var context: ApplicationContext

    @Transactional
    override fun create(user: User, post: Post, commentInput: CommentInput): Comment {
        val comment = Comment()
        if (commentInput.targetId != 0) {
            val target = get(commentInput.targetId)
            comment.comment = target
            context.publishEvent(MessageEvent(
                    this,
                    Message.Type.COMMENT,
                    "您的评论\n" +
                            "[" + target + "]\n" +
                            "被引用",
                    target.user))
        }
        comment.content = commentInput.content
        comment.post = post
        comment.user = user
        commentRepository.save(comment)
        return comment
    }

    override fun delete(id: Int) {
        commentRepository.deleteById(id)
    }

    override fun get(id: Int): Comment {
        val optional = commentRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw MyException("评论不存在")
        }
    }

    override fun paginate(page: Int, filter: String): Page<Comment> {
        val pre = Specifications.and<Comment>()
        pre.like("content", "%$filter%")
        return pre.defaultPage(page, commentRepository)
    }


    override fun listByUser(user: User, page: Int): List<Comment> {
        val pre = Specifications.and<Comment>()
        pre.eq("user", user)
        return pre.defaultPage(page, commentRepository).content
    }

    override fun listByPost(post: Post, page: Int): List<Comment> {
        val pre = Specifications.and<Comment>()
        pre.eq("post", post)
        return pre.defaultPage(page, commentRepository).content
    }
}