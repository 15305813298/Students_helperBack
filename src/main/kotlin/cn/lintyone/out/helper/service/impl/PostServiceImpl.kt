package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.common.defaultPage
import cn.lintyone.out.helper.model.Post
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.PostInput
import cn.lintyone.out.helper.repository.PostRepository
import cn.lintyone.out.helper.service.PostService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class PostServiceImpl : PostService {

    @Autowired
    lateinit var postRepository: PostRepository

    override fun create(user: User, postInput: PostInput): Post {
        val post = Post()
        post.user = user
        post.content = postInput.content
        post.image = postInput.image
        post.title = postInput.title
        postRepository.save(post)
        return post
    }

    override fun addCountComment(post: Post) {
        post.countComment++
        postRepository.save(post)
    }

    override fun addCountView(post: Post) {
        post.countView++
        postRepository.save(post)
    }

    override fun get(id: Int): Post {
        val optional = postRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw MyException("帖子不存在")
        }
    }

    override fun update(user: User, postInput: PostInput) {
        val post = get(postInput.id!!)
        if (post.user != user) {
            throw MyException("用户无权限")
        }
        post.title = postInput.title
        post.image = postInput.image
        post.content = postInput.content
        postRepository.save(post)
    }

    override fun list(page: Int, filter: String, users: List<User>): List<Post> {
        val pre = Specifications.or<Post>()
        if (filter.isNotEmpty()) {
            pre.eq("user", users)
            pre.like("title", "%$filter%")
            pre.like("content", "%$filter%")
        }
        return pre.defaultPage(page, postRepository).content
    }

    override fun listByUser(user: User, page: Int): List<Post> {
        val pre = Specifications.and<Post>()
        pre.eq("user", user)
        return pre.defaultPage(page, postRepository, 100).content
    }

    override fun paginate(page: Int, filter: String): Page<Post> {
        val pre = Specifications.or<Post>()
        pre.like("title", "%$filter%")
        pre.like("content", "%$filter%")
        return pre.defaultPage(page, postRepository)
    }

    override fun delete(id: Int) {
        postRepository.deleteById(id)
    }
}