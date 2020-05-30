package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.common.defaultPage
import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.School
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.CreateUserInput
import cn.lintyone.out.helper.model.input.LoginInput
import cn.lintyone.out.helper.repository.UserRepository
import cn.lintyone.out.helper.service.UserService
import com.github.wenhao.jpa.Specifications
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Timestamp

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun get(id: Int): User {
        val optional = userRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw MyException("用户不存在")
        }
    }

    override fun create(school: School, createUserInput: CreateUserInput): User {
        var user = userRepository.findByUserNameOrStudentId(createUserInput.userName, createUserInput.studentId)
        if (user == null) {
            user = User()
            user.studentId = createUserInput.studentId
            user.phone = createUserInput.phone
            user.userName = createUserInput.userName
            user.password = BCrypt.hashpw(createUserInput.password, BCrypt.gensalt())
            user.school = school
            userRepository.save(user)
            return user
        } else {
            throw MyException("学号或用户名已存在")
        }
    }

    override fun setDefault(user: User, address: Address) {
        user.address = address
        userRepository.save(user)
    }

    override fun updateName(user: User, name: String): User {
        user.nickName = name
        userRepository.save(user)
        return user
    }

    override fun updateAvatar(user: User, avatar: String): User {
        user.avatar = avatar
        userRepository.save(user)
        return user
    }

    override fun resetPassword(user: User, password: String): User {
        user.password = BCrypt.hashpw(password, BCrypt.gensalt())
        userRepository.save(user)
        return user
    }

    override fun login(loginInput: LoginInput): User {
        var user = userRepository.findByUserName(loginInput.userName)
        if (user == null) {
            user = userRepository.findByStudentId(loginInput.userName)
            if (user == null) {
                throw MyException("账号或密码错误")
            } else {
                if (!BCrypt.checkpw(loginInput.password, user.password)) {
                    throw MyException("账号或密码错误")
                }
            }
        } else {
            if (!BCrypt.checkpw(loginInput.password, user.password)) {
                throw MyException("账号或密码错误")
            }
        }
        return user
    }

    override fun changeCity(user: User, city: String) {
        if (city.isNotEmpty()) {
            user.city = city
            userRepository.save(user)
        } else {
            throw MyException("城市不得为空")
        }
    }

    override fun addBalance(user: User, number: BigDecimal) {
        user.balance = user.balance.add(number)
        userRepository.save(user)
    }

    override fun reduceBalance(user: User, number: BigDecimal) {
        if (user.balance.compareTo(number) == -1) {
            throw MyException("用户余额不足")
        }
        user.balance = user.balance.subtract(number)
        userRepository.save(user)
    }

    override fun updateLoginTime(user: User) {
        user.lastLoginTime = Timestamp(System.currentTimeMillis())
        userRepository.save(user)
    }

    override fun findLike(filter: String): List<User> {
        val pre = Specifications.or<User>()
        pre.like("userName", "%$filter%")
        pre.like("nickName", "%$filter%")
        return pre.defaultPage(0, userRepository).content
    }

    override fun paginate(page: Int, filter: String): Page<User> {
        val pre = Specifications.or<User>()
        if (filter.isNotEmpty()) {
            pre.like("userName", "%$filter%")
            pre.like("nickName", "%$filter%")
        }
        return pre.defaultPage(page, userRepository)
    }

    override fun delete(id: Int) {
        userRepository.deleteById(id)
    }
}