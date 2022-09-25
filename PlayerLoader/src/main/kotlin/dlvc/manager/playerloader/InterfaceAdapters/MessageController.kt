package dlvc.manager.playerloader.interfaceAdapters

import dlvc.manager.playerloader.MessageService
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(val service: MessageService) {
    @GetMapping("/hello")
    fun index(): List<Message> = service.findMessages()

    @PostMapping("/hello")
    fun post(@RequestBody message: Message) = service.postMessage(message)

    @RequestMapping("/start")
    fun start(): String {
      return "index";   // FIXME: Doesn't return html resource yet
    }
}

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)