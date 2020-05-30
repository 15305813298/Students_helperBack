package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class UserQuery : BaseQuery() {

    fun userInfo(id: Int?): User {
        return if (id == null) {
            val user = getUser()
            userService.updateLoginTime(user)
            user
        } else {
            userService.get(id)
        }
    }
}