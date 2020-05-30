package cn.lintyone.out.helper.resolver.mutation

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.CreateUserInput
import cn.lintyone.out.helper.model.input.ForgotPasswordInput
import cn.lintyone.out.helper.model.input.LoginInput
import cn.lintyone.out.helper.resolver.BaseMutation
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class UserMutation : BaseMutation() {

    fun register(createUserInput: CreateUserInput): User {
        val school = schoolService.getByName(createUserInput.schoolName)
        val user = userService.create(school, createUserInput)
        user.setToken()
        return user
    }

    fun resetPassword(forgotPasswordInput: ForgotPasswordInput): String {
//        if (forgotPasswordInput.phone != userService.findLike(forgotPasswordInput.username)) {
//            throw MyException("验证码错误")
//        }
        userService.resetPassword(getUser(), forgotPasswordInput.password)
        return "密码修改成功"
    }

    fun login(loginInput: LoginInput): User {
        val user = userService.login(loginInput)
        user.setToken()
        return user
    }

    fun changeName(name: String): String {
        userService.updateName(getUser(), name)
        return "用户昵称修改成功"
    }

    fun changeAvatar(avatar: String): String {
        userService.updateAvatar(getUser(), avatar)
        return "头像修改成功"
    }

    fun changeCity(city: String): String {
        userService.changeCity(getUser(), city)
        return "城市修改成功"
    }

    fun addBalance(money: Int): String {
        userService.addBalance(getUser(), BigDecimal(money))
        return "充值 $money 元成功"
    }
}