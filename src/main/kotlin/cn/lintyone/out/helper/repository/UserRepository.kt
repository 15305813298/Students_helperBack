package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date

interface UserRepository : JpaRepository<User, Int>, JpaSpecificationExecutor<User> {

    @Query("select count(id) from User where lastLoginTime > current_date ")
    fun countUser(): Int

    @Query("select count(id) from User where createdAt > :min and createdAt < :max")
    fun countUserDaily(@Param("min") min: Date, @Param("max") max: Date) : Int

    fun findByUserNameOrStudentId(userName: String, studentId: String): User?

    fun findByUserName(userName: String): User?

    fun findByStudentId(userName: String): User?

}