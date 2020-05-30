package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.Order
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class OrderQuery : BaseQuery() {

    fun orders(page: Int): List<Order> {
        return orderService.list(getUser(), page)
    }

    fun ordersByUser(page: Int): List<Order> {
        return orderService.listByUser(getUser(), page)
    }

    fun hasPick(): Boolean {
        return orderService.hasPick(getUser())
    }
}