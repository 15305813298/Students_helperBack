package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.User


interface AddressService {

    fun get(id: Int): Address

    fun create(user: User, description: String): Address

    fun list(user: User): List<Address>
}