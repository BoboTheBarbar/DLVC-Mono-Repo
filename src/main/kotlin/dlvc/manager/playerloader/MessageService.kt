package dlvc.manager.playerloader

import dlvc.manager.playerloader.interfaceAdapters.Message
import org.springframework.stereotype.Service

@Service
class MessageService(val messageRepository: MessageRepository) {

    fun findMessages() : List<Message> = messageRepository.findMessage()

    fun postMessage(message: Message) = messageRepository.save(message)
}