package cn.lintyone.out.helper.resolver.mutation

import cn.lintyone.out.helper.resolver.BaseMutation
import org.springframework.stereotype.Component

@Component
class FeedbackMutation : BaseMutation() {

    fun createFeedback(content: String): String {
        feedbackService.create(getUser(), content)
        return "反馈成功"
    }
}