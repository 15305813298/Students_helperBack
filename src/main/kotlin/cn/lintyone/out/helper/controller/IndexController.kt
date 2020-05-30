package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.common.AdminInterceptor
import cn.lintyone.out.helper.repository.*
import com.google.code.kaptcha.Constants
import com.google.code.kaptcha.Producer
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList

@Controller
@RequestMapping("/")
class IndexController : BaseController() {

    @Autowired
    private lateinit var producer: Producer
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var commentRepository: CommentRepository
    @Autowired
    private lateinit var feedbackRepository: FeedbackRepository
    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var postRepository: PostRepository

    @GetMapping
    fun index(): ModelAndView {
        clearAndInit("后台首页")
        mView.viewName = "index"
        val adminUser = getAdmin()
        if (adminUser.userName == "admin" && BCrypt.checkpw("admin", adminUser.password)) {
            setMessage("密码不安全，请尽快修改密码", false)
        }
        val userCount = userRepository.count()
        val userToday = userRepository.countUser()
        val orderCount = orderRepository.countOrder()
        val postCount = postRepository.countPost()

        mView.addObject("userCount", userCount)
        mView.addObject("userToday", userToday)
        mView.addObject("orderCount", orderCount)
        mView.addObject("postCount", postCount)

        val format = SimpleDateFormat("yyyy-MM-dd")
        val time = format.parse(format.format(Date())).time
        val countUserList = ArrayList<Int>()
        val countOrderList = ArrayList<Int>()
        val dayList = ArrayList<String>()
        for (i in 0..7) {
            val min = java.sql.Date(time - (i * 86400000))
            val max = java.sql.Date(time - ((i - 1) * 86400000))
            val countUser = userRepository.countUserDaily(min, max)
            val countOrder = orderRepository.countOrderDaily(min, max)
            dayList.add(min.toLocalDate().toString())
            countUserList.add(countUser)
            countOrderList.add(countOrder)
        }
        mView.addObject("countUserList", countUserList.toString())
        mView.addObject("countOrderList", countOrderList.toString())
        mView.addObject("dayList", dayList.toString())


        return mView
    }

    @GetMapping("login")
    fun loginPage(): ModelAndView {
        mView.viewName = "login"
        return mView
    }

    @PostMapping("login")
    fun login(@RequestParam("username") userName: String, @RequestParam password: String, @RequestParam kaptcha: String): ModelAndView {
        val sessionKaptcha = request.session.getAttribute(Constants.KAPTCHA_SESSION_KEY)?.toString()
        if (sessionKaptcha != null && kaptcha != sessionKaptcha) {
            mView.viewName = "login"
            mView.addObject("message", "验证码不正确")
        } else {
            val adminUser = adminRepository.findByUserName(userName)
            if (adminUser != null && BCrypt.checkpw(password, adminUser.password)) {
                request.session.setAttribute(AdminInterceptor.LOGIN_ADMIN_ID, adminUser.id)
                mView.viewName = "redirect:/"
            } else {
                mView.viewName = "login"
                mView.addObject("message", "用户名或密码不正确")
            }
        }
        return mView
    }

    @GetMapping("logout")
    fun logout(): ModelAndView? {
        request.session.removeAttribute(AdminInterceptor.LOGIN_ADMIN_ID)
        mView.viewName = "redirect:/"
        return mView
    }

    /**
     * 验证码生成
     */
    @RequestMapping("kaptcha")
    fun kaptcha(response: HttpServletResponse) {
        response.addDateHeader("Expires", 0)
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate")
        response.addHeader("Cache-Control", "post-check=0,pre-check=0")
        response.setHeader("Pragma", "no-cache")
        response.contentType = "image/jpeg"

        val capText = producer.createText()
        request.session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText)

        val bufferedImage = producer.createImage(capText)
        val out = response.outputStream
        ImageIO.write(bufferedImage, "jpg", out)
        out.use {
            it.flush()
        }
    }
}