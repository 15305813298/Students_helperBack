package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AdminRepository : JpaRepository<Admin, Int>, JpaSpecificationExecutor<Admin> {

    fun findByUserName(userName: String): Admin?

}