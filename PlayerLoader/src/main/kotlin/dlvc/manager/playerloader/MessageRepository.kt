package dlvc.manager.playerloader

import dlvc.manager.playerloader.interfaceAdapters.Message
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, String> {

    @Query("select * from messages")
    fun findMessage(): List<Message>
}