package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.model.Admin
import cn.lintyone.out.helper.repository.AdminRepository
import cn.lintyone.out.helper.service.AdminService
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl : AdminService {

    @Autowired
    lateinit var adminRepository: AdminRepository

    override fun get(id: Int): Admin {
        val optional = adminRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw MyException("管理员记录不存在")
        }
    }

    override fun getByUserName(userName: String): Admin? {
        return adminRepository.findByUserName(userName)
    }

    override fun changePassword(admin: Admin, password: String, oldPassword: String) {
        if (!BCrypt.checkpw(oldPassword, admin.password)) {
            throw MyException("旧密码不正确")
        } else {
            admin.password = BCrypt.hashpw(password, BCrypt.gensalt())
            adminRepository.save(admin)
        }
    }

}