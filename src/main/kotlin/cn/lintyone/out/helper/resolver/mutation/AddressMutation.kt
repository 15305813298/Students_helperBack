package cn.lintyone.out.helper.resolver.mutation

import cn.lintyone.out.helper.resolver.BaseMutation
import org.springframework.stereotype.Component

@Component
class AddressMutation : BaseMutation() {

    fun createAddress(description: String): String {
        val address = addressService.create(getUser(), description)
        userService.setDefault(getUser(), address)
        return "默认地址设置成功"
    }

    fun setDefaultAddress(id: Int): String {
        userService.setDefault(getUser(), addressService.get(id))
        return "默认地址设置成功"
    }

}