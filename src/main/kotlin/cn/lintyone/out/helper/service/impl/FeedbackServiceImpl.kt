package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.defaultPage
import cn.lintyone.out.helper.model.Comment
import cn.lintyone.out.helper.model.Feedback
import cn.lintyone.out.helper.model.Post
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.repository.FeedbackRepository
import cn.lintyone.out.helper.service.FeedbackService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
class FeedbackServiceImpl : FeedbackService {

    @Autowired
    lateinit var feedbackRepository: FeedbackRepository

    override fun create(user: User, content: String): Feedback {
        val feedback = Feedback()
        feedback.content = content
        feedback.user = user
        feedbackRepository.save(feedback)
        return feedback
    }

    override fun delete(id:Int){
        feedbackRepository.deleteById(id)
    }

    override fun listByUser(user: User, page: Int): List<Feedback> {
        val pre = Specifications.and<Feedback>()
        pre.eq("user", user)
        return pre.defaultPage(page, feedbackRepository).content
    }

    override fun listByPost(post: Post, page: Int): List<Feedback> {
        val pre = Specifications.and<Feedback>()
        pre.eq("post", post)
        return pre.defaultPage(page, feedbackRepository).content
    }

    override fun paginate(page: Int, filter: String): Page<Feedback> {
        val pre = Specifications.and<Feedback>()
        pre.like("content", "%$filter%")
        return pre.defaultPage(page, feedbackRepository)
    }
}