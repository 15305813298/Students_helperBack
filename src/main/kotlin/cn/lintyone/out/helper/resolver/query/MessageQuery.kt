package cn.lintyone.out.helper.resolver.query

import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.resolver.BaseQuery
import org.springframework.stereotype.Component

@Component
class MessageQuery : BaseQuery() {

    fun messages(page: Int): List<Message> {
        return messageService.list(getUser(),page)
    }
}