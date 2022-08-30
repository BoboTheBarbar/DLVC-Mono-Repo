package dlvc.manager.playerloader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class PlayerLoaderApplication

fun main(args: Array<String>) {
    runApplication<PlayerLoaderApplication>(*args)
}

@RestController
class MessageResource {
    @GetMapping("/hello")
    fun index(): List<Message> = listOf(
        Message("1", "Hello!"),
        Message("2", "Bonjour!"),
        Message("3", "Privet!"),
    )
}

data class Message(@Id val id: String?, val text: String)
