package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class AddressQuery : BaseQuery() {

    fun address(id: Int?): Address {
        val address = Address()
        address.id = 0
        address.description = "暂无地址"
        return if (id == null) {
            getUser().address ?: address
        } else {
            addressService.get(id)
        }
    }

    fun addresses(): List<Address> {
        return addressService.list(getUser())
    }



}