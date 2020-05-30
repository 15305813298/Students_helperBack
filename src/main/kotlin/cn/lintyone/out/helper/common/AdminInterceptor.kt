package cn.lintyone.out.helper.common

import cn.lintyone.out.helper.App
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AdminInterceptor : HandlerInterceptorAdapter() {

    companion object {
        const val LOGIN_ADMIN_ID = "loginUserID"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return if (request.session.getAttribute(LOGIN_ADMIN_ID) != null) {
            true
        } else {
            if (App.debug) {
                request.session.setAttribute(LOGIN_ADMIN_ID, 1)
                true
            } else {
                response.sendRedirect("/login")
                false
            }
        }
    }
}