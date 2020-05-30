package cn.lintyone.out.helper.controller

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/admin")
class AdminController : BaseController() {

    @GetMapping("/edit")
    fun edit(): ModelAndView {
        clearAndInit("管理员信息")
        mView.viewName = "admin/edit"
        return mView
    }


    @PostMapping("/update")
    fun update(@RequestParam("nickname") nickName: String): ModelAndView {
        clearView()
        val adminUser = getAdmin()
        adminUser.nickName = nickName
        adminRepository.save(adminUser)
        setMessage("用户信息更新成功", true, "/")
        initView("用户信息")
        mView.viewName = "layout"
        return mView
    }

    @GetMapping("/password")
    fun editPassword(): ModelAndView {
        clearAndInit("修改密码")
        mView.viewName = "admin/password"
        return mView
    }

    @PostMapping("/password")
    fun updatePassword(@RequestParam("newpwd") newPassword: String,
                       @RequestParam("oldpwd") oldPassword: String,
                       @RequestParam("confirmpwd") confirmPassword: String): ModelAndView {
        clearAndInit("修改密码")
        mView.viewName = "admin/password"
        if (newPassword != confirmPassword) {
            setMessage("两次密码输入不匹配", false)
            return mView
        }
        val adminUser = getAdmin()
        if (!BCrypt.checkpw(oldPassword, adminUser.password)) {
            setMessage("原密码输入不正确", false)
        }
        adminUser.password = BCrypt.hashpw(newPassword, BCrypt.gensalt())
        adminRepository.save(adminUser)
        setMessage("密码更新成功", true, "/")
        mView.viewName = "layout"
        return mView
    }

}