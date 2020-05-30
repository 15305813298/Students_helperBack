package cn.lintyone.out.helper.service

import cn.lintyone.out.helper.model.Comment
import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.User

interface MessageService {

    fun create(user: User, content: String, type: Message.Type): Message

    fun list(user: User, page: Int): List<Message>
}