package cn.lintyone.out.helper.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/user")
class UserController : BaseController() {

    @GetMapping
    fun index(
            @RequestParam(required = false, defaultValue = "0") page: Int = 0,
            @RequestParam(required = false, defaultValue = "") keyword: String = ""
    ): ModelAndView {
        clearAndInit("系统管理 - 用户管理 - 用户列表")
        val areaPage = userService.paginate(page, keyword)
        mView.addObject("keyword", keyword)
        mView.addObject("page", areaPage)
        mView.viewName = "user/index"
        return mView
    }


    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ModelAndView {
        clearAndInit("系统管理 - 用户管理 - 用户列表")
        userService.delete(id)
        setMessage("删除成功", true, "/user")
        mView.viewName = "layout"
        return mView
    }


    @PostMapping("/deleteAll")
    @ResponseBody
    fun deleteAll(@RequestParam checkId: String): Map<String, String> {
        val list = getRequestList(checkId)
        for (id in list) {
            userService.delete(id)
        }
        return sendMessage("删除成功", true, "/user")
    }


}