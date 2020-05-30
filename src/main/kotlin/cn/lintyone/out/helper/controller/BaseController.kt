package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.common.AdminInterceptor
import cn.lintyone.out.helper.common.JwtToken
import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.repository.AdminRepository
import cn.lintyone.out.helper.service.UserService
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest


open class BaseController {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var request: HttpServletRequest
    @Autowired
    lateinit var adminRepository: AdminRepository

    val mView = ModelAndView().apply {
        this.viewName = "error"
    }

    private fun getUserId(): String {
        val token: String = request.getHeader("token") ?: throw MyException("用户未登录")
        return JwtToken.getUserId(token)
    }

    fun getUser(): User {
        return userService.get(getUserId().toInt())
    }


    /**
     * 从session获取当前登录的用户ID
     */
    fun getAdminId() = request.session.getAttribute(AdminInterceptor.LOGIN_ADMIN_ID).toString().toInt()

    /**
     * 从session获取当前登录用户
     */
    fun getAdmin() = adminRepository.findById(getAdminId()).get()

    /**
     * 从session获取当前登录用户并渲染进模板Layout
     * 传入标题[title] 初始化界面标题
     */
    fun initView(title: String) {
        mView.addObject("admin", getAdmin())
        mView.addObject("title", title)
    }

    /**
     * 清理模型
     */
    fun clearView() = mView.model.clear()

    /**
     * 清理模型并初始化用户信息
     * 传入标题[title] 初始化界面标题
     */
    fun clearAndInit(title: String) {
        clearView()
        initView(title)
    }

    /**
     * 返回消息的格式
     */
    fun setMessage(message: String, type: Boolean, redirect: String = "") {
        mView.model.apply {
            this["message"] = message
            this["messageType"] = if (type) "success" else "danger"
            this["redirect"] = redirect
        }
    }

    fun sendMessage(message: String, type: Boolean, redirect: String = ""): Map<String, String> {
        return HashMap<String, String>().apply {
            this["message"] = message
            this["messageType"] = if (type) "success" else "danger"
            this["redirect"] = redirect
        }
    }

    /**
     * 获取请求中的ID列表
     */
    fun getRequestList(checkId: String): List<Int> {
        return Gson().fromJson(checkId, ArrayList<Int>().javaClass).toIntArray().toList()
    }

}