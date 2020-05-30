package cn.lintyone.out.helper

import cn.lintyone.out.helper.common.JwtToken
import cn.lintyone.out.helper.model.Admin
import cn.lintyone.out.helper.model.School
import cn.lintyone.out.helper.repository.AdminRepository
import cn.lintyone.out.helper.repository.SchoolRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.util.ResourceUtils
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
class App {
    companion object {
        var debug = false
        var runPath = ""
    }

    @Autowired
    private lateinit var adminRepository: AdminRepository

    @Autowired
    private lateinit var schoolRepository: SchoolRepository

    @Value("\${jwt.secret}")
    fun setSecret(secret: String) {
        JwtToken.secret = secret
    }

    @Value("\${jwt.expireTime}")
    fun setExpireTime(expireTime: Int) {
        JwtToken.expireTime = expireTime
    }

    @Value("\${config.debug}")
    fun setDebug(debug: Boolean) {
        App.debug = debug
    }

    @PostConstruct
    fun initServer() {
        val schoolcount = schoolRepository.count()
        if(schoolcount < 1){
            val schoolinit = School()
            schoolinit.name="杭师大"
            schoolRepository.save(schoolinit)
        }
        val adminCount = adminRepository.count()
        if (adminCount < 1) {
            val admin = Admin()
            admin.userName = "admin"
            admin.password = BCrypt.hashpw("admin", BCrypt.gensalt())
            adminRepository.save(admin)
        }
        runPath = if (!debug) {
            ResourceUtils.getURL("classpath:").path
        } else {
            ResourceUtils.getURL("").path + "src/"
        }
    }

}

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
