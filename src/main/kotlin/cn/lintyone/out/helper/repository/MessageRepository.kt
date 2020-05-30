package cn.lintyone.out.helper.repository

import cn.lintyone.out.helper.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MessageRepository : JpaRepository<Message, Int>, JpaSpecificationExecutor<Message> {

}