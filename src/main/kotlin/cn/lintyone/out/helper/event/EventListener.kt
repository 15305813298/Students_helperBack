package cn.lintyone.out.helper.event

import cn.lintyone.out.helper.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class EventListener {

    @Autowired
    lateinit var messageService: MessageService

    @TransactionalEventListener
    @Async
    fun messageListener(event: MessageEvent) {
        messageService.create(event.user, event.content, event.type)
    }
}