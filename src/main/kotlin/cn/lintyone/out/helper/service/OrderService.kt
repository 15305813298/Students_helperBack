package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.Order
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.OrderInput
import org.springframework.data.domain.Page

interface OrderService {

    fun get(id: Int): Order

    fun create(user: User, address: Address, orderInput: OrderInput): Order

    fun listByUser(user: User, page: Int): List<Order>

    fun list(user: User, page: Int): List<Order>

    fun paginate(page: Int): Page<Order>

    fun pick(user: User, order: Order)

    fun success(order: Order)

    fun hasPick(user: User): Boolean

    fun delete(id : Int)

}