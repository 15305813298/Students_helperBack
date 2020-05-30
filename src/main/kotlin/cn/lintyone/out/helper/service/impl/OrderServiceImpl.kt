package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.common.defaultPage
import cn.lintyone.out.helper.event.MessageEvent
import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.Order
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.model.input.OrderInput
import cn.lintyone.out.helper.repository.OrderRepository
import cn.lintyone.out.helper.service.OrderService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderServiceImpl : OrderService {

    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var context: ApplicationContext


    override fun get(id: Int): Order {
        val optional = orderRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw MyException("订单不存在")
        }
    }

    override fun create(user: User, address: Address, orderInput: OrderInput): Order {
        val order = Order()
        order.postUser = user
        order.address = address
        order.type = orderInput.type
        order.status = Order.Status.UNRECEIVED
        order.description = orderInput.description
        order.school = user.school
        order.expressCode = orderInput.expressCode
        order.pickAddress = orderInput.pickAddress
        order.expressName = orderInput.expressName
        order.offer = BigDecimal(orderInput.offer)
        order.successCode = (1000..9999).random().toString()
        orderRepository.save(order)
        return order
    }

    override fun listByUser(user: User, page: Int): List<Order> {
        val pre = Specifications.and<Order>()
        pre.eq("postUser", user)
        return pre.defaultPage(page, orderRepository).content
    }

    override fun list(user: User, page: Int): List<Order> {
        val pre = Specifications.and<Order>()
        if (hasPick(user)) {
            pre.eq("status", Order.Status.RECEIVED)
            pre.eq("pickUser", user)
        } else {
            pre.eq("status", Order.Status.UNRECEIVED)
        }
        return pre.defaultPage(page, orderRepository).content
    }

    override fun paginate(page: Int): Page<Order> {
        val pre = Specifications.or<Order>()
        return pre.defaultPage(page, orderRepository)
    }

    @Transactional
    override fun pick(user: User, order: Order) {
        if (hasPick(user)) {
            throw MyException("用户存在未完成订单")
        }
        order.pickUser = user
        order.status = Order.Status.RECEIVED
        orderRepository.save(order)
        context.publishEvent(MessageEvent(this, Message.Type.ORDER, "订单地址: " +
                "[" + order.address + "]\n" +
                "订单类型: " + order.getTypeName() + "\n" +
                "接取用户: " + user.userName, order.postUser))
    }

    @Transactional
    override fun success(order: Order) {
        if (order.status != Order.Status.RECEIVED) {
            throw MyException("订单状态异常")
        }
        order.status = Order.Status.SUCCESS
        orderRepository.save(order)
    }

    @Transactional
    override fun hasPick(user: User): Boolean {
        val order = orderRepository.findByPickUserAndStatus(user, Order.Status.RECEIVED)
        return order != null
    }

    override fun delete(id: Int) {
        orderRepository.deleteById(id)
    }
}