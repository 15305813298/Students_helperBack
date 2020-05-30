package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Order
import cn.lintyone.out.helper.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date

interface OrderRepository : JpaRepository<Order, Int>, JpaSpecificationExecutor<Order> {

    fun findByPickUserAndStatus(pickUser: User, status: Order.Status = Order.Status.RECEIVED): Order?

    @Query("select count(id) from Order where createdAt > current_date ")
    fun countOrder(): Int

    @Query("select count(id) from Order where createdAt > :min and createdAt < :max")
    fun countOrderDaily(@Param("min") min: Date, @Param("max") max: Date) : Int
}