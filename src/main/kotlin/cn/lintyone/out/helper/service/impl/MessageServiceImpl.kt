package cn.lintyone.out.helper.service.impl

import cn.lintyone.out.helper.model.Message
import cn.lintyone.out.helper.model.User
import cn.lintyone.out.helper.repository.MessageRepository
import cn.lintyone.out.helper.service.MessageService
import com.github.wenhao.jpa.Specifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl : MessageService {

    @Autowired
    lateinit var messageRepository: MessageRepository

    override fun create(user: User, content: String, type: Message.Type): Message {
        val message = Message()
        message.user = user
        message.content = content
        message.type = type
        messageRepository.save(message)
        return message
    }

    override fun list(user: User, page: Int): List<Message> {
        val pre = Specifications.and<Message>()
        pre.eq("user", user)

        val sort = Sort.Order(Sort.Direction.DESC, "id")
        val pageRequest = PageRequest.of(page, 15, Sort.by(sort))
        return messageRepository.findAll(pre.build(), pageRequest).content
    }

}