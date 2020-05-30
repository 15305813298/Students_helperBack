package cn.lintyone.out.helper.event

import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.User
import org.springframework.context.ApplicationEvent


class MessageEvent(source: Any, val type: Message.Type, val content: String, val user: User) : ApplicationEvent(source)