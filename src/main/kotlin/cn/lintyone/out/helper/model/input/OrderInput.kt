package cn.lintyone.out.helper.model.input

import cn.lintyone.out.helper.model.Order

class OrderInput {
    lateinit var type: Order.Type
    var expressCode: String? = null
    var expressName: String? = null
    var addressId: Int = 0
    lateinit var description: String
    var pickAddress: String? = null
    var offer: Int = 0
}