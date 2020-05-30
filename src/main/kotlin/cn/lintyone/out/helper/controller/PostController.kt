package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/post")
class PostController : BaseController() {

    @Autowired
    lateinit var postService: PostService

    @GetMapping
    fun index(
            @RequestParam(required = false, defaultValue = "0") page: Int = 0,
            @RequestParam(required = false, defaultValue = "") keyword: String = ""
    ): ModelAndView {
        clearAndInit("档案管理 - 帖子管理 - 帖子列表")
        val postPage = postService.paginate(page, keyword)
        mView.addObject("page", postPage)
        mView.addObject("keyword", keyword)
        mView.viewName = "post/index"
        return mView
    }


    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ModelAndView {
        clearAndInit("档案管理 - 帖子管理 - 帖子列表")
        userService.delete(id)
        setMessage("删除成功", true, "/post")
        mView.viewName = "layout"
        return mView
    }


    @PostMapping("/deleteAll")
    @ResponseBody
    fun deleteAll(@RequestParam checkId: String): Map<String, String> {
        val list = getRequestList(checkId)
        for (id in list) {
            postService.delete(id)
        }
        return sendMessage("删除成功", true, "/post")
    }

}