package dlvc.manager.playerloader.InterfaceAdapters

import dlvc.manager.playerloader.MessageService
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(val service: MessageService) {
    @GetMapping("/hello")
    fun index(): List<Message> = service.findMessages()

    @PostMapping("/hello")
    fun post(@RequestBody message: Message) = service.postMessage(message)
}

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)