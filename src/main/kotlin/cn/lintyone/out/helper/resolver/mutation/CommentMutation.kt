package cn.lintyone.out.helper.resolver.mutation

import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.input.CommentInput
import cn.lintyone.out.helper.resolver.BaseMutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentMutation : BaseMutation() {

    @Transactional
    fun createComment(commentInput: CommentInput): String {
        val post = postService.get(commentInput.postId)
        val comment = commentService.create(getUser(), post, commentInput)
        postService.addCountComment(post)
        messageService.create(post.user, "帖子被评论\n" +
                "内容: ${comment.content}" +
                "用户: ${comment.user.nickName
                        ?: comment.user.nickName}", Message.Type.COMMENT)
        if (comment.comment != null) {
            messageService.create(comment.comment!!.user, "评论被引用\n" +
                    "内容: ${comment.content}" +
                    "用户: ${comment.user.nickName
                            ?: comment.user.nickName}", Message.Type.COMMENT)
        }
        return "评论成功"
    }


}