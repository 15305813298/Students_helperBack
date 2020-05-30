package cn.lintyone.out.helper.resolver.mutation

import cn.lintyone.out.helper.common.MyException
import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.input.OrderInput
import cn.lintyone.out.helper.resolver.BaseMutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderMutation : BaseMutation() {

    @Transactional
    fun createOrder(orderInput: OrderInput): String {
        val address = addressService.get(orderInput.addressId)
        val order = orderService.create(getUser(), address, orderInput)
        userService.reduceBalance(getUser(), order.offer)
        messageService.create(getUser(), "订单创建成功\n" +
                "金额： ${order.offer.toInt()}\n" +
                "地址： ${order.address.description}\n", Message.Type.ORDER)
        return "订单创建成功"
    }

    fun pickOrder(id: Int): String {
        val order = orderService.get(id)
        orderService.pick(getUser(), order)
        return "订单接受成功"
    }

    fun orderSuccess(id: Int, code: String): String {
        val order = orderService.get(id)
        if (code != order.successCode) {
            throw MyException("确认码错误")
        }
        orderService.success(order)
        userService.addBalance(order.pickUser!!, order.offer)
        messageService.create(order.pickUser!!, "订单已完成\n" +
                "金额： ${order.offer.toInt()}\n", Message.Type.ORDER)
        return "订单已确认"
    }
}