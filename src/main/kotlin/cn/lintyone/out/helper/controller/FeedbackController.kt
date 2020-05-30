package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.model.Feedback
import cn.lintyone.out.helper.service.FeedbackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/feedback")
class FeedbackController :BaseController(){

    @Autowired
    lateinit var feedbackService: FeedbackService

    @GetMapping
    fun index(
            @RequestParam(required = false, defaultValue = "0") page: Int = 0,
            @RequestParam(required = false, defaultValue = "") keyword: String = ""
    ): ModelAndView {
        clearAndInit("档案管理 - 反馈管理 - 反馈列表")
        val feedbackPage = feedbackService.paginate(page, keyword)
        mView.addObject("page", feedbackPage)
        mView.addObject("keyword", keyword)
        mView.viewName = "feedback/index"
        return mView
    }

    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ModelAndView {
        clearAndInit("档案管理 - 反馈管理 - 反馈列表")
        userService.delete(id)
        setMessage("删除成功", true, "/feedback")
        mView.viewName = "layout"
        return mView
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    fun deleteAll(@RequestParam checkId: String): Map<String, String> {
        val list = getRequestList(checkId)
        for (id in list) {
            feedbackService.delete(id)
        }
        return sendMessage("删除成功", true, "/feedback")
    }
}