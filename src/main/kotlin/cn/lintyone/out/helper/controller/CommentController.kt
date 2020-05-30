package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/comment")
class CommentController : BaseController() {

    @Autowired
    lateinit var commentService: CommentService

    @GetMapping
    fun index(
            @RequestParam(required = false, defaultValue = "0") page: Int = 0,
            @RequestParam(required = false, defaultValue = "") keyword: String = ""
    ): ModelAndView {
        clearAndInit("档案管理 - 评论管理 - 评论列表")
        val commentPage = commentService.paginate(page, keyword)
        mView.addObject("page", commentPage)
        mView.addObject("keyword", keyword)
        mView.viewName = "comment/index"
        return mView
    }


    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ModelAndView {
        clearAndInit("档案管理 - 评论管理 - 评论列表")
        userService.delete(id)
        setMessage("删除成功", true, "/comment")
        mView.viewName = "layout"
        return mView
    }


    @PostMapping("/deleteAll")
    @ResponseBody
    fun deleteAll(@RequestParam checkId: String): Map<String, String> {
        val list = getRequestList(checkId)
        for (id in list) {
            commentService.delete(id)
        }
        return sendMessage("删除成功", true, "/comment")
    }

}