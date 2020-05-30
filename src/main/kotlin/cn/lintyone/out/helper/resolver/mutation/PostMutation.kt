package cn.lintyone.out.helper.resolver.mutation

import cn.lintyone.out.helper.model.input.PostInput
import cn.lintyone.out.helper.resolver.BaseMutation
import org.springframework.stereotype.Component

@Component
class PostMutation : BaseMutation() {

    fun createPost(postInput: PostInput): String {
        postService.create(getUser(), postInput)
        return "帖子创建成功"
    }

    fun updatePost(postInput: PostInput): String {
        postService.update(getUser(), postInput)
        return "帖子修改成功"
    }

}