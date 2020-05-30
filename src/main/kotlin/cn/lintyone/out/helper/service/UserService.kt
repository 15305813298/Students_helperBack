package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.School
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.CreateUserInput
import cn.lintyone.out.helper.model.input.LoginInput
import org.springframework.data.domain.Page
import java.math.BigDecimal


interface UserService {

    fun get(id: Int): User

    fun create(school: School, createUserInput: CreateUserInput): User

    fun setDefault(user: User, address: Address)

    fun updateName(user: User, name: String): User

    fun updateAvatar(user: User, avatar: String): User

    fun resetPassword(user: User, password: String): User

    fun login(loginInput: LoginInput): User

    fun changeCity(user: User, city: String)

    fun addBalance(user: User, number: BigDecimal)

    fun reduceBalance(user: User, number: BigDecimal)

    fun updateLoginTime(user: User)

    fun findLike(filter: String): List<User>

    fun paginate(page : Int,filter: String) : Page<User>

    fun delete(id : Int)


}