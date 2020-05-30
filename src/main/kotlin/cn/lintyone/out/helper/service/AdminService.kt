package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Admin

interface AdminService {

    fun get(id: Int): Admin

    fun getByUserName(userName: String): Admin?

    fun changePassword(admin: Admin, password: String, oldPassword: String)
}