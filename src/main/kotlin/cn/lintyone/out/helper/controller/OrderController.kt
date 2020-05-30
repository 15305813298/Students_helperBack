package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/order")
class OrderController : BaseController() {

    @Autowired
    lateinit var orderService: OrderService

    @GetMapping
    fun index(
            @RequestParam(required = false, defaultValue = "0") page: Int = 0
    ): ModelAndView {
        clearAndInit("档案管理 - 订单管理 - 订单列表")
        val orderPage = orderService.paginate(page)
        mView.addObject("page", orderPage)
        mView.viewName = "order/index"
        return mView
    }


    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Int): ModelAndView {
        clearAndInit("档案管理 - 订单管理 - 订单列表")
        userService.delete(id)
        setMessage("删除成功", true, "/order")
        mView.viewName = "layout"
        return mView
    }


    @PostMapping("/deleteAll")
    @ResponseBody
    fun deleteAll(@RequestParam checkId: String): Map<String, String> {
        val list = getRequestList(checkId)
        for (id in list) {
            orderService.delete(id)
        }
        return sendMessage("删除成功", true, "/order")
    }

}