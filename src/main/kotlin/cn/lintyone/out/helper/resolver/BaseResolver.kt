package cn.lintyone.out.helper.resolver

import cn.lintyone.out.helper.common.GraphQLAspect
import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

open class BaseResolver {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var addressService: AddressService
    @Autowired
    lateinit var commentService: CommentService
    @Autowired
    lateinit var feedbackService: FeedbackService
    @Autowired
    lateinit var messageService: MessageService
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var postService: PostService
    @Autowired
    lateinit var schoolService: SchoolService

    private var loginUser: User? = null

    private fun getUserId() = (GraphQLAspect.userId ?: throw MyException("用户未登录或登录信息已过期")).toInt()

    protected fun getUser(): User {
        return  userService.get(getUserId())
    }

//    protected fun getPhone():String{
//        return
//    }

    protected fun getUserNotException(): User? {
        return try {
            getUser()
        } catch (e: MyException) {
            null
        }
    }


}