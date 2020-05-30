package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Address
import cn.lintyone.out.helper.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AddressRepository : JpaRepository<Address, Int>, JpaSpecificationExecutor<Address> {

    fun findByUser(user: User): List<Address>
}